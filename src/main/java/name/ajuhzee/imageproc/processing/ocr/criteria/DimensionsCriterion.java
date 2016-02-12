package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.MathUtil;

/**
 * Compares the dimensions (width and height) of the matching and potential character.
 *
 * @author Ajuhzee
 */
public class DimensionsCriterion implements MatchingCriterion {

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

	private final int allowedPixelDifference;

	/**
	 * Creates the dimensions criterion
	 *
	 * @param maximumDeviation
	 * @param allowedPixelDifference
	 */
	public DimensionsCriterion(double maximumDeviation, int allowedPixelDifference) {
		this.maximumDeviation = maximumDeviation;
		this.allowedPixelDifference = allowedPixelDifference;
	}

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {
		double sizeDifference = sizeDifference(characterToMatch, potentialCharacter);

		return sizeDifference <= allowedPixelDifference || sizeDifference < maximumDeviation;
	}
}
