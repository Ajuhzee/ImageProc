package name.ajuhzee.imageproc.processing.ocr;

import name.ajuhzee.imageproc.processing.Area;

/**
 * Represents a recognized char with its circumscribing rectangle.
 *
 * @author Ajuhzee
 *
 */
public class RecognizedChar {

	private final Area boundingBox;

	/**
	 * Creates a new recognized character and sets the bounding box it.
	 *
	 * @param boundingBox
	 *            the bounding box
	 */
	public RecognizedChar(Area boundingBox) {
		this.boundingBox = boundingBox;
	}

	/**
	 * @return the bounding box
	 */
	public Area getBoundingBox() {
		return boundingBox;
	}
}
