package name.ajuhzee.imageproc.plugin.control;

import java.io.File;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import name.ajuhzee.imageproc.view.Popup;

public class GeneralControl {

	private final Stage stage;

	public GeneralControl(Stage stage) {
		this.stage = stage;
	}

	public void fileChooser(Callback<File, Void> fileChosen) {
		Platform.runLater(() -> {
			final FileChooser fc = new FileChooser();
			final File file = fc.showOpenDialog(null);
			fileChosen.call(file);
		});
	}

	public void showPopup(String title, String text) {
		Popup.show(title, text);
	}
}
