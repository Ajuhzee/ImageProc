/**
 *
 */
package name.ajuhzee.imageproc.processing;

import com.google.common.util.concurrent.AtomicDouble;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.util.MathUtil;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * A library for image processing algorithms.
 *
 * @author Ajuhzee
 */
public class ImageEditing {


	/**
	 * Binarizes a given image, dynamically changing its computation and result as the threshold changes.
	 *
	 * @param toBinarize the image to be binarized
	 * @param threshold the threshold for the binarization (can vary while function is running)
	 * @return the binarized image
	 */
	public static Image binarizeDynamic(Image toBinarize, AtomicDouble threshold) {
		BgraPreImageBuffer buffer;
		while (true) {
			try {
				buffer = BgraPreImageBuffer.getBgraPreBuffer(toBinarize);
				GlobalThreadPool.FORK_JOIN_POOL.invoke(new BinarizeAction(buffer, threshold));
				break;
			} catch (ValueChangedException e) {
				continue;
			}
		}

		int width = (int) toBinarize.getWidth();
		int height = (int) toBinarize.getHeight();
		return BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
	}

	/**
	 * Inverts a given image.
	 *
	 * @param toInvert the source image
	 * @return the inverted image
	 */
	public static Image invert(Image toInvert) {
		BgraPreImageBuffer buffer = BgraPreImageBuffer.getBgraPreBuffer(toInvert);
		for (int pixelIdx = 0; pixelIdx != buffer.getPixelCount(); ++pixelIdx) {
			buffer.setRed(pixelIdx, 255 - buffer.getRed(pixelIdx));
			buffer.setGreen(pixelIdx, 255 - buffer.getGreen(pixelIdx));
			buffer.setBlue(pixelIdx, 255 - buffer.getBlue(pixelIdx));
		}

		int width = (int) toInvert.getWidth();
		int height = (int) toInvert.getHeight();
		return BgraPreImageBuffer.createBgraPreImage(buffer, width, height);
	}

	public static Image dilate(Image img, Neighborhood neighborhood) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		WritableImage newImage = new WritableImage(width, height);
		PixelWriter out = newImage.getPixelWriter();

		forEveryWhitePixelWithBlackNeighbor(img, (x, y) -> {
			out.setColor(x, y, Color.BLACK);
		}, (x, y) -> {
			Color origColor = img.getPixelReader().getColor(x, y);
			Color newColor = newImage.getPixelReader().getColor(x, y);
			if (newColor.equals(Color.TRANSPARENT)) {
				out.setColor(x, y, origColor);
			}
		}, neighborhood);

