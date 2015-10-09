package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;

public interface MatchingCriterion {

	boolean matches(Image characterToMatch, Image potentialCharacter);
}
