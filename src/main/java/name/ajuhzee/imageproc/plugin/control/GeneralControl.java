package name.ajuhzee.imageproc.plugin.control;

import java.io.File;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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
	public void openDialog(Consumer<File> fileChosen) {
		Platform.runLater(() -> {
			fileChosen.accept(createFileChooser().showOpenDialog(mainStage));
		});
	}

	private static FileChooser createFileChooser() {
		ExtensionFilter supportedImages = new ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp");

		final FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(supportedImages);
		return fc;
	}

	/**
	 * Opens a File Dialog to save an Image to a file.
	 * 
	 * @param saveImage
	 *            method that gets called with "file" as its parameter
	 */
	public void saveDialog(Consumer<File> saveImage) {
		Platform.runLater(() -> {
			saveImage.accept(createFileChooser().showSaveDialog(mainStage));
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
