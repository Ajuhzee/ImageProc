package name.ajuhzee.imageproc.processing;

import com.google.common.util.concurrent.AtomicDouble;

import java.util.concurrent.RecursiveAction;

/**
 * Binarizes the image.
 *
 * @author Ajuhzee
 *
 */
public class BinarizeAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private static final int MAX_WORK_SIZE = 100_000;

	private final AtomicDouble threshold;

	private final int startThreshold;

	private final BgraPreImageBuffer buffer;

	private final int startIdx;

	private final int endIdx;

	private BinarizeAction(BgraPreImageBuffer buffer, AtomicDouble threshold, int startIdx, int endIdx) {
		this.buffer = buffer;
		startThreshold = (int) threshold.get();
		this.threshold = threshold;
		this.startIdx = startIdx;
		this.endIdx = endIdx;
	}

	/**
	 * Creates a new binarize action to be executed in a ForkJoinPool.
	 *
	 * @param buffer
	 *            the image buffer to binarize
	 * @param threshold
	 *            the brightness threshold
	 */
	public BinarizeAction(BgraPreImageBuffer buffer, AtomicDouble threshold) {
		this(buffer, threshold, 0, buffer.getPixelCount());
	}

	/**
	 * Splits up the computing process here
	 */
	@Override
	protected void compute() {
		int size = endIdx - startIdx;
		if (size < MAX_WORK_SIZE) {
			binarize(buffer);
			throwOnChangedThreshold();
			return;
		}

		splitBinarizeExecution();
	}

	/**
	 * Halves the computing area for the binarize process
	 */
	private void splitBinarizeExecution() {
		int size = endIdx - startIdx;
		int mid = size / 2 + startIdx;

		BinarizeAction left = new BinarizeAction(buffer, threshold, startIdx, mid);
		BinarizeAction right = new BinarizeAction(buffer, threshold, mid, endIdx);
		invokeAll(left, right);
	}

	private void throwOnChangedThreshold() {
		final boolean thresholdChangedByUser = (int) threshold.get() != startThreshold;
		if (thresholdChangedByUser) {
			throw new ValueChangedException();
		}
	}

	/**
	 * the actual binarizing process
	 *
	 * @param imageBuffer the image buffer
	 * @return the binarized buffer
	 */
	private BgraPreImageBuffer binarize(BgraPreImageBuffer imageBuffer) {
		for (int pixelIdx = startIdx; pixelIdx != endIdx; ++pixelIdx) {
			final int red = imageBuffer.getRed(pixelIdx);
			final int green = imageBuffer.getGreen(pixelIdx);
			final int blue = imageBuffer.getBlue(pixelIdx);

			// other brightness calculations possible
			// final int brightness = toBrightnessPerceived(red, green,
			// blue);
			// final int brightness = toBrightnessNTSCLuma(red, green,
			// blue);
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
		}

		return buffer;
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

}
