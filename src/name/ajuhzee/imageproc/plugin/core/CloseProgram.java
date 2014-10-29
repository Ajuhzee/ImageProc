package name.ajuhzee.imageproc.plugin.core;

import javafx.application.Platform;
import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.CorePluginContext;

public class CloseProgram extends CorePlugin {

	public CloseProgram(CorePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("file", "Datei", 0).then("close", "Beenden", 100).get(), context);
	}

	@Override
	public void started() {
		Platform.runLater(() -> {
			context().getMainStage().close();
		});
	}

}
