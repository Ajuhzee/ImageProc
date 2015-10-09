package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.util.MathUtil;

public class PixelAmountCriterion implements MatchingCriterion {

	/**
	 * Gets the amount of pixels of the given color in the image
	 *
	 * @param img
	 * @param color
	 * @return
	 */
	private static int pixelAmount(Image img, Color color) {
		PixelReader reader = img.getPixelReader();

		int sum = 0;
		for (int y = 0; y < img.getHeight(); ++y) {
			for (int x = 0; x < img.getWidth(); ++x) {
				if (color.equals(reader.getColor(x, y))) {
					++sum;
				}
			}
		}

		return sum;
	}

	private final double maximumDeviation;

	private final int allowedPixelDifference;

	public PixelAmountCriterion(double maximumDeviation, int allowedPixelDifference) {
		this.maximumDeviation = maximumDeviation;
		this.allowedPixelDifference = allowedPixelDifference;
	}

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {

		int charToMatchPixelAmount = pixelAmount(characterToMatch, Color.BLACK);
		int potentialCharacterPixelAmount = pixelAmount(potentialCharacter, Color.BLACK);
		int difference = Math.abs(charToMatchPixelAmount - potentialCharacterPixelAmount);

		return difference <= allowedPixelDifference || MathUtil.deviation(potentialCharacterPixelAmount,
				charToMatchPixelAmount) < maximumDeviation;
	}
}
