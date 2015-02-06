/**
 *
 */
package name.ajuhzee.imageproc.processing;

import java.util.concurrent.ForkJoinPool;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.filters.FilterActions;

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
	 * Binarizes a given image, dynamically changing its computation and result as the threshold changes.
	 *
	 * @param toBinarize
	 *            the image to be binarized
	 * @param threshold
	 *            the threshold for the binarization (can vary while function is running)
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

	/**
	 * Inverts a given image.
	 * 
	 * @param toInvert
	 *            the source image
	 * @return the inverted image
	 */
	public static Image invert(Image toInvert) {
		BgraPreImageBuffer buffer;

		while (true) {
			try {
				buffer = BgraPreImageBuffer.getBgraPreBuffer(toInvert);
				POOL.invoke(new InvertAction(buffer));
				break;
			} catch (ValueChangedException e) {
				continue;
			}
		}

		int width = (int) toInvert.getWidth();
		int height = (int) toInvert.getHeight();
		return BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
	}

	/**
	 * @param toFilter
	 *            the source image
	 * @param filterAction
	 *            the type of filter, that shall be applied
	 * @return the filtered image
	 */
	public static Image filter(Image toFilter, FilterActions filterAction) {
		while (true) {
			try {
				return POOL.invoke(filterAction.getFilterAction(toFilter));
			} catch (ValueChangedException e) {
				continue;
			}
		}
	}
}
