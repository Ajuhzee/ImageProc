package name.ajuhzee.imageproc.plugin.control;

import java.io.File;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import name.ajuhzee.imageproc.view.Popup;

/**
 * Provides some general features for ImageProc.
 * 
 * @author Ajuhzee
 *
 */
public class GeneralControl {

	/**
	 * Opens a File Dialog to choose an Image from file.
	 * 
	 * @param fileChosen
	 *            method that gets called with "file" as its parameter
	 */
	@SuppressWarnings("static-method")
	public void fileChooser(Callback<File, Void> fileChosen) {
		Platform.runLater(() -> {
			final FileChooser fc = new FileChooser();
			final File file = fc.showOpenDialog(null);
			fileChosen.call(file);
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
