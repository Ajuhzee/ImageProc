package name.ajuhzee.imageproc.processing.filters;

/**
 * Gives acces to the several filter typs in form of an enum.
 * 
 * @author Ajuhzee
 *
 */
public enum FilterType {
	/**
	 * The identity filter. Leaves the source image untouched.
	 */
	IDENTITY(Filters.IDENTITY),

	/**
	 * The mean 3x3 filter. Smoothes the image.
	 */
	MEAN_3X3(Filters.MEAN_3X3),

	/**
	 * The seperated mean 3x3 filter. A filter chain that contains a mean 3x1 filter and a mean 1x3 filter. Smoothes the
	 * image.
	 */
	MEAN_3X3_SEPERATED(Filters.MEAN_3X3_SEPERATED),

	/**
	 * A laplacian 3x3 filter. Emphasizes the edges in the image.
	 */
	LAPLACE_3X3(Filters.LAPLACE_3X3);

	private final FilterChain filter;

	private FilterType(FilterChain filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter chain
	 */
	public FilterChain getFilterChain() {
		return filter;
	}

}