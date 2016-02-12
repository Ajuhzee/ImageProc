package name.ajuhzee.imageproc.processing.filters.linear;

import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterMask.Direction;

/**
 * Contains the definition of different linear filters.
 *
 * @author Ajuhzee
 *
 */
public class LinearFilters {

	/**
	 * The content of the identity filter.
	 */
	public static final LinearFilterChain
			IDENTITY = new LinearFilterChain(new LinearFilterMask(new double[][] {{0, 0, 0}, {0, 1, 0},
																				  {0, 0, 0}}));

	private static final double MEAN3X3_MULTIPLIER = 1d / 9d;

	/**
	 * The content of the mean 3x3 filter.
	 */
	public static final LinearFilterChain MEAN_3X3 = new LinearFilterChain(new LinearFilterMask(new double[][] {
			{MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER},
			{MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER},
			{MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER}}));

	private static final double MEAN3X3_SEPERATED_MULTIPLIER = 1d / 3d;

	/**
	 * The content of the seperated mean 3x3 filter.
	 */
	public static final LinearFilterChain MEAN_3X3_SEPERATED = new LinearFilterChain(new LinearFilterMask(new double[] {
			MEAN3X3_SEPERATED_MULTIPLIER, MEAN3X3_SEPERATED_MULTIPLIER, MEAN3X3_SEPERATED_MULTIPLIER},
			Direction.HORIZONTAL), new LinearFilterMask(new double[] {MEAN3X3_SEPERATED_MULTIPLIER,
																	  MEAN3X3_SEPERATED_MULTIPLIER, MEAN3X3_SEPERATED_MULTIPLIER}, Direction.VERTICAL));

	private static final double LAPLACE_3X3_MULTIPLIER = 1d / 4d;

	/**
	 * The content of the laplacian 3x3 filter.
	 */
	public static final LinearFilterChain LAPLACE_3X3 = new LinearFilterChain(new LinearFilterMask(new double[][] {
			{LAPLACE_3X3_MULTIPLIER * 0, LAPLACE_3X3_MULTIPLIER * 1, LAPLACE_3X3_MULTIPLIER * 0},
			{LAPLACE_3X3_MULTIPLIER * 1, LAPLACE_3X3_MULTIPLIER * -4, LAPLACE_3X3_MULTIPLIER * 1},
			{LAPLACE_3X3_MULTIPLIER * 0, LAPLACE_3X3_MULTIPLIER * 1, LAPLACE_3X3_MULTIPLIER * 0}}));

}
