package name.ajuhzee.imageproc.processing.filters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterChain {

	private final List<FilterMask> filters;

	public FilterChain(FilterMask... filters) {
		this.filters = new ArrayList<>(Arrays.asList(filters));
	}

	public List<FilterMask> getFilterMasks() {
		return new ArrayList<FilterMask>(filters);
	}
}
