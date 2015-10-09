package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.MathUtil;

/**
 * Compares the dimensions (width and height) of the matching and potential character.
 */
public class DimensionsCriterium implements MatchingCriterium {

	/**
	 * Compares the image height and width.
	 *
	 * @param base
	 * @param other
	 * @return
	 */
	private static double sizeDifference(Image base, Image other) {
		return Math.max(
				MathUtil.deviation(base.getHeight(), other.getHeight()),
				MathUtil.deviation(base.getWidth(), other.getWidth()));
	}

	private final double maximumDeviation;

	public DimensionsCriterium(double maximumDeviation) {
		this.maximumDeviation = maximumDeviation;
	}

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {
		return sizeDifference(characterToMatch, potentialCharacter) > maximumDeviation;
	}
}
