package name.ajuhzee.imageproc.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;

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

	@FXML
	private Button binarizeOkButton;

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

	private List<Runnable> runnables = new ArrayList<>();

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
		// andere bearbeitungen wieder zulassen
		for (Runnable elem : runnables) {
			elem.run();
		}
	}

}
