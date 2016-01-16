package name.ajuhzee.imageproc.view;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;
import name.ajuhzee.imageproc.util.CallbackManager;

import java.io.IOException;
import java.net.URL;

/**
 * Provides features specifically for the binarize plugin.
 *
 * @author Ajuhzee
 *
 */
public class BinarizeMenuController implements NodeRepresentation {

	/**
	 * @return the BinarizeMenuController
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static BinarizeMenuController create() throws IOException {
		final URL fxml = Binarize.class.getClassLoader().getResource("BinarizeMenu.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<BinarizeMenuController> getController();
	}

	@FXML
	private Slider binarizeThresholdSlider;

	private CallbackManager okButtonCallbacks = new CallbackManager();
	private CallbackManager cancelButtonCallbacks = new CallbackManager();

	/**
	 * @return the slider value
	 */
	public DoubleProperty getSliderValue() {
		return binarizeThresholdSlider.valueProperty();
	}

	@FXML
	private AnchorPane binarizePane;

	@Override
	public Node toNodeRepresentation() {
		return binarizePane;
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
