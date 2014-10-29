package name.ajuhzee.imageproc.plugin;

import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

public abstract class ImagePlugin extends PluginBase {

	private final ImagePluginContext context;

	public ImagePlugin(MenuPosition menuPosition, ImagePluginContext context) throws PluginLoadException {
		super(menuPosition);
		if (context == null) {
			throw new PluginLoadException("Plugin context not correctly loaded!");
		}
		this.context = context;
	}

	public ImagePluginContext context() {
		return context;
	}

}
