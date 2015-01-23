package name.ajuhzee.imageproc.plugin.control;

import java.io.File;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;
import name.ajuhzee.imageproc.view.Popup;

/**
 * Provides some general features for ImageProc.
 * 
 * @author Ajuhzee
 *
 */
public class GeneralControl {

	private final Stage mainStage;

	/**
	 * Provides a class to access general control .
	 * 
	 * @param primaryStage
	 *            the main stage
	 */
	public GeneralControl(Stage primaryStage) {
		mainStage = primaryStage;
	}

	/**
	 * Opens a File Dialog to choose an Image from file.
	 * 
	 * @param fileChosen
	 *            method that gets called with "file" as its parameter
	 */
	public void openDialog(Callback<File, Void> fileChosen) {
		ExtensionFilter supportedImages = new ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp");

		Platform.runLater(() -> {
			final FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(supportedImages);
			final File file = fc.showOpenDialog(mainStage);
			fileChosen.call(file);
		});
	}

	/**
	 * Opens a File Dialog to save an Image to a file.
	 * 
	 * @param saveImage
	 *            method that gets called with "file" as its parameter
	 */
	public void saveDialog(Callback<File, Void> saveImage) {
		ExtensionFilter supportedImages = new ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp");

		Platform.runLater(() -> {
			final FileChooser fc = new FileChooser();
			fc.getExtensionFilters().add(supportedImages);
			final File file = fc.showSaveDialog(mainStage);
			saveImage.call(file);
		});
	}

	/**
	 * Opens a popup with specific text.
	 * 
	 * @param title
	 * @param text
	 */
	@SuppressWarnings("static-method")
	public void showPopup(String title, String text) {
		Popup.show(title, text);
	}

}
