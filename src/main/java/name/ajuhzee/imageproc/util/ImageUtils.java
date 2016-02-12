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
import name.ajuhzee.imageproc.processing.ocr.RecognizedChar;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import static name.ajuhzee.imageproc.plugin.image.ocr.Ocr.*;

/**
 * Provides some image utilities.
 *
 * @author Ajuhzee
 */
public final class ImageUtils {

	/**
	 *  Executes a pixel action.
	 */
	@FunctionalInterface
	public static interface PixelAction {

		public void forPixels(int x, int y);
	}

	/**
	 * Executes a 2DPointAction.
	 */
	@FunctionalInterface
	public interface Array2DPointAction {

		void execute(int x, int y);
	}

	/**
	 * Loads the image from file.
	 *
	 * @param file the path to save the image
	 * @throws IOException if the image could not be loaded
	 */
	public static Image loadImage(final File file) throws Exception {
		final BufferedImage img = ImageIO.read(file);
		final Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();

		return fxImage;
	}

	/**
	 * Loads the image from input stream file.
	 *
	 * @param file the input stream file to save the image
	 * @throws IOException if the image could not be loaded
	 */
	public static Image loadImage(final InputStream file) throws Exception {
		final BufferedImage img = ImageIO.read(file);
		final Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();

		return fxImage;
	}

	/**
	 * Saves the image by file.
	 *
	 * @param file the file to save the image in
	 * @param fxImage the source image
	 * @throws IOException if the image could not be saved
	 */
	public static void saveImage(final File file, final Image fxImage) throws IOException {
		final BufferedImage img = SwingFXUtils.fromFXImage(fxImage, null);

		final File saveTo = addPngExtension(file);
		ImageIO.write(img, "PNG", saveTo);
	}

	/**
	 * Saves the image by path.
	 *
	 * @param path the path to save the image in
	 * @param fxImage the source image
	 * @throws IOException if the image could not be saved
	 */
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

	/**
	 * Gets the number of the first slice with color by start value.
	 *
	 * @param img the source image
	 * @param startValue the start value
	 * @param color the color to look for
	 * @param direction the direction of the slice
	 * @return the number of the first slice with color
	 */
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

	/**
	 * Gets the number of the first slice with color by area.
	 *
	 * @param img the source image
	 * @param area the area
	 * @param color the color to look for
	 * @param direction the direction of the slice
	 * @return the number of the first slice with color
	 */
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

	/**
	 * Gets the number of the first slice without color by area.
	 *
	 * @param img the source image
	 * @param area the area
	 * @param color the color to look for
	 * @param direction the direction of the slice
	 * @return the number of the first slice without color
	 */
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

	/**
	 * Gets the number of the first slice without color by start value.
	 *
	 * @param img the source image
	 * @param color the color to look for
	 * @param startValue the start value
	 * @param direction the direction of the slice
	 * @return the number of the first slice without color
	 */
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

	/**
	 * Crops the image to his content.
	 *
	 * @param img the source image
	 * @param contentColor the color of his content
	 * @return the cropped image
	 */
	public static Image cropToContent(final Image img, final Color contentColor) {
		final OptionalInt potentialStartY = getFirstSliceWithColor(img, contentColor, 0, Direction.POSITIVE_Y);
		if (!potentialStartY.isPresent()) {
			return img;
		}
		final int startY = potentialStartY.getAsInt();
		final int startX = getFirstSliceWithColor(img, contentColor, 0, Direction.POSITIVE_X).getAsInt();

		final int endY = getFirstSliceWithColor(img, contentColor, (int) img.getHeight() - 1, Direction.NEGATIVE_Y).getAsInt();
		final int endX = getFirstSliceWithColor(img, contentColor, (int) img.getWidth() - 1, Direction.NEGATIVE_X).getAsInt();

		return crop(img, startX, startY, endX - startX, endY - startY);
	}

	private static Image crop(final Image img, final int startX, final int startY, final int width, final int height) {
		return new WritableImage(img.getPixelReader(), startX, startY, width, height);
	}

	/**
	 * Increases the image size and fills up new areas with a color.
	 *
	 * @param img the source image
	 * @param desiredWidth the new width
	 * @param desiredHeight the new height
	 * @param fillColor the filling color
	 * @return the new image
	 */
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

	/**
	 * Executes a pixel action for every pixel.
	 *
	 * @param startX start coordinate in x direction
	 * @param endX start coordinate in x direction
	 * @param startY start coordinate in x direction
	 * @param endY start coordinate in x direction
	 * @param action the pixel action which shall be executed
	 */
	public static void forEachPixel(final int startX, final int endX, final int startY, final int endY,
									final PixelAction action) {
		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				action.forPixels(x, y);
			}
		}
	}

	/**
	 * Executes a pixel action for every pixel.
	 *
	 * @param array source array
	 * @param action the point action which shall be executed
	 * @param <T> the array the action has been executed for
	 */
	public static <T> void forEach2DArrayElement(final T[][] array, final Array2DPointAction action) {
		for (int y = 0; y < array.length; ++y) {
			for (int x = 0; x < array[0].length; ++x) {
				action.execute(x, y);
			}
		}
	}

	/**
	 * Prepares the execution of every pixel.
	 *
	 * @param img source image
	 * @param action the pixel action which shall be executed
	 */
	public static void forEachPixel(final Image img, final PixelAction action) {
		final int width = (int) img.getWidth();
		final int height = (int) img.getHeight();
		forEachPixel(0, width, 0, height, action);
	}

	/**
	 * Marks recognized characters in the image.
	 *
	 * @param img source image
	 * @param recognizedChars the list of recognized chars
	 * @return the image with marked characters
	 */
	public static Image markCharactersOnImage(Image img, List<RecognizedChar> recognizedChars) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		WritableImage withMarkedLines = new WritableImage(img.getPixelReader(), width, height);
		PixelWriter writer = withMarkedLines.getPixelWriter();

			for (RecognizedChar recChar : recognizedChars) {
				Area boundingBox = recChar.getBoundingBox();
				int topY = (int) boundingBox.getTopLeft().getY();
				int bottomY = (int) boundingBox.getBottomLeft().getY();
				int leftX = (int) boundingBox.getTopLeft().getX();
				int rightX = (int) boundingBox.getTopRight().getX();

				drawLine(writer, START_COLOR, leftX, leftX, topY, bottomY);
				drawLine(writer, START_COLOR, leftX, rightX, topY, topY);
				drawLine(writer, END_COLOR, rightX, rightX, topY, bottomY);
				drawLine(writer, END_COLOR, leftX, rightX, bottomY, bottomY);
			}
		return withMarkedLines;
	}

	/**
	 * Draws a line on the given pixel writer
	 *
	 * @param writer
	 * @param color the color of the line
	 * @param startX the start x of the line to be drawn
	 * @param endX the end x of the line to be drawn
	 * @param startY the start y of the line to be drawn
	 * @param endY the end y of the line to be drawn
	 */
	public static void drawLine(PixelWriter writer, Color color, int startX, int endX, int startY, int endY) {
		double stepY;
		double stepX;
		double yLength = (double) endY - startY;
		double xLength = (double) endX - startX;
		if (xLength < yLength) {
			stepY = 1;
			stepX = yLength == 0 ? 0 : (xLength / yLength);
		} else {
			stepX = 1;
			stepY = xLength == 0 ? 0 : (yLength / xLength);
		}

		double y = startY;
		double x = startX;
		do {
			do {
				writer.setColor((int) x, (int) y, color);
				x += stepX;
			} while (x < endX);
			y += stepY;
		} while (y < endY);
	}

	private ImageUtils() {
	}
}
