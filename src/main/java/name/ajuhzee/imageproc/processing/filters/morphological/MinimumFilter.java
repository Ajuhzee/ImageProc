package name.ajuhzee.imageproc.processing.filters.morphological;

import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;

/**
 * Minimum filter implementation
 */
public class MinimumFilter extends MorphologicalFilter {


	@Override
	public Color applyImpl(List<Color> pixelList) {
		Optional<Color> minColor =
				pixelList.stream().reduce((c1, c2) -> c1.getBrightness() < c2.getBrightness() ? c1 : c2);

		return minColor.get();
	}
}
