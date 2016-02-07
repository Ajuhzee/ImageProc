package name.ajuhzee.imageproc.plugin;

import name.ajuhzee.imageproc.plugin.control.CorePluginContext;

/**
 * Base class for plugins which use core functionalities of the program.
 *
 * @author Ajuhzee
 */
public abstract class CorePlugin extends PluginBase {

	private final CorePluginContext context;

	/**
	 * Creates an instance of an core plugin.
	 *
	 * @param menuPosition the menu position of the plugin
	 * @param context the context of the plugin
	 * @throws PluginLoadException if context == null
	 */
	public CorePlugin(MenuPosition menuPosition, CorePluginContext context)
			throws PluginLoadException {
		super(menuPosition);
		if (context == null) {
			throw new PluginLoadException("Plugin context not correctly loaded!");
		}
		this.context = context;
	}

	/**
	 * @return access to core context
	 */
	protected CorePluginContext context() {
		return context;
	}

}
