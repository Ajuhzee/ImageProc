package name.ajuhzee.imageproc.processing.ocr;

/**
 * Represents a recognized line of text.
 * 
 * @author Ajuhzee
 */
public class RecognizedLine {

	private final int topY;

	private final int bottomY;

	/**
	 * Creates a new recognized line
	 * 
	 * @param topY
	 *            the y value where the line begins
	 * @param bottomY
	 *            the y value where the line ends
	 */
	public RecognizedLine(int topY, int bottomY) {
		this.topY = topY;
		this.bottomY = bottomY;
	}

	/**
	 * @return the bottom y value in the image
	 */
	public int getBottomY() {
		return bottomY;
	}

	/**
	 * @return the top y value in the image
	 */
	public int getTopY() {
		return topY;
	}

}
