package name.ajuhzee.imageproc.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.Area;
import name.ajuhzee.imageproc.processing.Direction;
import name.ajuhzee.imageproc.processing.OptimizedArea;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public final class ImageUtils {


	@FunctionalInterface
	public static interface PixelAction {

		public void forPixels(int x, int y);
	}

	@FunctionalInterface
	public interface Array2DPointAction {

		void execute(int x, int y);
	}

	public static Image loadImage(final File file) throws Exception {
		final BufferedImage img = ImageIO.read(file);
		final Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();

		return fxImage;
	}

	public static Image loadImage(final InputStream file) throws Exception {
		final BufferedImage img = ImageIO.read(file);
		final Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();

		return fxImage;
	}

	public static void saveImage(final File file, final Image fxImage) throws IOException {
		final BufferedImage img = SwingFXUtils.fromFXImage(fxImage, null);

		final File saveTo = addPngExtension(file);
		ImageIO.write(img, "PNG", saveTo);
	}

	public static void saveImage(final Path path, final Image fxImage) throws IOException {
		final File file = path.toFile();
		saveImage(file, fxImage);
	}

	private static File addPngExtension(final File file) {
		String filename = file.getName();
		if (FilenameUtils.getExtension(filename).equals("")) {
			filename += ".png";
		}

		return new File(file.getParent(), filename);
	}

	public static boolean areaContainsColor(final Image img, final Area area, final Color color) {
		return new OptimizedArea(img, area).containsColor(color);
	}

	public static OptionalInt getFirstSliceWithColor(final Image img, final Color color, final int startValue,
													 final Direction direction) {
		switch (direction) {
			case NEGATIVE_X:
				return IntStream.iterate(startValue, i -> i - 1).limit(startValue + 1)
						.filter((x) -> OptimizedArea.forColumn(img, x).containsColor(color))
						.findFirst();
			case POSITIVE_X:
				return IntStream.range(startValue, (int) img.getWidth())
						.filter((x) -> OptimizedArea.forColumn(img, x).containsColor(color))
						.findFirst();
			case NEGATIVE_Y:
				return IntStream.iterate(startValue, i -> i - 1).limit(startValue + 1)
						.filter((y) -> OptimizedArea.forLine(img, y).containsColor(color))
						.findFirst();
			case POSITIVE_Y:
				return IntStream.range(startValue, (int) img.getHeight())
						.filter((y) -> OptimizedArea.forLine(img, y).containsColor(color))
						.findFirst();
		}
		// can never happen
		return OptionalInt.empty();
	}

	public static OptionalInt getFirstSliceWithColor(final Image img, final Area area, final Color color,
													 final Direction direction) {
		switch (direction) {
			case NEGATIVE_X:
				return IntStream.iterate(area.getRight(), i -> i - 1).limit(area.getWidth())
						.filter((x) -> OptimizedArea.forColumn(img, area, x).containsColor(color))
						.findFirst();
			case POSITIVE_X:
				return IntStream.range(area.getLeft(), area.getLeft() + area.getWidth() - 1)
						.filter((x) -> OptimizedArea.forColumn(img, area, x).containsColor(color))
						.findFirst();
			case NEGATIVE_Y:
				return IntStream.iterate(area.getBottom(), i -> i - 1).limit(area.getHeight())
						.filter((y) -> OptimizedArea.forLine(img, area, y).containsColor(color))
						.findFirst();
			case POSITIVE_Y:
				return IntStream.range(area.getTop(), area.getTop() + area.getHeight() - 1)
						.filter((y) -> OptimizedArea.forLine(img, area, y).containsColor(color))
						.findFirst();
		}
		// can never happen
		return OptionalInt.empty();
	}

	public static OptionalInt getFirstSliceWithoutColor(final Image img, final Area area, final Color color,
														final Direction direction) {
		switch (direction) {
			case NEGATIVE_X:
				return IntStream.iterate(area.getRight(), i -> i - 1).limit(area.getWidth())
						.filter((x) -> !OptimizedArea.forColumn(img, area, x).containsColor(color))
						.findFirst();
			case POSITIVE_X:
				return IntStream.range(area.getLeft(), area.getLeft() + area.getWidth() - 1)
						.filter((x) -> !OptimizedArea.forColumn(img, area, x).containsColor(color))
						.findFirst();
			case NEGATIVE_Y:
				return IntStream.iterate(area.getBottom(), i -> i - 1).limit(area.getHeight())
						.filter((y) -> !OptimizedArea.forLine(img, area, y).containsColor(color))
						.findFirst();
			case POSITIVE_Y:
				return IntStream.range(area.getTop(), area.getTop() + area.getHeight() - 1)
						.filter((y) -> !OptimizedArea.forLine(img, area, y).containsColor(color))
						.findFirst();
		}
		// can never happen
		return OptionalInt.empty();
	}

	public static OptionalInt getFirstSliceWithoutColor(final Image img, final Color color, final int startValue,
														final Direction direction) {
		switch (direction) {
			case NEGATIVE_X:
				return IntStream.iterate(startValue, i -> i - 1).limit(startValue + 1)
						.filter((x) -> !OptimizedArea.forColumn(img, x).containsColor(color))
						.findFirst();
			case POSITIVE_X:
				return IntStream.range(startValue, (int) img.getWidth())
						.filter((x) -> !OptimizedArea.forColumn(img, x).containsColor(color))
						.findFirst();
			case NEGATIVE_Y:
				return IntStream.iterate(startValue, i -> i - 1).limit(startValue + 1)
						.filter((y) -> !OptimizedArea.forLine(img, y).containsColor(color))
						.findFirst();
			case POSITIVE_Y:
				return IntStream.range(startValue, (int) img.getHeight())
						.filter((y) -> !OptimizedArea.forLine(img, y).containsColor(color))
						.findFirst();
		}
		// can never happen
		return OptionalInt.empty();
	}


	public static Image cropToContent(final Image img, final Color contentColor) {
		final OptionalInt potentialStartY = getFirstSliceWithColor(img, contentColor, 0, Direction.POSITIVE_Y);
		if (!potentialStartY.isPresent()) {
			return img;
		}
		final int startY = potentialStartY.getAsInt();
		final int startX = getFirstSliceWithColor(img, contentColor, 0, Direction.POSITIVE_X).getAsInt();

		final int endY =
				getFirstSliceWithColor(img, contentColor, (int) img.getHeight() - 1, Direction.NEGATIVE_Y).getAsInt();
		final int endX =
				getFirstSliceWithColor(img, contentColor, (int) img.getWidth() - 1, Direction.NEGATIVE_X).getAsInt();

		return crop(img, startX, startY, endX - startX, endY - startY);
	}

	public static Image crop(final Image img, final int startX, final int startY, final int width, final int height) {
		return new WritableImage(img.getPixelReader(), startX, startY, width, height);
	}

	public static Image increaseCanvasSize(final Image img, final int desiredWidth, final int desiredHeight,
										   final Color fillColor) {
		final int width = (int) img.getWidth();
		final int height = (int) img.getHeight();
		if (width >= desiredWidth && height >= desiredHeight) {
			return img;
		}

		final WritableImage result = new WritableImage(desiredWidth, desiredHeight);
		fillWith(result, fillColor);
		final PixelWriter resultWriter = result.getPixelWriter();

		final PixelReader srcReader = img.getPixelReader();
		final int offsetX = desiredWidth - width;
		final int offsetY = desiredHeight - height;

		resultWriter.setPixels(offsetX, offsetY, width, height, srcReader, 0, 0);

		return result;
	}

	private static void fillWith(final WritableImage img, final Color fillColor) {
		final PixelWriter writer = img.getPixelWriter();
		forEachPixel(img, (x, y) -> {
			writer.setColor(x, y, fillColor);
		});
	}

	public static void forEachPixel(final int startX, final int endX, final int startY, final int endY,
									final PixelAction action) {
		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				action.forPixels(x, y);
			}
		}
	}

	public static <T> void forEach2DArrayElement(final T[][] array, final Array2DPointAction action) {
		for (int y = 0; y < array.length; ++y) {
			for (int x = 0; x < array[0].length; ++x) {
				action.execute(x, y);
			}
		}
	}

	public static void forEachPixel(final Image img, final PixelAction action) {
		final int width = (int) img.getWidth();
		final int height = (int) img.getHeight();
		forEachPixel(0, width, 0, height, action);
	}

	private ImageUtils() {
	}
}
