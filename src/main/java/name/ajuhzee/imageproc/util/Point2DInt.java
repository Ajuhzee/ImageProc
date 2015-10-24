package name.ajuhzee.imageproc.util;

public class Point2DInt {

	private final int x;

	private final int y;

	/**
	 * Creates a new point with the given coordinates
	 *
	 * @param x
	 * @param y
	 */
	public Point2DInt(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x of the point
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y of the point
	 */
	public int getY() {
		return y;
	}
}
