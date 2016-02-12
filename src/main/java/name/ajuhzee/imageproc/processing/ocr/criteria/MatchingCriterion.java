package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;

/**
 * Base class for a matching criterion.
 *
 * @author Ajuhzee
 */
public interface MatchingCriterion {

	/**
	 * Compares two images regarding a matching criterion
	 * @param characterToMatch
	 * @param potentialCharacter
	 * @return
	 */
	boolean matches(Image characterToMatch, Image potentialCharacter);
}
