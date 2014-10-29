package name.ajuhzee.imageproc.plugin.core;

import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.CorePluginContext;

public class Configuration extends CorePlugin {

	public Configuration(CorePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("file", "Datei", 0).then("config", "Einstellungen").get(), context);
	}

	@Override
	public void started() {
		// TODO Auto-generated method stub

	}

}
