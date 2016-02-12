package name.ajuhzee.imageproc.processing.ocr.criteria.pixelamount;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.ocr.criteria.MatchingCriterion;
import name.ajuhzee.imageproc.util.MathUtil;
import name.ajuhzee.imageproc.util.cache.ImagePropertyCache;

/**
 * Compares the pixel amount of two images.
 *
 * @author Ajuhzee
 */
public class PixelAmountCriterion implements MatchingCriterion {

	private final double maximumDeviation;

	private final int allowedPixelDifference;

	/**
	 * Creates the pixel amount criterion
	 *
	 * @param maximumDeviation
	 * @param allowedPixelDifference
	 */
	public PixelAmountCriterion(double maximumDeviation, int allowedPixelDifference) {
		this.maximumDeviation = maximumDeviation;
		this.allowedPixelDifference = allowedPixelDifference;
	}

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {

		int charToMatchPixelAmount = ImagePropertyCache.getPixelAmount(characterToMatch);
		int potentialCharacterPixelAmount = ImagePropertyCache.getPixelAmount(potentialCharacter);
		int difference = Math.abs(charToMatchPixelAmount - potentialCharacterPixelAmount);

		return difference <= allowedPixelDifference || MathUtil.deviation(potentialCharacterPixelAmount,
				charToMatchPixelAmount) < maximumDeviation;
	}
}
