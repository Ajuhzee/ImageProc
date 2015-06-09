package name.ajuhzee.imageproc.processing.ocr;

import name.ajuhzee.imageproc.processing.BoundingBox;

/**
 * Represents a recognized char with its circumscribing rectangle.
 * 
 * @author Ajuhzee
 *
 */
public class RecognizedChar {

	private final BoundingBox boundingBox;

	/**
	 * Creates a new recognized character and sets the bounding box it.
	 * 
	 * @param boundingBox
	 *            the bounding box
	 */
	public RecognizedChar(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	/**
	 * @return the bounding box
	 */
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
