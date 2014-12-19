package name.ajuhzee.imageproc.processing;

import java.util.concurrent.RecursiveAction;

import com.google.common.util.concurrent.AtomicDouble;

public class BinarizeAction extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private static final int MAX_WORK_SIZE = 10_000;

	private final AtomicDouble threshold;

	private final int startThreshold;

	private final BgraPreImageBuffer buffer;

	private final int startIdx;

	private final int endIdx;

	private BinarizeAction(BgraPreImageBuffer buffer, AtomicDouble threshold,
			int startIdx, int endIdx) {
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

	@Override
	protected void compute() {
		int size = endIdx - startIdx;
		if (size < MAX_WORK_SIZE) {
			performSubsetBinarization();
			throwOnChangedThreshold();
			return;
		}

		splitBinarizeExecution(size);
	}

	private void performSubsetBinarization() {
		BgraPreImageBuffer copy = buffer.copySubBuffer(startIdx, endIdx);
		BgraPreImageBuffer result = binarize(copy);
		saveResultToBuffer(result);
	}

	private void splitBinarizeExecution(int size) {
		int mid = size / 2 + startIdx;

		BinarizeAction left = new BinarizeAction(buffer, threshold, startIdx,
				mid);
		BinarizeAction right = new BinarizeAction(buffer, threshold, mid,
				endIdx);
		invokeAll(left, right);
	}

	private void saveResultToBuffer(BgraPreImageBuffer result) {
		System.arraycopy(result.getRawBuffer(), 0, buffer.getRawBuffer(),
				startIdx * BgraPreImageBuffer.BYTES_PER_PIXEL,
				result.getRawBuffer().length);
	}

	private void throwOnChangedThreshold() {
		final boolean thresholdChangedByUser = (int) threshold.get() != startThreshold;
		if (thresholdChangedByUser) {
			throw new ValueChangedException();
		}
	}

	private BgraPreImageBuffer binarize(BgraPreImageBuffer buffer) {
		for (int pixelIdx = 0; pixelIdx != buffer.getPixelCount(); ++pixelIdx) {
			final int red = buffer.getRed(pixelIdx);
			final int green = buffer.getGreen(pixelIdx);
			final int blue = buffer.getBlue(pixelIdx);

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
	 * @see <a href="https://en.wikipedia.org/wiki/Luma_%28video%29">Wikipedia:
	 *      Luma(Video)</a>
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
		return (int) Math.sqrt(0.299 * red * red + 0.587 * green * green
				+ 0.114 * blue * blue);
	}

}
