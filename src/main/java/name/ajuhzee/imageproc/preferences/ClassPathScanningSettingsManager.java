package name.ajuhzee.imageproc.preferences;

import com.google.common.reflect.ClassPath;
import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Temporary manager
 *
 * @author Ajuhzee
 */
public class ClassPathScanningSettingsManager implements SettingsManager {

	private static final Logger LOGGER = LogManager.getLogger();

	private Map<String, Object> settings = new HashMap<String, Object>();


	@Override
	public Set<Class<? extends CorePlugin>> getCorePlugins() {
		return loadPluginType("name.ajuhzee.imageproc.plugin.core", CorePlugin.class);
	}

	private <T> Set<Class<? extends T>> loadPluginType(String packageName, Class<T> pluginType) {
		try {
			Set<ClassPath.ClassInfo> corePlugins = ClassPath.from(this.getClass().getClassLoader())
					.getTopLevelClassesRecursive(packageName);
			return corePlugins.stream().filter((info) -> {
				Class<?> potentialCorePlugin = info.load();
				return potentialCorePlugin.getSuperclass().equals(pluginType);
			}).map((info) -> {
				return (Class<? extends T>) info.load();
			}).collect(Collectors.toSet());
		} catch (IOException e) {
			LOGGER.error("Could not load core plugins!", e);
			return Collections.emptySet();
		}
	}

	@Override
	public Set<Class<? extends ImagePlugin>> getImagePlugins() {
		return loadPluginType("name.ajuhzee.imageproc.plugin.image", ImagePlugin.class);
	}

	/**
	 * Stores a settings value
	 *
	 * @param key
	 * @param value
	 */
	public void store(String key, Object value) {
		settings.put(key, value);
	}

	@Override
	public <T> Optional<T> getValue(String string) {
		return Optional.ofNullable((T) settings.get(string));
	}
}
