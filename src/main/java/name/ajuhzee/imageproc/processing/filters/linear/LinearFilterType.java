package name.ajuhzee.imageproc.processing.filters.linear;

/**
 * Gives access to the several filter types in form of an enum.
 *
 * @author Ajuhzee
 *
 */
public enum LinearFilterType {
	/**
	 * The identity filter. Leaves the source image untouched.
	 */
	IDENTITY(LinearFilters.IDENTITY),

	/**
	 * The mean 3x3 filter. Smoothes the image.
	 */
	MEAN_3X3(LinearFilters.MEAN_3X3),

	/**
	 * The seperated mean 3x3 filter. A filter chain that contains a mean 3x1 filter and a mean 1x3 filter. Smoothes the
	 * image.
	 */
	MEAN_3X3_SEPERATED(LinearFilters.MEAN_3X3_SEPERATED),

	/**
	 * A laplacian 3x3 filter. Emphasizes the edges in the image.
	 */
	LAPLACE_3X3(LinearFilters.LAPLACE_3X3);

	private final LinearFilterChain filter;

	private LinearFilterType(LinearFilterChain filter) {
		this.filter = filter;
	}

	/**
	 * @return the filter chain
	 */
	public LinearFilterChain getFilterChain() {
		return filter;
	}

}
