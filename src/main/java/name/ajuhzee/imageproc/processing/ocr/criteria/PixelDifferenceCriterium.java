package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;

public class PixelDifferenceCriterium implements MatchingCriterium {

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {
		return false;
	}
}
