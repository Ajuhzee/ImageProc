package name.ajuhzee.imageproc.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Publishes data from the view layer to the Rotate plugin
 *
 * @author Ajuhzee
 */
public class RotateMenuController implements NodeRepresentation {

	/**
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
	private Button rotateOkButton;


	@FXML
	private ColorPicker fillColor;

	@FXML
	private GridPane rotatePane;

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

	private final List<Runnable> runnables = new ArrayList<>();

	/**
	 * Registers if the ok button was pressed and adds a runnable to a list.
	 *
	 * @param runnable
	 */
	public void addOkButtonPressedCallback(Runnable runnable) {
		runnables.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void okButtonPressed() {
		for (Runnable elem : runnables) {
			elem.run();
		}
	}

}
