package name.ajuhzee.imageproc.processing.ocr.adjust;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.Direction;
import name.ajuhzee.imageproc.util.ImageUtils;

import java.util.OptionalInt;

public class LineOrientationScore {

	private final int blankLines;

	private static int getBlankLines(final Image img) {
		OptionalInt whiteLine;
		final OptionalInt firstLineWithContent =
				ImageUtils.getFirstSliceWithColor(img, Color.BLACK, 0, Direction.POSITIVE_Y);
		if (!firstLineWithContent.isPresent()) {
			return (int) img.getHeight();
		}
		int nextLine = firstLineWithContent.getAsInt() + 1;
		int blankLines = firstLineWithContent.getAsInt();
		while ((whiteLine = ImageUtils.getFirstSliceWithoutColor(img, Color.BLACK, nextLine, Direction.POSITIVE_Y))
				.isPresent()) {
			final int curLine = whiteLine.getAsInt();
			final OptionalInt lineWithContent =
					ImageUtils.getFirstSliceWithColor(img, Color.BLACK, curLine, Direction.POSITIVE_Y);

			blankLines += lineWithContent.orElse((int) img.getHeight()) - curLine;
			nextLine = curLine + 1;
		}

		return blankLines;
	}

	private static int calculateBlankLines(final Image img) {
		return getBlankLines(img);
	}

	public LineOrientationScore(final Image img) {
		blankLines = calculateBlankLines(img);
	}

	public double getScore() {
		return blankLines;
	}

	@Override
	public final String toString() {
		return "LineOrientationScore{" +
				"blankLines=" + blankLines +
				'}';
	}
}
