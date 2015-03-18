package name.ajuhzee.imageproc;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.plugin.PluginManager;
import name.ajuhzee.imageproc.preferences.DummySettingsManager;
import name.ajuhzee.imageproc.view.MainWindow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

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
		loadOpenCV();
		Application.launch(args);
		System.exit(0);
	}

	private static void loadOpenCV() {
		logger.debug("Loading OpenCV library from path: {}", System.getProperty("java.library.path"));
		try {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			openCVTest();
		} catch (UnsatisfiedLinkError e) {
			logger.error("Set the system property 'java.library.path' to the location of the {} library.",
					Core.NATIVE_LIBRARY_NAME);
		}
		logger.debug("OpenCV successfully loaded.");
	}

	private static void openCVTest() {
		double mult = 0.25;

		Mat laplaceFilter = new Mat(3, 3, CvType.CV_64F, new Scalar(0));

		Mat middleRow = laplaceFilter.row(1);
		middleRow.setTo(new Scalar(mult));
		Mat middleColumn = laplaceFilter.col(1);
		middleColumn.setTo(new Scalar(mult));
		laplaceFilter.put(1, 1, mult * -4);

		logger.debug("Laplace filter 3x3:\n" + laplaceFilter.dump());
	}

	private static final Logger logger = LogManager.getLogger();

	private DummySettingsManager preferences;

	private PluginManager plugins;

	private MainWindow mainWindow;

	@Override
	public void start(Stage primaryStage) throws InitializationException {

		preferences = new DummySettingsManager();
		preferences.store("imageproc.loadimage.startimage", getParameters().getNamed().get("image"));

		try {
			mainWindow = new MainWindow(primaryStage, preferences);
		} catch (final IOException e) {
			throw new InitializationException("Could not load the main window.", e);
		}

		plugins = new PluginManager(mainWindow);

		plugins.loadCorePlugins(preferences.getCorePlugins());
		plugins.loadImagePlugins(preferences.getImagePlugins());

		logger.info("Successfully started ImageProc");
	}
}
