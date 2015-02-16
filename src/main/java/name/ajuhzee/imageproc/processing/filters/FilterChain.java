package name.ajuhzee.imageproc.processing.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Makes it possible to apply a chain of filters to an image.
 * 
 * @author Ajuhzee
 *
 */
public class FilterChain {

	private final List<FilterMask> filters;

	/**
	 * Creates the filter chain.
	 * 
	 * @param filters
	 *            the filters that will be contained
	 */
	public FilterChain(FilterMask... filters) {
		this.filters = new ArrayList<>(Arrays.asList(filters));
	}

	/**
	 * @return an array with the single filter masks of the filters
	 */
	public List<FilterMask> getFilterMasks() {
		return new ArrayList<FilterMask>(filters);
	}
}
