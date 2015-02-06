package name.ajuhzee.imageproc.generating;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * A library for image generating algorithms
 *
 * @author Ajuhzee
 *
 */
public class ImageGenerating {

	private static final int DIMENSION = 512;

	/**
	 * Generates a test image.
	 * 
	 * @return the generated image
	 */
	public static Image generate() {

		WritableImage newImage = new WritableImage(DIMENSION, DIMENSION);

		for (int x = 0; x < newImage.getWidth(); x++) {
			for (int y = 0; y < newImage.getHeight(); y++) {
				Color color = Color.rgb(x / 2, x / 2, x / 2);
				newImage.getPixelWriter().setColor(x, y, color);
			}
		}
		return newImage;
	}
}
