package name.ajuhzee.imageproc.processing.ocr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.BoundingBox;
import name.ajuhzee.imageproc.util.ImageUtils;

public final class ImageOcr {

	private static final int MIN_SPACE_WIDTH = 7;
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

		OptionalInt charStartXOptional = findCharStartX(img, line, startX);

		if (!charStartXOptional.isPresent()) {
			return Optional.empty();
		}

		int charStartX = charStartXOptional.getAsInt();
		OptionalInt charEndXOptional = findCharEndX(img, line, charStartX);

		if (!charEndXOptional.isPresent()) {
			return Optional.empty();
		}

		int charEndX = charEndXOptional.getAsInt();
		int charStartY = findCharStartY(img, line, charStartX, charEndX);
		int charEndY = findCharEndY(img, line, charStartX, charEndX);

		Point2D topLeft = new Point2D(charStartX, charStartY);
		Point2D bottomRight = new Point2D(charEndX, charEndY);
		return Optional.of(new RecognizedChar(new BoundingBox(topLeft, bottomRight)));
	}

	private static int findCharEndY(Image img, RecognizedLine line, int charStartX, int charEndX) {
		int endY = line.getBottomY();
		while (endY > line.getTopY() && sumBlackPixels(img, endY, endY + 1, charStartX, charEndX) == 0) {
			--endY;
		}
		return endY;

	}

	private static int findCharStartY(Image img, RecognizedLine line, int charStartX, int charEndX) {
		int startY = line.getTopY();
		while (startY < line.getBottomY() && sumBlackPixels(img, startY, startY + 1, charStartX, charEndX) == 0) {
			++startY;
		}
		return startY;
	}

	private static OptionalInt findCharEndX(Image img, RecognizedLine line, int startX) {
		int width = (int) img.getWidth();
		int charEndX = -1;

		int prevBlacks = sumBlackPixels(img, line.getTopY(), line.getBottomY(), startX, startX + 1);
		for (int x = startX; x != width; ++x) {
			int curBlackPixels = sumBlackPixels(img, line.getTopY(), line.getBottomY(), x, x + 1);
			boolean isEnd = curBlackPixels == 0 && prevBlacks != 0;
			if (isEnd && nextSpaceWidth(img, line, x) > MIN_SPACE_WIDTH) {
				charEndX = x;
				break;
			}

			prevBlacks = curBlackPixels;
		}
		if (charEndX == -1) {
			return OptionalInt.empty();
		}
		return OptionalInt.of(charEndX);
	}

	private static OptionalInt findCharStartX(Image img, RecognizedLine line, int startX) {
		int width = (int) img.getWidth();
		int charStartX = -1;

		int previousBlacks = sumBlackPixels(img, line.getTopY(), line.getBottomY(), startX, startX + 1);
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
			return OptionalInt.empty();
		}
		return OptionalInt.of(charStartX);
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

	public static String matchCharacters(Image img, List<RecognizedChar> recognizedChars) {
		StringBuilder sb = new StringBuilder();
		for (RecognizedChar recognizedChar : recognizedChars) {
			BoundingBox boundingBox = recognizedChar.getBoundingBox();
			Point2D topLeft = boundingBox.getTopLeft();
			Image charImage = new WritableImage(img.getPixelReader(), (int) topLeft.getX(), (int) topLeft.getY(),
					(int) boundingBox.getWidth(), (int) boundingBox.getHeight());
			sb.append(matchCharacter(charImage));
		}
		return sb.toString();
	}

	private static Character matchCharacter(Image recognizedChar) {
		Character matchedChar = null;
		try {
			ImageUtils.saveImage(new File("P:\\data\\curMatching\\curRecognized.png"), recognizedChar);
			double minPixelDeviation = Double.MAX_VALUE;
			for (TemplateChar templateChar : Templates.BASE_TEMPLATE.getCharacters()) {
				ImageUtils.saveImage(new File("P:\\data\\curMatching\\curTemplate.png"), templateChar.getSourceImage());
				double curPixelDeviation = getPixelDifference(recognizedChar, templateChar.getSourceImage());
				if (curPixelDeviation < minPixelDeviation) {
					matchedChar = new Character(templateChar.getRepresentedChar());
					minPixelDeviation = curPixelDeviation;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return matchedChar;
	}

	private static double getPixelDifference(Image recognizedChar, Image templateChar) {
		int width = Math.max((int) recognizedChar.getWidth(), (int) templateChar.getWidth());
		int height = Math.max((int) recognizedChar.getHeight(), (int) templateChar.getHeight());
		PixelReader scaledRecognizedChar = ImageUtils.increaseCanvasSize(recognizedChar, width, height, Color.WHITE)
				.getPixelReader();
		PixelReader scaledTemplateChar = ImageUtils.increaseCanvasSize(templateChar, width, height, Color.WHITE)
				.getPixelReader();

		double difference = 0;

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				Color recognizedColor = scaledRecognizedChar.getColor(x, y);
				Color templateColor = scaledTemplateChar.getColor(x, y);

				difference += Math.abs(templateColor.getBrightness() - recognizedColor.getBrightness());
			}
		}
		return difference;
	}
}
