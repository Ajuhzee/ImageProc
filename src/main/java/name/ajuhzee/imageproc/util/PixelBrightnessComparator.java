package name.ajuhzee.imageproc.util;

import javafx.scene.paint.Color;

import java.util.Comparator;

/**
 * Compares the brightness of the color values
 */
public class PixelBrightnessComparator implements Comparator<Color> {


	@Override
	public int compare(Color o1, Color o2) {
		double brightness1 = o1.getBrightness();
		double brightness2 = o2.getBrightness();

		return Double.compare(brightness1, brightness2);
	}
}
