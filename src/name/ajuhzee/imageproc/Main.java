package name.ajuhzee.imageproc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Main extends Application {

	private Stage stage;

	@Override
	public void start(Stage stage) {
		try {
			AnchorPane mainScreen = (AnchorPane) FXMLLoader.load(Main.class.getResource("main.fxml"));
			Scene scene = new Scene(mainScreen);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("ImageProc");
			stage.setScene(scene);
			stage.setHeight(500);
			stage.show();
			this.stage = stage;
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

		FileChooser fc = new FileChooser();
		if (e.getSource() == loadImage) {
			File file = fc.showOpenDialog(null);
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
			FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
			FileChooser.ExtensionFilter extFilterBMP = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.BMP");
			fc.getExtensionFilters().addAll(extFilterJPG, extFilterPNG, extFilterBMP);

			try {
				BufferedImage img = ImageIO.read(file);
				Image image = SwingFXUtils.toFXImage(img, null);
				imageView.setImage(image);
			} catch (IOException ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		// imageView.fitWidthProperty().bind(mainPane.widthProperty());
		// imageView.fitHeightProperty().bind(mainPane.heightProperty());
		// mainPane.minWidth(imageView.getScene().getHeight());
		// stage.setHeight(imageView.getScene().getHeight());
		System.out.println("Test");
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
