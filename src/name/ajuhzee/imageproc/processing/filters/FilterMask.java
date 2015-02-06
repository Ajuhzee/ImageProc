package name.ajuhzee.imageproc.processing.filters;

public class FilterMask {

	public static enum Direction {
		VERTICAL, HORIZONTAL;
	}

	private final double[][] filterMask;

	public FilterMask(double[][] filterMask) {
		this.filterMask = filterMask;
	}

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

	public double getMultiplier(int x, int y) {
		return filterMask[y + getKernelY() / 2][x + getKernelX() / 2];
	}

	public int getKernelX() {
		return filterMask[0].length;
	}

	public int getKernelY() {
		return filterMask.length;
	}

}