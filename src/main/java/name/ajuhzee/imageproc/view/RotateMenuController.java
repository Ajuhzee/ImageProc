package name.ajuhzee.imageproc.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;
import name.ajuhzee.imageproc.util.CallbackManager;

import java.io.IOException;
import java.net.URL;

/**
 * Publishes data from the view layer to the Rotate plugin
 *
 * @author Ajuhzee
 */
public class RotateMenuController implements NodeRepresentation {

	/**
	 * Creates the BinarizeMenuController
	 *
	 * @return the BinarizeMenuController
	 * @throws IOException if an I/O error occurs
	 */
	public static RotateMenuController create() throws IOException {
		final URL fxml = Binarize.class.getClassLoader().getResource("RotateMenu.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<RotateMenuController>getController();
	}

	@FXML
	private Slider rotateAngleSlider;


	@FXML
	private ColorPicker fillColor;

	@FXML
	private GridPane rotatePane;


	private CallbackManager okButtonCallbacks = new CallbackManager();
	private CallbackManager cancelButtonCallbacks = new CallbackManager();

	/**
	 * @return the slider value
	 */
	public DoubleProperty getSliderValue() {
		return rotateAngleSlider.valueProperty();
	}

	/**
	 * @return the color picker value
	 */
	public ObjectProperty<Color> getColorPickerValue() {
		return fillColor.valueProperty();
	}


	@Override
	public Node toNodeRepresentation() {
		return rotatePane;
	}

	/**
	 * Adds a runnable to the list of functions to be executed when the OK-button is pressed
	 *
	 * @param runnable
	 */
	public void addOkButtonPressedCallback(Runnable runnable) {
		okButtonCallbacks.addCallback(runnable);
	}

	/**
	 * Adds a runnable to the list of functions to be executed when the Cancel-button is pressed
	 *
	 * @param runnable
	 */
	public void addCancelButtonPressedCallback(Runnable runnable) {
		cancelButtonCallbacks.addCallback(runnable);
	}

	/**
	 * Calls the functions listening for an OK-button press
	 */
	public void okButtonPressed() {
		okButtonCallbacks.executeCallbacks();
	}

	/**
	 * Calls the functions listening for an Cancel-button press
	 */
	public void cancelButtonPressed() {
		cancelButtonCallbacks.executeCallbacks();
	}

}
