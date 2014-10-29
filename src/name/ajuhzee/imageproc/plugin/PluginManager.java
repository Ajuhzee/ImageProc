package name.ajuhzee.imageproc.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import name.ajuhzee.imageproc.plugin.control.CorePluginContext;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.view.MainWindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.FormattedMessage;

public class PluginManager {

	private static final Logger logger = LogManager.getLogger();

	private final MainWindow mainWindow;

	private final Set<PluginBase> loadedPlugins = new HashSet<>();

	private final PluginExecutor executor = new SingleThreadPluginExecutor();

	public PluginManager(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void loadCorePlugins(Set<Class<? extends CorePlugin>> corePlugins) {
		loadPlugins(corePlugins, CorePluginContext.class);
	}

	public void loadImagePlugins(Set<Class<? extends ImagePlugin>> imagePlugins) {
		loadPlugins(imagePlugins, ImagePluginContext.class);
	}

	private <PluginType extends PluginBase> void loadPlugin(Class<? extends PluginType> pluginClass,
			Class<?> contextClass) throws PluginLoadException {
		try {
			final Constructor<? extends PluginType> constructor = pluginClass.getConstructor(contextClass);
			final PluginType plugin = constructor.newInstance(contextClass.cast(mainWindow));

			if (loadedPlugins.contains(plugin)) {
				throw new PluginLoadException("Can't load plugins with the same menu position.");
			}

			mainWindow.getMainMenu().addPlugin(plugin, executor);

			loadedPlugins.add(plugin);
		} catch (final NoSuchMethodException e) {
			throw new PluginLoadException(
					"The plugin does not have a constructor that takes a single argument of type "
							+ contextClass.getName() + ".", e);
		} catch (final IllegalAccessException e) {
			throw new PluginLoadException("The plugin constructor is not visible.", e);
		} catch (final InstantiationException e) {
			throw new PluginLoadException("The plugin class can not be abstract.", e);
		} catch (final InvocationTargetException e) {
			throw new PluginLoadException("The plugin constructor threw an exception.", e);
		} catch (final ClassCastException e) {
			throw new PluginLoadException("The mainwindow can not provide the plugin context \""
					+ contextClass.getName() + "\".");
		}
	}

	private <PluginType extends PluginBase, ContextType> void loadPlugins(Set<Class<? extends PluginType>> plugins,
			Class<ContextType> contextClass) {
		for (final Class<? extends PluginType> pluginType : plugins) {
			try {
				loadPlugin(pluginType, contextClass);
			} catch (final PluginLoadException e) {
				logger.fatal(new FormattedMessage("Plugin {} could not be loaded.", pluginType.getName()), e);
			}
		}
	}
}
