package name.ajuhzee.imageproc.generating;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.BgraPreImageBuffer;

/**
 * A library for image generating algorithms
 *
 * @author Ajuhzee
 *
 */
public class ImageGenerating {

	private static final int DIMENSION = 512;

	/**
	 * Generates a test image.
	 * 
	 * @return the generated image
	 */
	public static Image generate() {

		BgraPreImageBuffer buffer = new BgraPreImageBuffer(DIMENSION, DIMENSION);

		for (int pixelIdx = 0; pixelIdx != DIMENSION * DIMENSION; ++pixelIdx) {
			buffer.setBlue(pixelIdx, pixelIdx / 2);
			buffer.setGreen(pixelIdx, pixelIdx / 2);
			buffer.setRed(pixelIdx, pixelIdx / 2);
			buffer.setAlpha(pixelIdx, 255);
		}

		Image testImg = BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
		return testImg;
	}
}
