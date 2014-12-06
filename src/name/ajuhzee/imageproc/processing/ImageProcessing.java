/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * Workes as a library for image processing algorithms
 * 
 * @author Ajuhzee
 *
 */
public class ImageProcessing {

	/**
	 * Binarizes a given image.
	 * 
	 * @param oldImage
	 *            the image to be binarized
	 * @param startThreshold
	 *            the threshold for the binarization (can vary while function is running)
	 * @param threshold
	 *            a fix threshold to compute the binarized image
	 * @return the binarized image
	 */
	public static Image concurrentBinarize(Image oldImage, AtomicDouble threshold) {
		double startThreshold = threshold.get();
		double height = oldImage.getHeight();
		double width = oldImage.getWidth();
		WritableImage newImage = new WritableImage((int) width, (int) height);

		// alle Zeilen
		for (int y = 0; y < height; y++) {
			// alle Spalten
			for (int x = 0; x < width; x++) {
				Color c = oldImage.getPixelReader().getColor(x, y);
				double greyValue = c.getBrightness() * 256;
				if (greyValue <= startThreshold)
					newImage.getPixelWriter().setColor(x, y, Color.BLACK);
				else
					newImage.getPixelWriter().setColor(x, y, Color.WHITE);
			}
			if (threshold.get() != startThreshold) {
				return concurrentBinarize(oldImage, threshold);
			}
		}

		return newImage;
	}
}
