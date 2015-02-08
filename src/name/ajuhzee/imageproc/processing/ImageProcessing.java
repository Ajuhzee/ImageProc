/**
 *
 */
package name.ajuhzee.imageproc.processing;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.filters.FilterChain;
import name.ajuhzee.imageproc.processing.filters.FilterMask;

import com.google.common.util.concurrent.AtomicDouble;

/**
 * A library for image processing algorithms.
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
		BgraPreImageBuffer buffer = BgraPreImageBuffer.getBgraPreBuffer(toInvert);
		for (int pixelIdx = 0; pixelIdx != buffer.getPixelCount(); ++pixelIdx) {
			buffer.setRed(pixelIdx, 255 - buffer.getRed(pixelIdx));
			buffer.setGreen(pixelIdx, 255 - buffer.getGreen(pixelIdx));
			buffer.setBlue(pixelIdx, 255 - buffer.getBlue(pixelIdx));
		}

		int width = (int) toInvert.getWidth();
		int height = (int) toInvert.getHeight();
		return BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
	}

	/**
	 * Filters a given image with a given filter type by using one thread.
	 * 
	 * @param toFilter
	 *            the source image
	 * @param filterChain
	 *            a chain of filters that are going to be applied
	 * @return the filtered image
	 */
	public static Image filter(Image toFilter, FilterChain filterChain) {
		int startX = 0;
		int endX = (int) toFilter.getWidth();
		return filterPartial(toFilter, filterChain, startX, endX);
	}

	/**
	 * Filters a given image with a given filter type by using multiple threads.
	 * 
	 * @param toFilter
	 *            the source image
	 * @param filterChain
	 *            a chain of filters that are going to be applied
	 * @return the filtered image
	 */
	public static Image filterThreaded(Image toFilter, FilterChain filterChain) {
		return POOL.invoke(new FilterAction(toFilter, filterChain));
	}

	/**
	 * Filters a specisfic part of the image with given filter types.
	 * 
	 * @param toFilter
	 *            the source image
	 * @param filterChain
	 *            a chain of filters that are going to be applied
	 * @param startX
	 *            the column to start the filtering
	 * @param endX
	 *            the column to stop the filtering
	 * @return the filtered image
	 */
	public static Image filterPartial(Image toFilter, FilterChain filterChain, int startX, int endX) {
		WritableImage newImage = new WritableImage((int) toFilter.getWidth(), (int) toFilter.getHeight());
		List<FilterMask> masks = filterChain.getFilterMasks();
		applyFilterMask(toFilter, newImage, masks.get(0), startX, endX);

		for (int i = 1; i != masks.size(); ++i) {
			applyFilterMask(newImage, newImage, masks.get(i), startX, endX);
		}
		return newImage;

	}

	private static boolean isOutsideOfImage(Image toFilter, int x, int y) {
		return x < 0 || x >= toFilter.getWidth() || y < 0 || y >= toFilter.getHeight();
	}

	private static Color getPaddedColor(Image toFilter, int x, int y) {
		if (isOutsideOfImage(toFilter, x, y)) {
			return Color.BLACK;
		}

		return toFilter.getPixelReader().getColor(x, y);
	}

	private static void applyFilterMask(Image image, WritableImage newImage, FilterMask mask, int startX, int endX) {
		PixelWriter pixelWriter = newImage.getPixelWriter();
		int xRadius = mask.getKernelX() / 2;
		int yRadius = mask.getKernelY() / 2;

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = startX; x < endX; x++) {
				int newRedValue = 0;
				int newGreenValue = 0;
				int newBlueValue = 0;

				for (int maskX = -1 * xRadius; maskX <= xRadius; maskX++) {
					for (int maskY = -1 * yRadius; maskY <= yRadius; maskY++) {
						newRedValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getRed())
								* mask.getMultiplier(maskX, maskY);
						newGreenValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getGreen())
								* mask.getMultiplier(maskX, maskY);
						newBlueValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getBlue())
								* mask.getMultiplier(maskX, maskY);
					}
				}

				Color color = Color.rgb(Math.max(Math.min(newRedValue, 255), 0),
						Math.max(Math.min(newGreenValue, 255), 0), Math.max(Math.min(newBlueValue, 255), 0));

				pixelWriter.setColor(x, y, color);
			}

		}
	}
}
