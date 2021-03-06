package name.ajuhzee.imageproc.processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * An Area tha also makes it possible to retrieve attributes about it in an optimized way
 */
public class OptimizedArea extends Area implements AreaAttributes {

	private final byte[] buffer;

	/**
	 * Creates an Area for the given line of the image
	 *
	 * @param img
	 * @param y
	 * @return
	 */
	public static OptimizedArea forLine(final Image img, final int y) {
		if (y < 0 || y >= img.getHeight()) {
			throw new IllegalArgumentException("The line requested (" + y + ") is not inside the image bounds!");
		}

		return new OptimizedArea(img, 0, y, (int) img.getWidth(), 1);
	}

	/**
	 * Creates an area for the given column of the image
	 *
	 * @param img
	 * @param x
	 * @return
	 */
	public static OptimizedArea forColumn(final Image img, final int x) {
		if (x < 0 || x >= img.getWidth()) {
			throw new IllegalArgumentException(
					"The column requested (" + x + ") is not inside the image bounds!");
		}

		return new OptimizedArea(img, x, 0, 1, (int) img.getHeight());
	}

	private static byte colorValueToByte(final double color) {
		return (byte) (color * 255.0);
	}

	/**
	 * Creates a new Area for the given column of the image, but only inside the given Area's bounds
	 *
	 * @param img
	 * @param area
	 * @param x
	 * @return
	 */
	public static OptimizedArea forColumn(final Image img, final Area area, final int x) {
		if (x < area.getLeft() || x >= area.getLeft() + area.getWidth()) {
			throw new IllegalArgumentException(
					"The column requested (" + x + ") is not inside the area bounds!");
		}

		return new OptimizedArea(img, x, area.getTop(), 1, area.getHeight());
	}

	/**
	 * Creates a new Area for the given line of the image, but only inside the given Area's bounds
	 *
	 * @param img
	 * @param area
	 * @param y
	 * @return
	 */
	public static OptimizedArea forLine(final Image img, final Area area, final int y) {
		if (y < area.getTop() || y >= area.getTop() + area.getHeight()) {
			throw new IllegalArgumentException(
					"The line requested (" + y + ") is not inside the area bounds!");
		}

		return new OptimizedArea(img, area.getLeft(), y, area.getWidth(), 1);
	}

	/**
	 * Creates the optimized Area.
	 *
	 * @param img the source image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public OptimizedArea(final Image img, final int x, final int y, final int width, final int height) {
		super(x, x + width, y, y + height);

		final PixelReader reader = img.getPixelReader();
		buffer = new byte[width * height * BgraPreImageBuffer.BYTES_PER_PIXEL];
		reader.getPixels(x, y, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width
				* BgraPreImageBuffer.BYTES_PER_PIXEL);
	}

	@Override
	public boolean containsColor(final Color color) {
		final byte blue = colorValueToByte(color.getBlue());
		final byte green = colorValueToByte(color.getGreen());
		final byte red = colorValueToByte(color.getRed());
		final byte alpha = colorValueToByte(color.getOpacity());
		for (int i = 0; i != buffer.length; i += 4) {
			if (isColorAt(i, blue, green, red, alpha)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int countColor(final Color color) {
		final byte blue = colorValueToByte(color.getBlue());
		final byte green = colorValueToByte(color.getGreen());
		final byte red = colorValueToByte(color.getRed());
		final byte alpha = colorValueToByte(color.getOpacity());

		int sum = 0;
		for (int i = 0; i != buffer.length; i += 4) {
			if (isColorAt(i, blue, green, red, alpha)) {
				++sum;
			}
		}
		return sum;
	}

	private boolean isColorAt(final int idx, final byte blue, final byte green, final byte red, final byte alpha) {
		return buffer[idx] == blue && buffer[idx + 1] == green && buffer[idx + 2] == red && buffer[idx + 3] == alpha;
	}

}
