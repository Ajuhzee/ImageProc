package name.ajuhzee.imageproc.processing;


public class RecognizedChar {

	private final BoundingBox boundingBox;

	public RecognizedChar(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
}