		return newImage;
	}

	public static Image erode(Image img, Neighborhood neighborhood) {

		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		WritableImage newImage = new WritableImage(width, height);
		PixelWriter out = newImage.getPixelWriter();

		forEveryWhitePixelWithBlackNeighbor(img, (x, y) -> {
			out.setColor(x, y, Color.WHITE);
			getNeighborPixels(img, x, y, neighborhood).stream().forEach(point -> {
				out.setColor((int) point.getX(), (int) point.getY(), Color.WHITE);
			});
		}, (x, y) -> {
			Color origColor = img.getPixelReader().getColor(x, y);
			Color newColor = newImage.getPixelReader().getColor(x, y);
			if (newColor.equals(Color.TRANSPARENT)) {
				out.setColor(x, y, origColor);
			}
		}, neighborhood);


		return newImage;
	}

	/**
	 * @param img
	 * @param matched callback when the white pixel has a black neighbor
	 * @param unmatched callback when the white pixel does not have a black neighbor
	 * @param neighborhood
	 */
	private static void forEveryWhitePixelWithBlackNeighbor(Image img, PixelMatched matched, PixelMatched unmatched,
															Neighborhood neighborhood) {

		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		PixelReader in = img.getPixelReader();
		for (int x = 0; x != width; ++x) {
			for (int y = 0; y != height; ++y) {
				Color pixel = in.getColor(x, y);

				if (pixel.equals(Color.WHITE) && hasBlackNeighbor(img, x, y, neighborhood)) {
					matched.pixelMatched(x, y);
				} else {
					unmatched.pixelMatched(x, y);
				}

			}
		}
	}

	private static boolean hasBlackNeighbor(Image img, int x, int y, Neighborhood neighborhood) {

		ArrayList<Point2D> toCheck = getNeighborPixels(img, x, y, neighborhood);

		return toCheck.stream().anyMatch((point) -> {
			Color pixel = img.getPixelReader().getColor((int) point.getX(), (int) point.getY());
			return pixel.equals(Color.BLACK);
		});
	}

	private static ArrayList<Point2D> getNeighborPixels(Image img, int x, int y, Neighborhood neighborhood) {

		int maxX = (int) img.getWidth() - 1;
		int maxY = (int) img.getHeight() - 1;

		ArrayList<Point2D> neighbors = new ArrayList<Point2D>();
		switch (neighborhood) {
			case NEIGHBORHOOD8: {
				if (x != 0 && y != 0) {
					neighbors.add(new Point2D(x - 1, y - 1));
				}
				if (x != 0 && y != maxY) {
					neighbors.add(new Point2D(x - 1, y + 1));
				}
				if (x != maxX && y != 0) {
					neighbors.add(new Point2D(x + 1, y - 1));
				}
				if (x != maxX && y != maxY) {
					neighbors.add(new Point2D(x + 1, y + 1));
				}
			}
			case NEIGHBORHOOD4: {
				if (x != 0) {
					neighbors.add(new Point2D(x - 1, y));
				}
				if (x != maxX) {
					neighbors.add(new Point2D(x + 1, y));
				}
				if (y != 0) {
					neighbors.add(new Point2D(x, y - 1));
				}
				if (y != maxY) {
					neighbors.add(new Point2D(x, y + 1));
				}
				break;
			}

		}

		return neighbors;

	}


	public static Image rotate(Image img, double angle, Color toFill) {
		BufferedImage bufferedImg = SwingFXUtils.fromFXImage(img, null);

		// converts the input angle
		double positiveAngle = angle < 0 ? 360 - Math.abs(angle) : angle;
		double normalizedAngle = positiveAngle % 360;
		double radiansAngle = Math.toRadians(normalizedAngle);

		// trigonometry
		double height1 = Math.abs(Math.sin(radiansAngle) * img.getWidth());
		double height2 = Math.abs(Math.cos(radiansAngle) * img.getHeight());
		int newHeight = (int) MathUtil.roundCustom(height1 + height2, 0.01);
		double width1 = Math.abs(Math.sin(radiansAngle) * img.getHeight());
		double width2 = Math.abs(Math.cos(radiansAngle) * img.getWidth());
		int newWidth = (int) MathUtil.roundCustom(width1 + width2, 0.01);


		BufferedImage rotatedImg =
				new BufferedImage(newWidth, newHeight, bufferedImg.getType());

		Graphics2D graphics = (Graphics2D) rotatedImg.getGraphics();
		graphics.setColor(
				new java.awt.Color((float) toFill.getRed(), (float) toFill.getGreen(), (float) toFill.getBlue(),
						(float) toFill.getOpacity()));
		graphics.fillRect(0, 0, newWidth, newHeight);

		// creates the new image dimensions
		double moveX;
		double moveY;
		if (normalizedAngle < 90) {
			moveX = width1;
			moveY = 0;
		} else if (normalizedAngle < 180) {
			moveX = newWidth;
			moveY = height2;
		} else if (normalizedAngle < 270) {
			moveX = width2;
			moveY = newHeight;
		} else {
			moveX = 0;
			moveY = height1;
		}
		graphics.translate(moveX, moveY);
		graphics.rotate(radiansAngle);

		graphics.drawImage(bufferedImg, 0, 0, bufferedImg.getWidth(), bufferedImg.getHeight(), null);
		return SwingFXUtils.toFXImage(rotatedImg, null);
	}
}
