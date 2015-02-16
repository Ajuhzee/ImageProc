package name.ajuhzee.imageproc.plugin.control;

import javafx.stage.Stage;
import name.ajuhzee.imageproc.view.PluginMenu;

/**
 * Provides a class to give extended access to program recources.
 * 
 * @author Ajuhzee
 *
 */
public interface CorePluginContext extends ImagePluginContext {

	/**
	 * 
	 * @return access to the main menu
	 */
	public PluginMenu getMainMenu();

	/**
	 * 
	 * @return access to the main stage
	 */
	public Stage getMainStage();
}
