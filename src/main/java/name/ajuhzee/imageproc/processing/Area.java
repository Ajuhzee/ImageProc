package name.ajuhzee.imageproc.processing;

import name.ajuhzee.imageproc.util.Point2DInt;

public class Area {

	private final Point2DInt topLeft;

	private final Point2DInt bottomRight;

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


	public Area(final int left, final int right, final int top, final int bottom) {
		this(new Point2DInt(left, top), new Point2DInt(right, bottom));
	}

	public Point2DInt getTopLeft() {
		return topLeft;
	}

	public Point2DInt getBottomRight() {
		return bottomRight;
	}

	public Point2DInt getTopRight() {
		return new Point2DInt(bottomRight.getX(), topLeft.getY());
	}

	public Point2DInt getBottomLeft() {
		return new Point2DInt(topLeft.getX(), bottomRight.getY());
	}

	public int getBottom() {
		return bottomRight.getY();
	}

	public int getRight() {
		return bottomRight.getX();
	}

	public int getTop() {
		return topLeft.getY();
	}

	public int getLeft() {
		return topLeft.getX();
	}

	public int getWidth() {
		return bottomRight.getX() - topLeft.getX() + 1;
	}

	public int getHeight() {
		return bottomRight.getY() - topLeft.getY() + 1;
	}
}
