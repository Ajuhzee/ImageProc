package name.ajuhzee.imageproc.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.image.process.Ocr;

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
	private Button stepOneButton;

	@FXML
	private Button stepTwoButton;

	@FXML
	private Button stepThreeButton;

	@FXML
	private Button stepFourButton;

	@FXML
	private Button stepFiveButton;

	@FXML
	private TextArea outputTextField;

	@FXML
	private Button ocrDoneButton;

	@Override
	public Node toNodeRepresentation() {
		return ocrPane;
	}

	private final List<Runnable> runnables = new ArrayList<>();

	/**
	 * Registers if the done button was pressed and adds a runnable to a list.
	 * 
	 * @param runnable
	 */
	public void addDoneButtonPressedCallback(Runnable runnable) {
		runnables.add(runnable);
	}

	/**
	 * Clears the content of the ocrPane.
	 */
	public void doneButtonPressed() {
		for (Runnable elem : runnables) {
			elem.run();
		}
	}

}
