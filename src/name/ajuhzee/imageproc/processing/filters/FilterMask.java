package name.ajuhzee.imageproc.processing.filters;

/**
 * 
 * @author Ajuhzee
 *
 */
public class FilterMask {

	/**
	 * Defines the directions of the filter mask.
	 * 
	 * @author Ajuhzee
	 *
	 */
	public static enum Direction {
		/**
		 * the vertical dimension of the filter mask
		 */
		VERTICAL,
		/**
		 * the vertical dimension of the filter mask
		 */
		HORIZONTAL;
	}

	private final double[][] filterMask;

	/**
	 * Creates the filter mask.
	 * 
	 * @param filterMask
	 *            the filter mask to be created
	 */
	public FilterMask(double[][] filterMask) {
		this.filterMask = filterMask;
	}

	/**
	 * 
	 * @param filterMask
	 *            the filter mask with its containing multipliers
	 * @param d
	 *            the direction of the filter mask
	 */
	public FilterMask(double[] filterMask, Direction d) {
		int x;
		int y;
		if (d == Direction.HORIZONTAL) {
			x = filterMask.length;
			y = 1;
		} else {
			x = 1;
			y = filterMask.length;
		}

		this.filterMask = new double[y][x];

		if (d == Direction.HORIZONTAL) {
			for (int i = 0; i != filterMask.length; ++i) {
				this.filterMask[0][i] = filterMask[i];
			}
		} else {
			for (int i = 0; i != filterMask.length; ++i) {
				this.filterMask[i][0] = filterMask[i];
			}
		}
	}

	/**
	 * @param x
	 *            the x coordinate of the filter mask
	 * @param y
	 *            the y coordinate of the filter mask
	 * @return the prefactor of the filter
	 */
	public double getMultiplier(int x, int y) {
		return filterMask[y + getKernelY() / 2][x + getKernelX() / 2];
	}

	/**
	 * @return the kernel size in x direction
	 */
	public int getKernelX() {
		return filterMask[0].length;
	}

	/**
	 * @return the kernel size in y direction
	 */
	public int getKernelY() {
		return filterMask.length;
	}

}