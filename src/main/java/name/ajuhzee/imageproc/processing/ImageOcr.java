package name.ajuhzee.imageproc.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public final class ImageOcr {

	private static final double LINE_TRANSITION_RATIO = 0.1;

	private ImageOcr() {
	}

	public static List<RecognizedLine> recognizeLines(Image img) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();

		List<RecognizedLine> recognizedLines = new ArrayList<>();
		int y = 0;
		while (y != height) {
			Optional<RecognizedLine> line = getNextLine(img, y);
			if (line.isPresent()) {
				recognizedLines.add(line.get());
				y = line.get().getBottomY();
			} else {
				break;
			}
		}

		return recognizedLines;
	}

	private static Optional<RecognizedLine> getNextLine(Image img, int startY) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();

		List<Integer> lineTransitions = new ArrayList<Integer>();
		int previousBlacks = sumBlackPixelsInRow(img, startY);
		for (int y = startY + 1; y < height; ++y) {
			int curBlackPixels = sumBlackPixelsInRow(img, y);

			boolean isStartOfLine = previousBlacks == 0 && curBlackPixels != 0;
			boolean isEndOfLine = curBlackPixels == 0 && previousBlacks != 0;
			if (isStartOfLine || isEndOfLine) {
				lineTransitions.add(y);
			}

			if (lineTransitions.size() == 2) {
				return Optional.of(new RecognizedLine(lineTransitions.get(0), lineTransitions.get(1)));
			}

			previousBlacks = curBlackPixels;
		}

		return Optional.empty();
	}

	public static List<RecognizedChar> recognizeChars(Image img, List<RecognizedLine> lines) {
		List<RecognizedChar> chars = new ArrayList<RecognizedChar>();
		for (RecognizedLine line : lines) {
			chars.addAll(recognizeChars(img, line));
		}
		return chars;
	}

	public static List<RecognizedChar> recognizeChars(Image img, RecognizedLine line) {
		int width = (int) img.getWidth();
		List<RecognizedChar> recognizedChars = new ArrayList<RecognizedChar>();

		Optional<RecognizedChar> recognizedChar = getNextChar(img, line, 0);
		while (recognizedChar.isPresent()) {
			recognizedChars.add(recognizedChar.get());
			int nextX = (int) recognizedChar.get().getBoundingBox().getBottomRight().getX();
			recognizedChar = getNextChar(img, line, nextX);
		}

		return recognizedChars;
	}

	private static Optional<RecognizedChar> getNextChar(Image img, RecognizedLine line, int startX) {
		int width = (int) img.getWidth();

		int previousBlacks = sumBlackPixels(img, line.getTopY(), line.getBottomY(), startX, startX + 1);
		int charStartX = -1;
		int charEndX = -1;
		for (int x = startX + 1; x != width; ++x) {
			int curBlackPixels = sumBlackPixels(img, line.getTopY(), line.getBottomY(), x, x + 1);

			boolean isStart = previousBlacks == 0 && curBlackPixels != 0;
			if (isStart) {
				charStartX = x;
				break;
			}

			previousBlacks = curBlackPixels;
		}

		if (charStartX == -1) {
			return Optional.empty();
		}

		for (int x = charStartX; x != width; ++x) {
			int curBlackPixels = sumBlackPixels(img, line.getTopY(), line.getBottomY(), x, x + 1);
			boolean isEnd = curBlackPixels == 0 && previousBlacks != 0;
			if (isEnd && nextSpaceWidth(img, line, x) > 8) {
				charEndX = x;
				break;
			}

			previousBlacks = curBlackPixels;
		}

		if (charEndX == -1) {
			return Optional.empty();
		}

		Point2D topLeft = new Point2D(charStartX, line.getTopY());
		Point2D bottomRight = new Point2D(charEndX, line.getBottomY());
		return Optional.of(new RecognizedChar(new BoundingBox(topLeft, bottomRight)));
	}

	private static int nextSpaceWidth(Image img, RecognizedLine line, int startX) {
		int width = (int) img.getWidth();
		int x = startX;
		while (x != width) {
			int blackPixels = sumBlackPixels(img, line.getTopY(), line.getBottomY(), x, x + 1);
			if (blackPixels != 0) {
				break;
			}
			++x;
		}

		return x - startX;
	}

	private static int sumBlackPixelsInRow(Image img, int y) {
		return sumBlackPixels(img, y, y + 1, 0, (int) img.getWidth());
	}

	/**
	 * Sum the black pixels in the specified area. Indices are inclusive.
	 * 
	 * @param img
	 * @param startY
	 * @param endY
	 * @param startX
	 * @param endX
	 * @return
	 */
	private static int sumBlackPixels(Image img, int startY, int endY, int startX, int endX) {
		PixelReader pixelReader = img.getPixelReader();
		int xStep = (startX < endX) ? 1 : -1;
		int yStep = (startY < endY) ? 1 : -1;

		int blackPixels = 0;
		for (int y = startY; y < endY; y += yStep) {
			for (int x = startX; x < endX; x += xStep) {
				Color current = pixelReader.getColor(x, y);
				if (current.getBrightness() < 0.01) {
					++blackPixels;
				}
			}
		}
		return blackPixels;
	}
}
