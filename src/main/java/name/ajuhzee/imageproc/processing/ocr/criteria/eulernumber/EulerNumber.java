package name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.ocr.criteria.MatchingCriterion;

public class EulerNumber implements MatchingCriterion {

	@Override
	public boolean matches(Image characterToMatch, Image potentialCharacter) {
		int eulerNumberToMatch = EulerNumberCompute.getEulerNumber(characterToMatch);
		int eulerNumberPotential = EulerNumberCompute.getEulerNumber(potentialCharacter);
		return eulerNumberPotential == eulerNumberToMatch;
	}


}
