package name.ajuhzee.imageproc.processing.filters.morphological;

import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.util.PixelBrightnessComparator;

import java.util.List;

/**
 * Median filter implementation
 */
public class MedianFilter extends MorphologicalFilter {

	@Override
	protected Color applyImpl(List<Color> pixelList) {
		pixelList.sort(new PixelBrightnessComparator());


		if (pixelList.size() % 2 == 0) {
			Color c1 = pixelList.get(pixelList.size() / 2);
			Color c2 = pixelList.get((pixelList.size() / 2) + 1);
			double brightness1 = c1.getBrightness();
			double brightness2 = c2.getBrightness();
			double average = (brightness1 + brightness2) / 2;
			return new Color(average, average, average, 1);
		} else {
			int median = pixelList.size() / 2;
			return pixelList.get(median);
		}
	}
}

