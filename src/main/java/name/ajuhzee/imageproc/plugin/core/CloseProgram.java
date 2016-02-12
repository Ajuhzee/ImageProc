package name.ajuhzee.imageproc.plugin.core;

import javafx.application.Platform;
import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.CorePluginContext;

/**
 * Adds a core plugin, which closes the program.
 *
 * @author Ajuhzee
 */
public class CloseProgram extends CorePlugin {

	private static final PluginInformation INFO = new PluginInformation("Beenden", false);

	/**
	 * Creates the CloseProgram plugin and positions a Menu-button for it.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public CloseProgram(CorePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("file", "Datei", 0).subMenu("close", INFO, 100).get(), context);
	}

	@Override
	public void started() {
		Platform.runLater(() -> {
			context().getMainStage().close();
		});
	}

}
