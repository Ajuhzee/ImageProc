package name.ajuhzee.imageproc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import name.ajuhzee.imageproc.util.CallbackManager;
import name.ajuhzee.imageproc.util.Callbacks;

import java.io.IOException;
import java.net.URL;

/**
 * Publishes data from the view layer to the LearnCharacterSet plugin
 *
 * @author Ajuhzee
 */
public class LearnCharacterSetController implements NodeRepresentation {

	@FXML
	private GridPane rootPane;

	@FXML
	private TextField minGap;

	private CallbackManager cancelCallbacks = new CallbackManager();

	private CallbackManager learnCharacterSetCallbacks = new CallbackManager();

	private CallbackManager testCharSeparationCallbacks = new CallbackManager();

	/**
	 * @return the OcrMenuController
	 * @throws IOException if an I/O error occurs
	 */
	public static LearnCharacterSetController create() throws IOException {
		final URL fxml = LearnCharacterSetController.class.getClassLoader().getResource("LearnCharacterSet.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.getController();
	}

	/**
	 * @return the minimum gap between the characters (in px)
	 */
	public int getMinimumCharacterGapPx() {
		return Integer.parseInt(minGap.textProperty().getValue());
	}

	/**
	 * Executes the callbacks when the learn button is pressed
	 */
	public void learnCharacterSetPressed() {
		learnCharacterSetCallbacks.executeCallbacks();
	}

	/**
	 * @return the object with which to register to get called when the learnCharacterSet button is pressed
	 */
	public Callbacks getLearnCharacterSetPressedCallbacks() {
		return learnCharacterSetCallbacks;
	}

	/**
	 * Executes the callbacks when the cancel button is pressed
	 */
	public void cancelPressed() {
		cancelCallbacks.executeCallbacks();
	}

	/**
	 * @return the object with which to register to get called when the cancel button is pressed
	 */
	public Callbacks getCancelCallbacks() {
		return cancelCallbacks;
	}

	/**
	 * Executes the callbacks when the test button is pressed
	 */
	public void testCharSeparationPressed() {
		testCharSeparationCallbacks.executeCallbacks();
	}


	/**
	 * @return the object with which to register to get called when the testCharSeparation button is pressed
	 */
	public Callbacks getTestCharSeparationPressedCallbacks() {
		return testCharSeparationCallbacks;
	}

	@Override
	public Node toNodeRepresentation() {
		return rootPane;
	}
}
