/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import java.util.concurrent.RecursiveAction;

/**
 * Performs the actual invertation.
 * 
 * @author Ajuhzee
 *
 */
public class InvertAction extends RecursiveAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final BgraPreImageBuffer buffer;

	private final int startIdx;

	private final int endIdx;

	/**
	 * @param buffer
	 */

	public InvertAction(BgraPreImageBuffer buffer) {
		this.buffer = buffer;
		this.startIdx = 0;
		this.endIdx = buffer.getPixelCount();
	}

	private BgraPreImageBuffer invert(BgraPreImageBuffer buffer) {
		for (int pixelIdx = startIdx; pixelIdx != endIdx; ++pixelIdx) {
			buffer.setRed(pixelIdx, 255 - buffer.getRed(pixelIdx));
			buffer.setGreen(pixelIdx, 255 - buffer.getGreen(pixelIdx));
			buffer.setBlue(pixelIdx, 255 - buffer.getBlue(pixelIdx));
		}
		return buffer;

	}

	@Override
	protected void compute() {
		invert(buffer);
		return;
	}
}
