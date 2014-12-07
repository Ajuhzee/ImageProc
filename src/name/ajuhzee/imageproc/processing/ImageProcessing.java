/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

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
		int startThreshold = ((int) threshold.get()) * 3;
		int width = (int) oldImage.getWidth();
		int height = (int) oldImage.getHeight();
		WritableImage newImage = new WritableImage(width, height);
		PixelReader reader = oldImage.getPixelReader();
		PixelWriter writer = newImage.getPixelWriter();

		byte[] buffer = new byte[width * height * 4];
		reader.getPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width * 4);

		int remainingPixels = width % 4;
		int loopCount = width - remainingPixels;

		for (int y = 0; y != height; ++y) {
			int rowOffset = y * width * 4;
			for (int x = 0; x != loopCount; x += 4) {
				int pixelOffset = rowOffset + x * 4;

				// int blue = (buffer[pixelOffset + 0] & 0xFF);
				// int green = (buffer[pixelOffset + 1] & 0xFF);
				// int red = (buffer[pixelOffset + 2] & 0xFF);

				// int grey = toBrightnessPerceived(red, green, blue);
				// int grey = toBrightnessNTSCLuma(red, green, blue);
				// int grey = toBrightnessAverage(red, green, blue); // fastest

				if ((buffer[pixelOffset] & 0xFF) + (buffer[pixelOffset + 1] & 0xFF) + (buffer[pixelOffset + 2] & 0xFF) < startThreshold) {
					buffer[pixelOffset] = (byte) 0;
					buffer[pixelOffset + 1] = (byte) 0;
					buffer[pixelOffset + 2] = (byte) 0;
				} else {
					buffer[pixelOffset] = (byte) 0xFF;
					buffer[pixelOffset + 1] = (byte) 0xFF;
					buffer[pixelOffset + 2] = (byte) 0xFF;
				}
				if ((buffer[pixelOffset + 4] & 0xFF) + (buffer[pixelOffset + 5] & 0xFF)
						+ (buffer[pixelOffset + 6] & 0xFF) < startThreshold) {
					buffer[pixelOffset + 4] = (byte) 0;
					buffer[pixelOffset + 5] = (byte) 0;
					buffer[pixelOffset + 6] = (byte) 0;
				} else {
					buffer[pixelOffset + 4] = (byte) 0xFF;
					buffer[pixelOffset + 5] = (byte) 0xFF;
					buffer[pixelOffset + 6] = (byte) 0xFF;
				}
				if ((buffer[pixelOffset + 8] & 0xFF) + (buffer[pixelOffset + 9] & 0xFF)
						+ (buffer[pixelOffset + 10] & 0xFF) < startThreshold) {
					buffer[pixelOffset + 8] = (byte) 0;
					buffer[pixelOffset + 9] = (byte) 0;
					buffer[pixelOffset + 10] = (byte) 0;
				} else {
					buffer[pixelOffset + 8] = (byte) 0xFF;
					buffer[pixelOffset + 9] = (byte) 0xFF;
					buffer[pixelOffset + 10] = (byte) 0xFF;
				}
				if ((buffer[pixelOffset + 12] & 0xFF) + (buffer[pixelOffset + 13] & 0xFF)
						+ (buffer[pixelOffset + 14] & 0xFF) < startThreshold) {
					buffer[pixelOffset + 12] = (byte) 0;
					buffer[pixelOffset + 13] = (byte) 0;
					buffer[pixelOffset + 14] = (byte) 0;
				} else {
					buffer[pixelOffset + 12] = (byte) 0xFF;
					buffer[pixelOffset + 13] = (byte) 0xFF;
					buffer[pixelOffset + 14] = (byte) 0xFF;
				}
			}
			for (int x = width - remainingPixels; x != width; ++x) {
				int pixelOffset = rowOffset + x * 4;

				if ((buffer[pixelOffset + 0] & 0xFF) + (buffer[pixelOffset + 1] & 0xFF) + (buffer[pixelOffset + 2] & 0xFF) < startThreshold) {
					buffer[pixelOffset + 0] = (byte) 0;
					buffer[pixelOffset + 1] = (byte) 0;
					buffer[pixelOffset + 2] = (byte) 0;
				} else {
					buffer[pixelOffset + 0] = (byte) 0xFF;
					buffer[pixelOffset + 1] = (byte) 0xFF;
					buffer[pixelOffset + 2] = (byte) 0xFF;
				}
			}
			if (((int) threshold.get() * 3) != startThreshold) {
				return concurrentBinarize(oldImage, threshold);
			}
		}
		writer.setPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width * 4);

		return newImage;
	}

	private static int toBrightnessAverage(int red, int green, int blue) {
		return (red + green + blue) / 3;
	}

	private static int toBrightnessNTSCLuma(int red, int green, int blue) {
		return (int) (0.299 * red + 0.587 * green + 0.114 * blue);
	}

	private static int toBrightnessPerceived(int red, int green, int blue) {
		return (int) Math.sqrt(0.299 * red * red + 0.587 * green * green + 0.114 * blue * blue);
	}
}
