package name.ajuhzee.imageproc.view;

import com.google.common.base.Throwables;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import name.ajuhzee.imageproc.util.localization.MapResourceBundle;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Optional;

/**
 * Is used to provide a simple popup for varying purposes.
 *
 * @author Ajuhzee
 */
public class Popup {

	private final Runnable onClose;

	@FXML
	private Button okButton;

	@FXML
	private Label label;

	/**
	 * Opens the popup.
	 *
	 * @param title the name of the popup
	 * @param text the information of the popup
	 */
	public static void show(String title, String text) {
		show(title, text, Optional.empty());
	}


	/**
	 * Opens the popup. Calls the given callback on completion
	 *
	 * @param title the name of the popup
	 * @param text the information of the popup
	 * @param onClose the information of the popup
	 */
	public static void show(String title, String text, Runnable onClose) {
		show(title, text, Optional.of(onClose));
	}

	private static void show(String title, String text, Optional<Runnable> onClose) {
		Platform.runLater(() -> {
			final URL fxml = Popup.class.getClassLoader().getResource("Popup.fxml");
			final MapResourceBundle<String> bundle = new MapResourceBundle<>();
			bundle.put("label", text);
			final FXMLLoader loader = new FXMLLoader(fxml, bundle);

			final Stage popupStage = new Stage();
			popupStage.setTitle(title);
			final Runnable close = () -> {
				popupStage.close();
				if (onClose.isPresent()) {
					onClose.get().run();
				}
			};

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
				final Scene popupScene = new Scene(loader.<AnchorPane>load());
				popupStage.setScene(popupScene);
				popupStage.show();
			} catch (final IOException e) {
				// should never happen
				throw Throwables.propagate(e);
			}

		});

	}

	/**
	 * @param onClose the command executed on dialog close
	 */
	public Popup(Runnable onClose) {
		this.onClose = onClose;
	}

	/**
	 * @param event not used
	 */
	@FXML
	private void close(ActionEvent event) {
		onClose.run();
	}

}
