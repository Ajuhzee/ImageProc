package name.ajuhzee.imageproc.view;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.localization.MapResourceBundle;

import com.google.common.base.Throwables;

public class Popup {

	public static void show(String title, String text) {
		Platform.runLater(() -> {
			final URL popupFxml = Popup.class.getClassLoader().getResource("name/ajuhzee/imageproc/view/Popup.fxml");
			final MapResourceBundle<String> bundle = new MapResourceBundle<>();
			bundle.put("label", text);
			final FXMLLoader loader = new FXMLLoader(popupFxml, bundle);

			final Stage popupStage = new Stage();
			popupStage.setTitle(title);
			final Runnable close = popupStage::close;

			loader.setControllerFactory(controllerClass -> {
				try {
					final Constructor<?> c = controllerClass.getConstructor(Runnable.class);
					return c.newInstance(close);
				} catch (final Exception e) {
					// should never happen
					throw Throwables.propagate(e);
				}
			});

			try {
				final Scene popupScene = new Scene(loader.<AnchorPane> load());
				popupStage.setScene(popupScene);
				popupStage.show();
			} catch (final IOException e) {
				// should never happen
				throw Throwables.propagate(e);
			}

		});

	}

	@FXML
	private Button okButton;

	@FXML
	private Label label;

	private final Runnable onClose;

	public Popup(Runnable onClose) {
		this.onClose = onClose;
	}

	@FXML
	void close(ActionEvent event) {
		onClose.run();
	}

}
