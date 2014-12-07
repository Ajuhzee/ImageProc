/**
 *
 */
package name.ajuhzee.imageproc.processing;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * Workes as a library for image processing algorithms
 *
 * @author Ajuhzee
 *
 */
public class ImageProcessing {

	/**
	 * Represents an image buffer with the BGRA-premultiplied format
	 * 
	 * @see PixelFormat.Type#BYTE_BGRA_PRE
	 * @see PixelFormat#getByteBgraPreInstance()
	 */
	private static class BgraPreImageBuffer {

		private final byte[] buffer;

		private final int height;

		private final int width;

		public BgraPreImageBuffer(int width, int height, byte[] buffer) {
			checkArgument(width * height * BGRA_PRE_BYTES_PER_PIXEL == buffer.length,
					"Width and height do not correspond to the given buffer");
			this.width = width;
			this.height = height;
			this.buffer = buffer;
		}

		public int getBlue(int idx) {
			return buffer[idx * BGRA_PRE_BYTES_PER_PIXEL] & 0xFF;
		}

		public int getGreen(int idx) {
			return buffer[idx * BGRA_PRE_BYTES_PER_PIXEL + 1] & 0xFF;
		}

		public int getRed(int idx) {
			return buffer[idx * BGRA_PRE_BYTES_PER_PIXEL + 2] & 0xFF;
		}

		public void setBlue(int idx, int value) {
			buffer[idx * BGRA_PRE_BYTES_PER_PIXEL] = (byte) value;
		}

		public void setGreen(int idx, int value) {
			buffer[idx * BGRA_PRE_BYTES_PER_PIXEL + 1] = (byte) value;
		}

		public void setRed(int idx, int value) {
			buffer[idx * BGRA_PRE_BYTES_PER_PIXEL + 2] = (byte) value;
		}

		/**
		 * Creates an image from the values of this buffer.
		 * 
		 * @return the new image
		 */
		public WritableImage createBgraPreImage() {
			final WritableImage newImage = new WritableImage(width, height);
			final PixelWriter writer = newImage.getPixelWriter();
			writer.setPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width
					* BGRA_PRE_BYTES_PER_PIXEL);
			return newImage;
		}
	}

	private static final int BGRA_PRE_BYTES_PER_PIXEL = 4;

	/**
	 * This number of threads performs faster than the actual processor count
	 */
	private static final int THREADS = 64; // Runtime.getRuntime().availableProcessors();

	private static final ThreadFactory BINARIZE_NAMES = new ThreadFactoryBuilder().setNameFormat("binarize-%d").build();

	private static final ExecutorService POOL = Executors.newFixedThreadPool(THREADS, BINARIZE_NAMES);

	/**
	 * Binarizes a given image.
	 *
	 * @param toBinarize
	 *            the image to be binarized
	 * @param threshold
	 *            the threshold for the binarization (can vary while function is running)
	 * @return the binarized image
	 */
	public static Image concurrentBinarize(Image toBinarize, AtomicDouble threshold) {
		final int startThreshold = (int) threshold.get();
		final int width = (int) toBinarize.getWidth();
		final int height = (int) toBinarize.getHeight();

		final BgraPreImageBuffer buffer = getBgraPreBuffer(toBinarize);

		final int numComputations = THREADS;
		final List<Future<?>> computations = new ArrayList<>(numComputations);
		for (int i = 0; i != numComputations; ++i) {
			final int numberOfPixels = width * height;
			final int pixelsPerPartition = numberOfPixels / numComputations;
			final int curPartitionStartIdx = i * pixelsPerPartition;
			final int curPartitionEndIdx;

			// last partition always goes to the last pixel
			final boolean isLastPartition = (i + 1) == numComputations;
			if (isLastPartition) {
				curPartitionEndIdx = numberOfPixels;
			} else {
				curPartitionEndIdx = (i + 1) * pixelsPerPartition;
			}

			final Runnable curPartitionJob = () -> {
				for (int pixelIdx = curPartitionStartIdx; pixelIdx != curPartitionEndIdx; ++pixelIdx) {
					final int red = buffer.getRed(pixelIdx);
					final int green = buffer.getGreen(pixelIdx);
					final int blue = buffer.getBlue(pixelIdx);

					// other brightness calculations possible
					// final int brightness = toBrightnessPerceived(red, green, blue);
					// final int brightness = toBrightnessNTSCLuma(red, green, blue);
					final int brightness = toBrightnessAverage(red, green, blue);

					if (brightness < startThreshold) {
						buffer.setRed(pixelIdx, 0);
						buffer.setGreen(pixelIdx, 0);
						buffer.setBlue(pixelIdx, 0);
					} else {
						buffer.setRed(pixelIdx, 0xFF);
						buffer.setGreen(pixelIdx, 0xFF);
						buffer.setBlue(pixelIdx, 0xFF);
					}

					final boolean thresholdChangedByUser = (int) threshold.get() != startThreshold;
					if (thresholdChangedByUser) {
						return;
					}
				}
			};
			computations.add(POOL.submit(curPartitionJob));
		}

		waitFor(computations);

		final boolean thresholdChangedByUser = (int) threshold.get() != startThreshold;
		if (thresholdChangedByUser) {
			return concurrentBinarize(toBinarize, threshold);
		}

		return buffer.createBgraPreImage();
	}

	/**
	 * Gets the BGRA-premultiplied buffer out of the image.
	 * 
	 * @param img
	 * @return the BGRA premultiplied buffer
	 */
	private static BgraPreImageBuffer getBgraPreBuffer(Image img) {
		final int width = (int) img.getWidth();
		final int height = (int) img.getHeight();
		final byte[] buffer = new byte[width * height * BGRA_PRE_BYTES_PER_PIXEL];

		final PixelReader reader = img.getPixelReader();
		reader.getPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width
				* BGRA_PRE_BYTES_PER_PIXEL);
		return new BgraPreImageBuffer(width, height, buffer);
	}

	/**
	 * Simple brightness by taking the average of the RGB values
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return brightness
	 */
	private static int toBrightnessAverage(int red, int green, int blue) {
		return (red + green + blue) / 3;
	}

	/**
	 * @param red
	 * @param green
	 * @param blue
	 * @return the NTSC luma value
	 * @see <a href="https://en.wikipedia.org/wiki/Luma_%28video%29">Wikipedia: Luma(Video)</a>
	 */
	@SuppressWarnings("unused")
	private static int toBrightnessNTSCLuma(int red, int green, int blue) {
		return (int) (0.299 * red + 0.587 * green + 0.114 * blue);
	}

	/**
	 * A more accurate human-perceived brightness. <br />
	 * <b>Expensive computation</b>
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 * @return the perceived brightness value
	 */
	@SuppressWarnings("unused")
	private static int toBrightnessPerceived(int red, int green, int blue) {
		return (int) Math.sqrt(0.299 * red * red + 0.587 * green * green + 0.114 * blue * blue);
	}

	/**
	 * Waits for the completion of all the futures in the list
	 * 
	 * @param computations
	 */
	private static void waitFor(List<Future<?>> computations) {
		for (final Future<?> computation : computations) {
			while (true) { // helper loop to continue waiting on interrupt
				try {
					computation.get();
				} catch (final InterruptedException e) {
					continue; // continue waiting when interrupted
				} catch (final ExecutionException e) {
					// should never happen
					throw new RuntimeException(e);
				}
				break;
			}
		}
	}
}
