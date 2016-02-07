package name.ajuhzee.imageproc.processing.filters.morphological;

import javafx.scene.paint.Color;

import java.util.List;

/**
 * Base class for all morphological filters.
 */
public abstract class MorphologicalFilter {


	/**
	 * Applies the filter to the given pixel list
	 *
	 * @param pixelList
	 * @return
	 */
	public final Color apply(List<Color> pixelList) {
		if (pixelList.isEmpty()) {
			throw new IllegalArgumentException("The list of pixels must not be empty!");
		}

		return applyImpl(pixelList);
	}

	/**
	 * Method which implements the filtering. Guaranteed to never receive an empty list.
	 *
	 * @param pixelList
	 * @return
	 */
	protected abstract Color applyImpl(List<Color> pixelList);
}
