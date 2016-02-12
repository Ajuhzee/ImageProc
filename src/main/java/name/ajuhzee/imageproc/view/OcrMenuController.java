package name.ajuhzee.imageproc.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.util.CallbackManager;
import name.ajuhzee.imageproc.util.Callbacks;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Provides features specifically for the ocr plugin.
 *
 * @author Ajuhzee
 */
public class OcrMenuController implements NodeRepresentation {

	@FXML
	private AnchorPane ocrPane;

	@FXML
	private TextField dimensionPixelDeviationPercent;

	@FXML
	private CheckBox dimensionPixelDeviation;

	@FXML
	private CheckBox pixelAmountDeviation;

	@FXML
	private TextField minGap;

	@FXML
	private TextField pixelAmountDeviationPercent;

	@FXML
	private TextField dimensionPixelDeviationAllowed;

	@FXML
	private TextArea outputTextField;

	@FXML
	private CheckBox shouldAdjustImage;

	@FXML
	private Button adjustImage;

	@FXML
	private CheckBox eulerNumber;

	@FXML
	private TextField pixelAmountDeviationAllowed;

	private CallbackManager doneButtonCallbacks = new CallbackManager();

	private CallbackManager selectCharacterSetCallbacks = new CallbackManager();

	private CallbackManager recognizeLineCallbacks = new CallbackManager();

	private CallbackManager adjustImageCallbacks = new CallbackManager();

	private CallbackManager seperateCharactersCallbacks = new CallbackManager();

	private CallbackManager matchCharactersCallbacks = new CallbackManager();

	private CallbackManager showOutputCallbacks = new CallbackManager();

	/**
	 * Creates the OcrMenuController
	 *
	 * @return the OcrMenuController
	 * @throws IOException if an I/O error occurs
	 */
	public static OcrMenuController create() throws IOException {
		final URL fxml = OcrMenuController.class.getClassLoader().getResource("OcrMenu.fxml");
		final FXMLLoader loader = new FXMLLoader(fxml);

		loader.load();
		return loader.<OcrMenuController>getController();
	}

	@Override
	public Node toNodeRepresentation() {
		return ocrPane;
	}

	/**
	 * Sets the text of the textfield.
	 * @param text
	 */
	public void setText(String text) {
		outputTextField.setText(text);
	}

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
	 * @return the object with which to register to get called when the selectCharacterSet button is pressed
	 */
	public Callbacks getSelectCharacterSetButtonCallbacks() {
		return selectCharacterSetCallbacks;
	}

	/**
	 * @return the object with which to register to get called when the AdjustLines button is pressed
	 */
	public Callbacks getAdjustImageButtonCallbacks() {
		return adjustImageCallbacks;
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
	/**
	 * Opens the file chooser.
	 */
	public void selectCharacterSetPressed() {
		selectCharacterSetCallbacks.executeCallbacks();
	}
	/**
	 * Recognizes lines.
	 */
	public void recognizeLinePressed() {
		recognizeLineCallbacks.executeCallbacks();
	}
	/**
	 * Adjusts the image.
	 */
	public void adjustImagePressed() {
		adjustImageCallbacks.executeCallbacks();
	}
	/**
	 * Separates characters.
	 */
	public void seperateCharactersPressed() {
		seperateCharactersCallbacks.executeCallbacks();
	}
	/**
	 * Matches characters.
	 */
	public void matchCharactersPressed() {
		matchCharactersCallbacks.executeCallbacks();
	}
	/**
	 * Disables the AdjustImage function for the ocr feature.
	 */
	public void shouldAdjustImageClicked() {
		adjustImage.disableProperty().set(!shouldAdjustImage());
	}
	/**
	 * Shows the output of the ocr feature.
	 */
	public void showOutputPressed() {
		showOutputCallbacks.executeCallbacks();
	}
	/**
	 * Checks if the pixel deviation criterion is enabled.
	 */
	public boolean isPixelDeviationEnabled() {
		return pixelAmountDeviation.selectedProperty().getValue();
	}
	/**
	 * Checks if the dimension deviation criterion is enabled.
	 */
	public boolean isDimensionDeviationEnabled() {
		return dimensionPixelDeviation.selectedProperty().getValue();
	}
	/**
	 * Checks if the euler number criterion is enabled.
	 */
	public boolean isEulerNumberEnabled() {
		return eulerNumber.selectedProperty().getValue();
	}
	/**
	 * Calculates the pixel deviation and returns it.
	 */
	public double getPixelDeviationPercent() {
		return parsePercentString(pixelAmountDeviationPercent.textProperty().getValue());
	}
	/**
	 * Calculates the dimension deviation and returns it.
	 */
	public double getDimensionDeviationPercent() {
		return parsePercentString(dimensionPixelDeviationPercent.textProperty().getValue());
	}
	/**
	 * Checks if the amount of dimension deviation is allowed.
	 */
	public int getDimensionDeviationAllowed() {
		try {
			return NumberFormat.getIntegerInstance().parse(dimensionPixelDeviationAllowed.textProperty().getValue())
					.intValue();
		} catch (ParseException e) {
			throw new ValidationException();
		}
	}

	/**
	 * Checks if the amount of pixel deviation is allowed.
	 */
	public int getPixelDeviationAllowed() {
		try {
			return NumberFormat.getIntegerInstance().parse(pixelAmountDeviationAllowed.textProperty().getValue())
					.intValue();
		} catch (ParseException e) {
			throw new ValidationException(e);
		}
	}


	private double parsePercentString(String percentString) {
		try {
			return DecimalFormat.getPercentInstance(Locale.GERMAN).parse(percentString).doubleValue();
		} catch (ParseException e) {
			// ignore
		}
		try {
			return DecimalFormat.getPercentInstance(Locale.US).parse(percentString).doubleValue();
		} catch (ParseException e) {
			// ignore
		}
		try {
			return DecimalFormat.getNumberInstance(Locale.GERMAN).parse(percentString).doubleValue();
		} catch (ParseException e) {
			// ignore
		}
		try {
			return DecimalFormat.getNumberInstance(Locale.US).parse(percentString).doubleValue();
		} catch (ParseException e) {
			// ignore
		}

		throw new ValidationException("Could not parse the string");
	}

	/**
	 * Checks if the image is supposed to be adjusted.
	 * @return
	 */
	public boolean shouldAdjustImage() {
		return shouldAdjustImage.isSelected();
	}

	/**
	 * @return the minimum gap between characters (in px)
	 */
	public int getMinimumCharacterGapPx() {
		return Integer.parseInt(minGap.textProperty().getValue());
	}
}
