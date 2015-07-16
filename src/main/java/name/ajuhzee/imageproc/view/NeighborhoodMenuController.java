package name.ajuhzee.imageproc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NeighborhoodMenuController implements NodeRepresentation {

	private final List<Runnable> okCallbacks = new ArrayList<>();

	private final List<Runnable> radio4Callbacks = new ArrayList<>();

	private final List<Runnable> radio8Callbacks = new ArrayList<>();

	@FXML
	private AnchorPane neighborhoodPane;

	@FXML
	private Button okButton;

	@FXML
	private ToggleGroup neighborhood;

	@FXML
	private RadioButton radio8;

	@FXML
	private RadioButton radio4;


	/**
	 * @return the BinarizeMenuController
	 * @throws IOException if an I/O error occurs
	 */
	public static NeighborhoodMenuController create() throws IOException {
		final URL fxml = Binarize.class.getClassLoader().getResource("Neighborhood.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<NeighborhoodMenuController>getController();
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
	public void addRadio4PressedCallback(Runnable runnable) {
		radio4Callbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void radio4Pressed() {
		for (Runnable elem : radio4Callbacks) {
			elem.run();
		}
	}

	/**
	 * Registers if the ok button was pressed and adds a runnable to a list.
	 *
	 * @param runnable
	 */
	public void addRadio8PressedCallback(Runnable runnable) {
		radio8Callbacks.add(runnable);
	}

	/**
	 * Clears the content of the binarizePane.
	 */
	public void radio8Pressed() {
		for (Runnable elem : radio8Callbacks) {
			elem.run();
		}
	}
}
