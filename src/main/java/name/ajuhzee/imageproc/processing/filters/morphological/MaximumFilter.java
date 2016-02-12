package name.ajuhzee.imageproc.processing.filters.morphological;

import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.util.PixelBrightnessComparator;

import java.util.List;

/**
 * Maximum filter implementation
 *
 * @author Ajuhzee
 */
public class MaximumFilter extends MorphologicalFilter {

	@Override
	protected final Color applyImpl(List<Color> pixelList) {
		pixelList.sort(new PixelBrightnessComparator());

		return getLastElement(pixelList);
	}

	private Color getLastElement(List<Color> pixelList) {return pixelList.get(pixelList.size() - 1);}
}
