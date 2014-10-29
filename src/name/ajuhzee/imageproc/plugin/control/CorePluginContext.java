package name.ajuhzee.imageproc.plugin.control;

import javafx.stage.Stage;
import name.ajuhzee.imageproc.view.PluginMenu;

public interface CorePluginContext extends ImagePluginContext {

	public PluginMenu getMainMenu();

	public Stage getMainStage();
}
