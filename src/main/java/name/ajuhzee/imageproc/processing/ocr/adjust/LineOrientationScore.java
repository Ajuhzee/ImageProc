package name.ajuhzee.imageproc.processing.ocr.adjust;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.Area;
import name.ajuhzee.imageproc.processing.Direction;
import name.ajuhzee.imageproc.util.ImageUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.OptionalDouble;
import java.util.OptionalInt;

public class LineOrientationScore {

	private final int blankLines;

	private final double averageGapWidth;


	private static OptionalDouble getAverageGapWidthForLine(final Image img, final int y) {
		final Collection<Integer> gapWidths = new ArrayList<>();

		final Area line = new Area(0, (int) img.getWidth() - 1, y, y);
		OptionalInt curBlack = ImageUtils.getFirstSliceWithColor(img, line, Color.BLACK, Direction.POSITIVE_X);
		if (!curBlack.isPresent()) {
			return OptionalDouble.empty();
		}
		while (curBlack.isPresent()) {
			Area gapStartIdxSearchArea =
					new Area(curBlack.getAsInt(), line.getRight(), line.getTop(), line.getBottom());
			OptionalInt potentialGapStartIdx =
					ImageUtils.getFirstSliceWithColor(img, gapStartIdxSearchArea, Color.WHITE, Direction.POSITIVE_X);
			if (!potentialGapStartIdx.isPresent()) {
				break;
			}

			int gapStartIdx = potentialGapStartIdx.getAsInt();
			Area nextBlackStartIdxSearchArea = new Area(gapStartIdx, line.getRight(), line.getTop(), line.getBottom());
			curBlack = ImageUtils
					.getFirstSliceWithColor(img, nextBlackStartIdxSearchArea, Color.BLACK, Direction.POSITIVE_X);
			if (!curBlack.isPresent()) {
				break;
			}
			gapWidths.add(curBlack.getAsInt() - gapStartIdx);
		}

		return gapWidths.stream().mapToInt(Integer::valueOf).average();
	}

	private static int getBlankLines(final Image img) {
		OptionalInt lineWithContent;
		int blankLines = 0;
		int lastLine = 0;
		int nextLine = 0;
		while ((lineWithContent = ImageUtils.getFirstSliceWithColor(img, Color.BLACK, nextLine, Direction.POSITIVE_X))
				.isPresent()) {
			final int curLine = lineWithContent.getAsInt();
			blankLines += curLine - lastLine - 1;
			lastLine = curLine;
			nextLine = curLine + 1;
		}

		blankLines += img.getHeight() - lastLine;

		return blankLines;
	}

	public LineOrientationScore(final Image img) {
		blankLines = calculateBlankLines(img);
		averageGapWidth = calculateAverageGapWidth(img);
	}

	private static double calculateAverageGapWidth(final Image img) {
		final int height = (int) img.getHeight();
		final Collection<OptionalDouble> averages = new ArrayList<>();
		for (int y = 0; y != height; ++y) {
			averages.add(getAverageGapWidthForLine(img, y));
		}

		final OptionalDouble average =
				averages.stream().filter(OptionalDouble::isPresent).mapToDouble(OptionalDouble::getAsDouble).average();

		return average.orElse(Double.POSITIVE_INFINITY);
	}

	private static int calculateBlankLines(final Image img) {
		return getBlankLines(img);
	}

	public double getScore() {
		return averageGapWidth;
	}

	@Override
	public final String toString() {
		return "LineOrientationScore{" +
				"blankLines=" + blankLines +
				", averageGapWidth=" + averageGapWidth +
				'}';
	}
}
