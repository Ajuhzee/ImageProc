package name.ajuhzee.imageproc.processing.filters;

import name.ajuhzee.imageproc.processing.filters.FilterMask.Direction;

/**
 * Contains the definition of different filters.
 * 
 * @author Ajuhzee
 *
 */
public class Filters {

	/**
	 * The content of the identity filter.
	 */
	public static final FilterChain IDENTITY = new FilterChain(new FilterMask(new double[][] { {0, 0, 0}, {0, 1, 0},
			{0, 0, 0}}));

	private static final double MEAN3X3_MULTIPLIER = 1d / 9d;

	/**
	 * The content of the mean 3x3 filter.
	 */
	public static final FilterChain MEAN_3X3 = new FilterChain(new FilterMask(new double[][] {
			{MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER},
			{MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER},
			{MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER, MEAN3X3_MULTIPLIER}}));

	private static final double MEAN3X3_SEPERATED_MULTIPLIER = 1d / 3d;

	/**
	 * The content of the seperated mean 3x3 filter.
	 */
	public static final FilterChain MEAN_3X3_SEPERATED = new FilterChain(new FilterMask(new double[] {
			MEAN3X3_SEPERATED_MULTIPLIER, MEAN3X3_SEPERATED_MULTIPLIER, MEAN3X3_SEPERATED_MULTIPLIER},
			Direction.HORIZONTAL), new FilterMask(new double[] {MEAN3X3_SEPERATED_MULTIPLIER,
			MEAN3X3_SEPERATED_MULTIPLIER, MEAN3X3_SEPERATED_MULTIPLIER}, Direction.VERTICAL));

	private static final double LAPLACE_3X3_MULTIPLIER = 1d / 4d;

	/**
	 * The content of the laplacian 3x3 filter.
	 */
	public static final FilterChain LAPLACE_3X3 = new FilterChain(new FilterMask(new double[][] {
			{LAPLACE_3X3_MULTIPLIER * 0, LAPLACE_3X3_MULTIPLIER * 1, LAPLACE_3X3_MULTIPLIER * 0},
			{LAPLACE_3X3_MULTIPLIER * 1, LAPLACE_3X3_MULTIPLIER * -4, LAPLACE_3X3_MULTIPLIER * 1},
			{LAPLACE_3X3_MULTIPLIER * 0, LAPLACE_3X3_MULTIPLIER * 1, LAPLACE_3X3_MULTIPLIER * 0}}));

}
