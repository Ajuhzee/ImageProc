package name.ajuhzee.imageproc.processing;

import javafx.scene.paint.Color;

/**
 * Provides access to Color information for an area.
 * @author Ajuhzee
 */
public interface OptimizedArea {

	/**
	 *
	 * @param color checks if an color exists in the area
	 * @return
	 */
	public boolean containsColor(Color color);

	/**
	 *
	 * @param color checks how many colors exist in the area
	 * @return
	 */
	public int countColor(Color color);
}
