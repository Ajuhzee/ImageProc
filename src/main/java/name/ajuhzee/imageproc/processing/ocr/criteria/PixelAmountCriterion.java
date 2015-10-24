package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.MathUtil;
import name.ajuhzee.imageproc.util.cache.ImagePropertyCache;

public class PixelAmountCriterion implements MatchingCriterion {

	private final double maximumDeviation;

	private final int allowedPixelDifference;

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
