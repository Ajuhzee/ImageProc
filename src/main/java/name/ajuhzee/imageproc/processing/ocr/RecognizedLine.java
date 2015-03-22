package name.ajuhzee.imageproc.processing.ocr;

/**
 * Represents a recognized line of text
 * 
 * @author Timo
 */
public class RecognizedLine {

	private final int topY;

	private final int bottomY;

	/**
	 * Creates a new recognized line
	 * 
	 * @param topY
	 * @param bottomY
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
