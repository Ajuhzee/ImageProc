/**
 *
 */
package name.ajuhzee.imageproc.processing;

import java.util.concurrent.ForkJoinPool;

import javafx.scene.image.Image;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * Workes as a library for image processing algorithms
 *
 * @author Ajuhzee
 *
 */
public class ImageProcessing {

	private static final ForkJoinPool POOL = new ForkJoinPool();

	/**
	 * Binarizes a given image, dynamically changing its computation and result
	 * as the threshold changes.
	 *
	 * @param toBinarize
	 *            the image to be binarized
	 * @param threshold
	 *            the threshold for the binarization (can vary while function is
	 *            running)
	 * @return the binarized image
	 */
	public static Image binarizeDynamic(Image toBinarize, AtomicDouble threshold) {
		BgraPreImageBuffer buffer;
		while (true) {
			try {
				buffer = BgraPreImageBuffer.getBgraPreBuffer(toBinarize);
				POOL.invoke(new BinarizeAction(buffer, threshold));
				break;
			} catch (ValueChangedException e) {
				continue;
			}
		}

		int width = (int) toBinarize.getWidth();
		int height = (int) toBinarize.getHeight();
		return BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
	}

}
