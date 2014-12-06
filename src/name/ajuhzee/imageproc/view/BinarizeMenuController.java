package name.ajuhzee.imageproc.view;

import java.io.IOException;
import java.net.URL;

import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
		final URL fxml = Binarize.class.getClassLoader().getResource("name/ajuhzee/imageproc/view/BinarizeMenu.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<BinarizeMenuController> getController();
	}

	@FXML
	private Slider binarizeThresholdSlider;

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
}
