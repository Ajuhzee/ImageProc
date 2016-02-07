package name.ajuhzee.imageproc.plugin.control;

import javafx.application.Platform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.view.Popup;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Provides some general features for ImageProc.
 *
 * @author Ajuhzee
 */
public class GeneralControl {

	private final Stage mainStage;

	private static FileChooser createImageFileChooser(final List<String> fileTypes) {
		List<String> wildcardFileTypes = fileTypes.stream().map((type) -> "*." + type).collect(Collectors.toList());
		final ExtensionFilter supportedImages =
				new ExtensionFilter("Images", wildcardFileTypes.toArray(new String[wildcardFileTypes.size()]));

		final FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(supportedImages);
		return fc;
	}

	/**
	 * Provides a class to access general control .
	 *
	 * @param primaryStage the main stage
	 */
	public GeneralControl(final Stage primaryStage) {
		mainStage = primaryStage;
	}

	/**
	 * Opens a File Dialog to choose an Image from file.
	 *
	 * @param fileChosen method that gets called with "file" as its parameter
	 */
	public void openImageDialog(final Consumer<File> fileChosen) {
		Platform.runLater(() -> {
			final File chosenFile =
					createImageFileChooser(Arrays.asList("png", "jpg", "bmp")).showOpenDialog(mainStage);
			if (chosenFile != null) {
				fileChosen.accept(chosenFile);
			}
		});
	}

	/**
	 * Opens a File Dialog to save an Image to a file.
	 *
	 * @param saveImage method that gets called with "file" as its parameter
	 */
	public void saveImageDialog(final Consumer<File> saveImage) {
		Platform.runLater(() -> {
			saveImage.accept(createImageFileChooser(Collections.singletonList("png")).showSaveDialog(mainStage));
		});
	}

	/**
	 * Opens a File Dialog to choose a directory.
	 *
	 * @param callback
	 */
	public void specifyDirectoryDialog(final Consumer<Path> callback) {
		final DirectoryChooser chooser = new DirectoryChooser();
		Platform.runLater(() -> {
			final File file = chooser.showDialog(mainStage);
			if (file == null) {
				return;
			}
			callback.accept(file.toPath());
		});
	}

	/**
	 * Opens a popup with specific text.
	 *
	 * @param title
	 * @param text
	 */
	@SuppressWarnings("static-method")
	public void showPopup(final String title, final String text) {
		Popup.show(title, text);
	}


	/**
	 * Opens a popup with specific text and calls the given callback when the popup is closed by the user
	 *
	 * @param title
	 * @param text
	 * @param onClose the function to call on popup close
	 */
	@SuppressWarnings("static-method")
	public void showPopup(final String title, final String text, Runnable onClose) {
		Popup.show(title, text, onClose);
	}

}
