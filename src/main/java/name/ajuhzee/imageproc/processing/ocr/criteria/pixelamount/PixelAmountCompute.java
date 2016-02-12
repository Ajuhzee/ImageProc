package name.ajuhzee.imageproc.processing.ocr.criteria.pixelamount;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * Provides methods to compute the pixel amount of a black object.
 *
 * @author Ajuhzee
 */
public class PixelAmountCompute {

	/**
	 * Gets the amount of black pixels in the image.
	 *
	 * @param img the image to analyze
	 * @return the pixel amount
	 */
	public static int pixelAmount(Image img) {
		PixelReader reader = img.getPixelReader();

		int sum = 0;
		for (int y = 0; y < img.getHeight(); ++y) {
			for (int x = 0; x < img.getWidth(); ++x) {
				if (Color.BLACK.equals(reader.getColor(x, y))) {
					++sum;
				}
			}
		}

		return sum;
	}

}
