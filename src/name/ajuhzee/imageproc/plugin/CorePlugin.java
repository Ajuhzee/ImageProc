package name.ajuhzee.imageproc.plugin;

import name.ajuhzee.imageproc.plugin.control.CorePluginContext;

public abstract class CorePlugin extends PluginBase {

	private final CorePluginContext context;

	public CorePlugin(MenuPosition menuPosition, CorePluginContext context) throws PluginLoadException {
		super(menuPosition);
		if (context == null) {
			throw new PluginLoadException("Plugin context not correctly loaded!");
		}
		this.context = context;
	}

	protected CorePluginContext context() {
		return context;
	}

}
