package name.ajuhzee.imageproc.view;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import name.ajuhzee.imageproc.plugin.control.ImageControl;

/**
 * Controls image displaying and changing
 * 
 * @author Ajuhzee
 */
public class ImageDisplay implements NodeRepresentation, ImageControl {

	/**
	 * Creates the image view.
	 * 
	 * @return the image view.
	 * @throws IOException
	 *             if the fxml could not be loaded
	 */
	public static ImageDisplay create() throws IOException {
		final FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ImageDisplay.class.getResource("ImageDisplay.fxml"));
		loader.load();

		return loader.<ImageDisplay> getController();
	}

	@FXML
	private ImageView imageView;

	@FXML
	private AnchorPane mainPane;

	@Override
	public Image getImage() {
		return imageView.getImage();
	}

	@Override
	public void showImage(Image img) {
		checkNotNull(img);
		Platform.runLater(() -> imageView.setImage(img));
	}

	@Override
	public Node toNodeRepresentation() {
		return mainPane;
	}

}
