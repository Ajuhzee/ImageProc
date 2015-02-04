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
	 * @param type
	 *            the type of filter, that shall be applied
	 * @return the filtered image
	 */
	public static Image filter(Image toFilter, String type) {
		double[] filterMask1;
		double[] filterMask2;
		double c;
		boolean isThreaded = false;
		int kernelX1;
		int kernelY1;
		int kernelX2;
		int kernelY2;

		switch (type) {
		default:
			filterMask1 = new double[] {0, 0, 0, 0, 1, 0, 0, 0, 0};
			filterMask2 = null;
			kernelX1 = 3;
			kernelY1 = 3;
			kernelX2 = 0;
			kernelY2 = 0;
			break;
		case "mean3x3":
			c = 1d / 9d;
			filterMask1 = new double[] {c, c, c, c, c, c, c, c, c};
			filterMask2 = null;
			kernelX1 = 3;
			kernelY1 = 3;
			kernelX2 = 0;
			kernelY2 = 0;
			break;
		case "mean3x3seperated":
			c = 1d / 3d;
			filterMask1 = new double[] {c, c, c};
			filterMask2 = new double[] {c, c, c};
			kernelX1 = 3;
			kernelY1 = 1;
			kernelX2 = 1;
			kernelY2 = 3;
			break;
		case "mean3x3threaded":
			c = 1d / 9d;
			filterMask1 = new double[] {c, c, c, c, c, c, c, c, c};
			filterMask2 = null;
			isThreaded = true;
			kernelX1 = 3;
			kernelY1 = 3;
			kernelX2 = 0;
			kernelY2 = 0;
			break;
		case "laplace":
			c = 1d / 4d;
			filterMask1 = new double[] {c * 0, c * 1, c * 01, c * 1, c * -4, c * 1, c * 0, c * 1, c * 0};
			filterMask2 = null;
			isThreaded = true;
			kernelX1 = 3;
			kernelY1 = 3;
			kernelX2 = 0;
			kernelY2 = 0;
			break;
		}

		while (true) {
			try {
				return POOL.invoke(new FilterAction(toFilter, filterMask1, filterMask2, kernelX1, kernelY1, kernelX2,
						kernelY2, isThreaded));
			} catch (ValueChangedException e) {
				continue;
			}
		}
	}
}
