package name.ajuhzee.imageproc.processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		int previousBlacks = getBlackPixelsInRow(img, startY);
		for (int y = startY + 1; y < height; ++y) {
			int curBlackPixels = getBlackPixelsInRow(img, y);

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

	private static int getBlackPixelsInRow(Image img, int y) {
		PixelReader pixelReader = img.getPixelReader();
		int width = (int) img.getWidth();

		int blackPixels = 0;
		for (int x = 0; x < width; ++x) {
			Color current = pixelReader.getColor(x, y);
			if (current.getBrightness() < 0.01) {
				++blackPixels;
			}
		}
		return blackPixels;
	}
}
