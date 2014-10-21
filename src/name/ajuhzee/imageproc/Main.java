package name.ajuhzee.imageproc;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		try {
			AnchorPane mainScreen = (AnchorPane) FXMLLoader.load(Main.class.getResource("main.fxml"));
			Scene scene = new Scene(mainScreen);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("ImageProc");
			stage.setScene(scene);
			stage.show();
			Graphics.setMainStage(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	private AnchorPane mainPane;

	@FXML
	private javafx.scene.control.MenuItem closeButton;

	@FXML
	private javafx.scene.control.MenuItem loadImage;

	@FXML
	private javafx.scene.control.MenuItem loadTestImageSlow;

	@FXML
	private javafx.scene.control.MenuItem loadTestImageFast;

	@FXML
	private javafx.scene.control.MenuItem invertImage;

	@FXML
	private javafx.scene.control.MenuItem binarizeImage;

	@FXML
	private javafx.scene.control.MenuItem timeMessure;

	@FXML
	private javafx.scene.control.MenuItem filterImage;

	@FXML
	private ImageView imageView;

	@FXML
	private void closeButtonAction() {
		((Stage) mainPane.getScene().getWindow()).close();
	}

	@FXML
	private void loadImageAction(ActionEvent e) {
		ImageLoader.loadFromFile();
	}

	public void setImage(Image img) {
		imageView.setImage(img);
	}

	@FXML
	private void loadTestImageSlowAction() {
		JOptionPane.showMessageDialog(null, "Eggs aren't supposed to be green.");
	}

	@FXML
	private void loadTestImageFastAction() {
		JOptionPane.showMessageDialog(null, "Eggs aren't supposed to be green.");
	}

	@FXML
	private void invertImageAction() {
		JOptionPane.showMessageDialog(null, "Eggs aren't supposed to be green.");
	}

	@FXML
	private void binarizeImageAction() {
		JOptionPane.showMessageDialog(null, "Eggs aren't supposed to be green.");
	}

	@FXML
	private void filterImageAction() {
		JOptionPane.showMessageDialog(null, "Eggs aren't supposed to be green.");
	}

	@FXML
	private void timeMessureAction() {
		JOptionPane.showMessageDialog(null, "Eggs aren't supposed to be green.");
	}
}
