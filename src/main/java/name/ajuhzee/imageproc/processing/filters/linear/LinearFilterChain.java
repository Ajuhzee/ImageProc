package name.ajuhzee.imageproc.processing.filters.linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Makes it possible to apply a chain of filters to an image.
 *
 * @author Ajuhzee
 *
 */
public class LinearFilterChain {

	private final List<LinearFilterMask> filters;

	/**
	 * Creates the filter chain.
	 *
	 * @param filters
	 *            the filters that will be contained
	 */
	public LinearFilterChain(LinearFilterMask... filters) {
		this.filters = new ArrayList<>(Arrays.asList(filters));
	}

	/**
	 * @return an array with the single filter masks of the filters
	 */
	public List<LinearFilterMask> getFilterMasks() {
		return new ArrayList<LinearFilterMask>(filters);
	}
}
