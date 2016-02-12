package name.ajuhzee.imageproc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;
import name.ajuhzee.imageproc.processing.Neighborhood;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides features specifically for the open and close plugin.
 *
 * @author Ajuhzee
 */
public class OpenCloseMenuController implements NodeRepresentation {

	private final List<Runnable> okCallbacks = new ArrayList<>();

	private final List<Runnable> openCallbacks = new ArrayList<>();

	private final List<Runnable> closeCallbacks = new ArrayList<>();


	@FXML
	private GridPane neighborhoodPane;

	@FXML
	private RadioButton radio8;

	@FXML
	private RadioButton radio4;

	@FXML
	private Button closeButton;

	@FXML
	private Button okButton;

	@FXML
	private Slider repeatCount;

	@FXML
	private ToggleGroup neighborhood;

	@FXML
	private Button openButton;

	/**
	 * Creates the OpenCloseMenuController.
	 *
	 * @return the BinarizeMenuController
	 * @throws IOException if an I/O error occurs
	 */
	public static OpenCloseMenuController create() throws IOException {
		final URL fxml = Binarize.class.getClassLoader().getResource("OpenClose.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<OpenCloseMenuController>getController();
	}

	@Override
	public Node toNodeRepresentation() {
		return neighborhoodPane;
	}

	public int getRepeatCount() {
		return (int) repeatCount.getValue();
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
	public void addClosePressedCallback(Runnable runnable) {
		closeCallbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void closePressed() {
		for (Runnable elem : closeCallbacks) {
			elem.run();
		}
	}

	/**
	 * Registers if the ok button was pressed and adds a runnable to a list.
	 *
	 * @param runnable
	 */
	public void addOpenPressedCallback(Runnable runnable) {
		openCallbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void openPressed() {
		for (Runnable elem : openCallbacks) {
			elem.run();
		}
	}

	/**
	 * Checks which neighborhood checkbox is checked.
	 * @return the neighborhood
	 */
	public Neighborhood getNeighborhoodStatus() {
		return neighborhood.getSelectedToggle().equals(radio4) ? Neighborhood.NEIGHBORHOOD4 :
				Neighborhood.NEIGHBORHOOD8;
	}
}
