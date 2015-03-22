package name.ajuhzee.imageproc.processing;

import javafx.geometry.Point2D;

public class BoundingBox {

	private final Point2D topLeft;
	private final Point2D bottomRight;

	public BoundingBox(Point2D topLeft, Point2D bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	public Point2D getTopLeft() {
		return topLeft;
	}

	public Point2D getBottomRight() {
		return bottomRight;
	}

	public Point2D getTopRight() {
		return new Point2D(bottomRight.getX(), topLeft.getY());
	}

	public Point2D getBottomLeft() {
		return new Point2D(topLeft.getX(), bottomRight.getY());
	}

	public double getWidth() {
		return bottomRight.getX() - topLeft.getX();
	}

	public double getHeight() {
		return bottomRight.getY() - topLeft.getY();
	}
}
