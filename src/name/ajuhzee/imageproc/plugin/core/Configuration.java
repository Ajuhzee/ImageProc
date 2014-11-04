package name.ajuhzee.imageproc.plugin.core;

import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.CorePluginContext;

/**
 * Adds a core plugin for some configurations.
 * 
 * @author Ajuhzee
 *
 */
public class Configuration extends CorePlugin {

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public Configuration(CorePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("file", "Datei", 0).subMenu("config", "Einstellungen").get(), context);
	}

	@Override
	public void started() {
		// TODO Auto-generated method stub

	}

}
