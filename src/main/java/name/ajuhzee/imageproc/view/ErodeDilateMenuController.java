package name.ajuhzee.imageproc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;
import name.ajuhzee.imageproc.processing.Neighborhood;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Publishes data from the view layer to the Erode & Dilate plugin
 *
 * @author Ajuhzee
 */
public class ErodeDilateMenuController implements NodeRepresentation {

	private final List<Runnable> okCallbacks = new ArrayList<>();

	private final List<Runnable> dilateCallbacks = new ArrayList<>();

	private final List<Runnable> erodeCallbacks = new ArrayList<>();


	@FXML
	private AnchorPane neighborhoodPane;

	@FXML
	private RadioButton radio8;

	@FXML
	private RadioButton radio4;

	@FXML
	private Button erodeButton;

	@FXML
	private Button okButton;

	@FXML
	private ToggleGroup neighborhood;

	@FXML
	private Button dilateButton;

	/**
	 * Creates the BinarizeMenuController
	 * @return the BinarizeMenuController
	 * @throws IOException if an I/O error occurs
	 */
	public static ErodeDilateMenuController create() throws IOException {
		final URL fxml = Binarize.class.getClassLoader().getResource("ErodeDilate.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<ErodeDilateMenuController>getController();
	}

	@Override
	public Node toNodeRepresentation() {
		return neighborhoodPane;
	}


	/**
	 * Registers if the ok button was pressed and adds a runnable to a list.
	 *
	 * @param runnable
	 */
	public void addOkButtonPressedCallback(Runnable runnable) {
		okCallbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void okButtonPressed() {
		for (Runnable elem : okCallbacks) {
			elem.run();
		}
	}

	/**
	 * Registers if the ok button was pressed and adds a runnable to a list.
	 *
	 * @param runnable
	 */
	public void addErodePressedCallback(Runnable runnable) {
		erodeCallbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void erodePressed() {
		for (Runnable elem : erodeCallbacks) {
			elem.run();
		}
	}

	/**
	 * Registers if the ok button was pressed and adds a runnable to a list.
	 *
	 * @param runnable
	 */
	public void addDilatePressedCallback(Runnable runnable) {
		dilateCallbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void dilatePressed() {
		for (Runnable elem : dilateCallbacks) {
			elem.run();
		}
	}

	/**
	 *
	 * @return the neighbourhood
	 */
	public Neighborhood getNeighborhoodStatus() {
		return neighborhood.getSelectedToggle().equals(radio4) ? Neighborhood.NEIGHBORHOOD4 :
				Neighborhood.NEIGHBORHOOD8;
	}
}
