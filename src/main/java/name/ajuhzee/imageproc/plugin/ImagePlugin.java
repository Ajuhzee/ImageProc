package name.ajuhzee.imageproc.plugin;

import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

/**
 * Base class for plugins which work with the image.
 *
 * @author Ajuhzee
 */
public abstract class ImagePlugin extends PluginBase {

	private final ImagePluginContext context;

	/**
	 * Creates an instance of an image plugin.
	 *
	 * @param menuPosition the menu position of the plugin
	 * @param context the context of the plugin
	 * @throws PluginLoadException if context == null
	 */
	public ImagePlugin(MenuPosition menuPosition, ImagePluginContext context)
			throws PluginLoadException {
		super(menuPosition);
		if (context == null) {
			throw new PluginLoadException("Plugin context not correctly loaded!");
		}
		this.context = context;
	}

	/**
	 * @return access to the image context
	 */
	public ImagePluginContext context() {
		return context;
	}

}
