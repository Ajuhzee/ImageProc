package name.ajuhzee.imageproc.processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ajuhzee
 */
public class OptimizedAreaImpl implements OptimizedArea {

	private static final Map<Image, OptimizedArea> areaCache = new HashMap<>();

	/**
	 *
	 * @param img
	 * @param y
	 * @return
	 */
	public static OptimizedArea forLine(final Image img, final int y) {
		if (y < 0 || y >= img.getHeight()) {
			throw new IllegalArgumentException("The line requested (" + y + ") is not inside the image bounds!");
		}

		return new OptimizedAreaImpl(img, 0, y, (int) img.getWidth(), 1);
	}


	public static OptimizedArea forColumn(final Image img, final int x) {
		if (x < 0 || x >= img.getWidth()) {
			throw new IllegalArgumentException(
					"The column requested (" + x + ") is not inside the image bounds!");
		}

		return new OptimizedAreaImpl(img, x, 0, 1, (int) img.getHeight());
	}


	private final byte[] buffer;

	/**
	 * Creates the optimized Area.
	 *
	 * @param img the source image
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public OptimizedAreaImpl(final Image img, final int x, final int y, final int width, final int height) {
		if (areaCache.containsKey(img)) {

		}
		final PixelReader reader = img.getPixelReader();
		buffer = new byte[width * height * BgraPreImageBuffer.BYTES_PER_PIXEL];
		reader.getPixels(x, y, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width
				* BgraPreImageBuffer.BYTES_PER_PIXEL);
	}

	/**
	 * Creates the optimized Area.
	 *
	 * @param img the source image
	 * @param area the area
	 */
	public OptimizedAreaImpl(final Image img, final Area area) {
		final PixelReader reader = img.getPixelReader();
		int width = area.getWidth();
		int height = area.getHeight();
		buffer = new byte[width * height * BgraPreImageBuffer.BYTES_PER_PIXEL];
		reader.getPixels(area.getTopLeft().getX(), area.getTopLeft().getY(), width, height,
				PixelFormat.getByteBgraPreInstance(), buffer, 0, width
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

	private static byte colorValueToByte(final double color) {
		return (byte) (color * 255.0);
	}

	/**
	 * Builds an optimized area for
	 *
	 * @param img the source image
	 * @param area the area
	 * @param x the current x coordinate
	 * @return the optimized area
	 */
	public static OptimizedArea forColumn(final Image img, final Area area, final int x) {
		if (x < area.getLeft() || x >= area.getLeft() + area.getWidth()) {
			throw new IllegalArgumentException(
					"The column requested (" + x + ") is not inside the area bounds!");
		}

		return new OptimizedAreaImpl(img, x, area.getTop(), 1, area.getHeight());
	}

	public static OptimizedArea forLine(final Image img, final Area area, final int y) {
		if (y < area.getTop() || y >= area.getTop() + area.getHeight()) {
			throw new IllegalArgumentException(
					"The line requested (" + y + ") is not inside the area bounds!");
		}

		return new OptimizedAreaImpl(img, area.getLeft(), y, area.getWidth(), 1);
	}

}
