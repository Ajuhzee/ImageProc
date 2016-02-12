package name.ajuhzee.imageproc.processing;

import name.ajuhzee.imageproc.util.Point2DInt;

/**
 * Represents a smaller section of the image.
 *
 * @author Ajuhzee
 */
public class Area {

	private final Point2DInt topLeft;

	private final Point2DInt bottomRight;

	/**
	 * Creates the Area with corner coordinates.
	 *
	 * @param topLeft
	 * @param bottomRight
	 */
	public Area(Point2DInt topLeft, Point2DInt bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;

		if (getWidth() <= 0) {
			throw new IllegalArgumentException("The width can not be 0 or smaller");
		}

		if (getHeight() <= 0) {
			throw new IllegalArgumentException("The height can not be 0 or smaller");
		}
	}

	/**
	 *  Creates the Area with x and y values for the border.
	 *
	 * @param left
	 * @param right
	 * @param top
	 * @param bottom
	 */
	public Area(final int left, final int right, final int top, final int bottom) {
		this(new Point2DInt(left, top), new Point2DInt(right, bottom));
	}

	/**
	 *
	 * @return the coordinates of the top left corner
	 */
	public Point2DInt getTopLeft() {
		return topLeft;
	}

	/**
	 *
	 * @return the coordinates of the bottom right corner
	 */
	public Point2DInt getBottomRight() {
		return bottomRight;
	}

	/**
	 *
	 * @return the coordinates of the top right corner
	 */
	public Point2DInt getTopRight() {
		return new Point2DInt(bottomRight.getX(), topLeft.getY());
	}

	/**
	 *
	 * @return the coordinates of the bottom left corner
	 */
	public Point2DInt getBottomLeft() {
		return new Point2DInt(topLeft.getX(), bottomRight.getY());
	}

	/**
	 *
	 * @return the y value for bottom border
	 */
	public int getBottom() {
		return bottomRight.getY();
	}

	/**
	 *
	 * @return the x value for bottom border
	 */
	public int getRight() {
		return bottomRight.getX();
	}

	/**
	 *
	 * @return the y value for left border
	 */
	public int getTop() {
		return topLeft.getY();
	}

	/**
	 *
	 * @return the x value for the left border
	 */
	public int getLeft() {
		return topLeft.getX();
	}

	/**
	 *
	 * @return the width of the area
	 */
	public int getWidth() {
		return bottomRight.getX() - topLeft.getX() + 1;
	}

	/**
	 *
	 * @return the height of the area
	 */
	public int getHeight() {
		return bottomRight.getY() - topLeft.getY() + 1;
	}
}
