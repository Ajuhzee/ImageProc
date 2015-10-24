package name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.ocr.criteria.MatchingCriterion;
import name.ajuhzee.imageproc.util.cache.ImagePropertyCache;

public class EulerNumberCriterion implements MatchingCriterion {

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {
		int eulerNumberToMatch = ImagePropertyCache.getEulerNumber(characterToMatch);
		int eulerNumberPotential = ImagePropertyCache.getEulerNumber(potentialCharacter);
		return eulerNumberPotential == eulerNumberToMatch;
	}


}
