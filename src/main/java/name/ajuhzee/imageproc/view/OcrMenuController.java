package name.ajuhzee.imageproc.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.image.process.Ocr;
import name.ajuhzee.imageproc.util.CallbackManager;
import name.ajuhzee.imageproc.util.Callbacks;

/**
 * Provides features specifically for the ocr plugin.
 * 
 * @author Ajuhzee
 *
 */
public class OcrMenuController implements NodeRepresentation {

	/**
	 * @return the OcrMenuController
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static OcrMenuController create() throws IOException {
		final URL fxml = Ocr.class.getClassLoader().getResource("OcrMenu.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<OcrMenuController> getController();
	}

	@FXML
	private AnchorPane ocrPane;

	@FXML
	private Button recognizeLine;

	@FXML
	private Button adjustLines;

	@FXML
	private Button seperateCharacters;

	@FXML
	private Button matchCharacters;

	@FXML
	private Button showOutput;

	@FXML
	private TextArea outputTextField;

	@FXML
	private Button ocrDoneButton;

	@Override
	public Node toNodeRepresentation() {
		return ocrPane;
	}

	private CallbackManager doneButtonCallbacks = new CallbackManager();

	private CallbackManager recognizeLineCallbacks = new CallbackManager();

	private CallbackManager adjustLinesCallbacks = new CallbackManager();

	private CallbackManager seperateCharactersCallbacks = new CallbackManager();

	private CallbackManager matchCharactersCallbacks = new CallbackManager();

	private CallbackManager showOutputCallbacks = new CallbackManager();

	/**
	 * @return the object with which to register to get called when the done button is pressed
	 */
	public Callbacks getDoneButtonCallbacks() {
		return doneButtonCallbacks;
	}

	/**
	 * @return the object with which to register to get called when the RecognizeLine button is pressed
	 */
	public Callbacks getRecognizeLineButtonCallbacks() {
		return recognizeLineCallbacks;
	}

	/**
	 * @return the object with which to register to get called when the AdjustLines button is pressed
	 */
	public Callbacks getAdjustLinesButtonCallbacks() {
		return adjustLinesCallbacks;
	}

	/**
	 * @return the object with which to register to get called when the SeperateCharacters button is pressed
	 */
	public Callbacks getSeperateCharactersButtonCallbacks() {
		return seperateCharactersCallbacks;
	}

	/**
	 * @return the object with which to register to get called when the MatchCharacters button is pressed
	 */
	public Callbacks getMatchCharactersButtonCallbacks() {
		return matchCharactersCallbacks;
	}

	/**
	 * @return the object with which to register to get called when the ShowOutput button is pressed
	 */
	public Callbacks getShowOutputButtonCallbacks() {
		return showOutputCallbacks;
	}

	/**
	 * Clears the content of the ocrPane.
	 */
	public void doneButtonPressed() {
		doneButtonCallbacks.executeCallbacks();
	}

	public void recognizeLinePressed() {
		recognizeLineCallbacks.executeCallbacks();
	}

	public void adjustLinesPressed() {
		adjustLinesCallbacks.executeCallbacks();
	}

	public void seperateCharactersPressed() {
		seperateCharactersCallbacks.executeCallbacks();
	}

	public void matchCharactersPressed() {
		matchCharactersCallbacks.executeCallbacks();
	}

	public void showOutputPressed() {
		showOutputCallbacks.executeCallbacks();
	}

}
