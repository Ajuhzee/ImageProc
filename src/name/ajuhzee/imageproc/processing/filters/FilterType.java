package name.ajuhzee.imageproc.processing.filters;


public enum FilterType {
	IDENTITY(Filters.IDENTITY),

	MEAN_3X3(Filters.MEAN_3X3),

	MEAN_3X3_SEPERATED(Filters.MEAN_3X3_SEPERATED),

	LAPLACE_3X3(Filters.LAPLACE_3X3);

	private final FilterChain filter;

	private FilterType(FilterChain filter) {
		this.filter = filter;
	}

	public FilterChain getFilterChain() {
		return filter;
	}

}