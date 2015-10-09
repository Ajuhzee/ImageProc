package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.ocr.TemplateChar;
import name.ajuhzee.imageproc.util.ImageUtils;

import java.util.Comparator;

public class PixelDifferenceComparator implements Comparator<TemplateChar> {


	/**
	 * Eliminates black pixels at the same position in both chars and returns the amount of black pixels left.
	 *
	 * @param recognizedChar
	 * @param templateChar
	 * @return
	 */
	private static double getPixelDifference(Image recognizedChar, Image templateChar) {


		int width = Math.max((int) recognizedChar.getWidth(), (int) templateChar.getWidth());
		int height = Math.max((int) recognizedChar.getHeight(), (int) templateChar.getHeight());
		PixelReader scaledRecognizedChar = ImageUtils.increaseCanvasSize(recognizedChar, width, height, Color.WHITE)
				.getPixelReader();
		PixelReader scaledTemplateChar = ImageUtils.increaseCanvasSize(templateChar, width, height, Color.WHITE)
				.getPixelReader();

		double difference = 0;

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				Color recognizedColor = scaledRecognizedChar.getColor(x, y);
				Color templateColor = scaledTemplateChar.getColor(x, y);

				difference += Math.abs(templateColor.getBrightness() - recognizedColor.getBrightness());
			}
		}
		return difference;
	}

	private final Image charToMatch;

	public PixelDifferenceComparator(Image charToMatch) {
		this.charToMatch = charToMatch;
	}

	@Override
	public int compare(TemplateChar c1, TemplateChar c2) {
		double c1Difference = getPixelDifference(charToMatch, c1.getSourceImage());
		double c2Difference = getPixelDifference(charToMatch, c2.getSourceImage());
		return Double.compare(c1Difference, c2Difference);
	}
}
