package name.ajuhzee.imageproc.processing;

import javafx.scene.paint.Color;

/**
 * Provides access to area attributes.
 * @author Ajuhzee
 */
public interface AreaAttributes {

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
