package name.ajuhzee.imageproc.generating;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.BgraPreImageBuffer;

/**
 * Workes as a library for image generating algorithms
 *
 * @author Ajuhzee
 *
 */
public class ImageGenerating {

	/**
	 * Generates a test image.
	 * 
	 * @return the generated image
	 */
	public static Image generate() {
		int width = 512;
		int height = 512;
		BgraPreImageBuffer buffer = new BgraPreImageBuffer(width, height);

		for (int pixelIdx = 0; pixelIdx != width * height; ++pixelIdx) {
			buffer.setBlue(pixelIdx, pixelIdx / 2);
			buffer.setGreen(pixelIdx, pixelIdx / 2);
			buffer.setRed(pixelIdx, pixelIdx / 2);
			buffer.setAlpha(pixelIdx, 255);
		}

		Image testImg = BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
		return testImg;
	}
}
