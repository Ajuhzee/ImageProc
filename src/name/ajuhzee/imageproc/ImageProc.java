package name.ajuhzee.imageproc;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.plugin.PluginManager;
import name.ajuhzee.imageproc.preferences.DummySettingsManager;
import name.ajuhzee.imageproc.preferences.SettingsManager;
import name.ajuhzee.imageproc.view.MainWindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides a program for image processing.
 * 
 * @author Ajuhzee
 *
 */
public class ImageProc extends Application {

	/**
	 * Launches the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
		System.exit(0);
	}

	private static final Logger logger = LogManager.getLogger();

	private SettingsManager preferences;

	private PluginManager plugins;

	private MainWindow mainWindow;

	@Override
	public void start(Stage primaryStage) throws InitializationException {
		try {
			mainWindow = new MainWindow(primaryStage);
		} catch (final IOException e) {
			throw new InitializationException("Could not load the main window.", e);
		}

		preferences = new DummySettingsManager();

		plugins = new PluginManager(mainWindow);

		plugins.loadCorePlugins(preferences.getCorePlugins());
		plugins.loadImagePlugins(preferences.getImagePlugins());

		logger.info("Successfully started ImageProc");
	}

}
