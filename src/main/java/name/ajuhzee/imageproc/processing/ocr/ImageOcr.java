package name.ajuhzee.imageproc.processing.ocr;

import com.google.common.math.DoubleMath;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.Area;
import name.ajuhzee.imageproc.processing.Direction;
import name.ajuhzee.imageproc.processing.ImageEditing;
import name.ajuhzee.imageproc.processing.ocr.adjust.LineOrientationScore;
import name.ajuhzee.imageproc.processing.ocr.criteria.DimensionsCriterion;
import name.ajuhzee.imageproc.processing.ocr.criteria.MatchingCriterion;
import name.ajuhzee.imageproc.processing.ocr.criteria.PixelDifferenceComparator;
import name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber.EulerNumberCriterion;
import name.ajuhzee.imageproc.processing.ocr.criteria.pixelamount.PixelAmountCriterion;
import name.ajuhzee.imageproc.util.ImageUtils;
import name.ajuhzee.imageproc.util.Point2DInt;

import java.util.*;
import java.util.Map.Entry;

/**
 * Provides several functions for the ocr plugin.
 *
 * @author Ajuhzee
 */
public final class ImageOcr {

	// Unicode FFFF is the "Noncharacter"
	private static final String CHARACTER_NOT_RECOGNIZED_INDICATOR = "￿";

	private static final int MIN_SPACE_WIDTH = 5;

	private static final double CRITERION_PIXEL_AMOUNT_MAXIMUM_DEVIATION = 0.2;

	private static final int CRITERION_PIXEL_AMOUNT_ALLOWED_PIXEL_DEVIATION = 2;

	private static final double CRITERION_DIMENSION_MAXIMUM_DEVIATION = 0.1;

	private static final int CRITERION_DIMENSION_ALLOWED_PIXEL_DEVIATION = 2;

	private static final List<MatchingCriterion> MATCHING_CRITERIA;

	static {
		final List<MatchingCriterion> matchingCriteria = new ArrayList<>();
		matchingCriteria.add(new DimensionsCriterion(CRITERION_DIMENSION_MAXIMUM_DEVIATION,
				CRITERION_DIMENSION_ALLOWED_PIXEL_DEVIATION));
		matchingCriteria.add(new PixelAmountCriterion(CRITERION_PIXEL_AMOUNT_MAXIMUM_DEVIATION,
				CRITERION_PIXEL_AMOUNT_ALLOWED_PIXEL_DEVIATION));
		matchingCriteria.add(new EulerNumberCriterion());

		MATCHING_CRITERIA = matchingCriteria;
	}

	public static Image adjustImage(final Image img) {
		final Map<Double, LineOrientationScore> scoreForAngle = new TreeMap<>();

		int curStep = 14;
		Entry<Double, LineOrientationScore> bestEntry = null;
		while ((bestEntry == null || bestEntry.getValue().getScore() < 0) && curStep > 1) {
			curStep *= 0.75;
			collectScoreForAngles(img, scoreForAngle, -90, 89, curStep);
			bestEntry = getEntryWithMaxScore(scoreForAngle);
		}

		assert bestEntry != null;
		final double inaccurateBestAngle = bestEntry.getKey();
		collectScoreForAngles(img, scoreForAngle, inaccurateBestAngle - curStep, inaccurateBestAngle + curStep, 0.25);
		final double bestAngle = getEntryWithMaxScore(scoreForAngle).getKey();


		Image result = img;
		if (DoubleMath.fuzzyEquals(0.0, bestAngle, 0.01)) {
			result = ImageEditing.rotate(img, bestAngle, Color.WHITE);
		}
		return ImageUtils.cropToContent(result, Color.BLACK);
	}

	private static Entry<Double, LineOrientationScore> getEntryWithMaxScore(
			final Map<Double, LineOrientationScore> scoreForAngle) {
		final Optional<Entry<Double, LineOrientationScore>> max = scoreForAngle.entrySet().stream()
				.max((e1, e2) -> Double.compare(e1.getValue().getScore(), e2.getValue().getScore()));
		assert max.isPresent();
		return max.get();
	}

	private static int getMinBlank(final Image img) {
		final Image cropped1 = ImageUtils.cropToContent(img, Color.BLACK);
		final Image cropped2 = ImageUtils.cropToContent(ImageEditing.rotate(img, 45, Color.WHITE), Color.BLACK);
		final Image cropped3 = ImageUtils.cropToContent(ImageEditing.rotate(img, -45, Color.WHITE), Color.BLACK);

		final double minFractionOfBlankLines = 0.08;
		final double img1Min = cropped1.getHeight() * minFractionOfBlankLines;
		final double img2Min = cropped2.getHeight() * minFractionOfBlankLines;
		final double img3Min = cropped3.getHeight() * minFractionOfBlankLines;
		return (int) Math.min(Math.min(img1Min, img2Min), img3Min);
	}

	private static void collectScoreForAngles(final Image img, final Map<Double, LineOrientationScore> scoreForAngle,
											  final double from,
											  final double to, final double step) {
		if (from > to) {
			throw new IllegalArgumentException(String.format("<from>(%d) may not be smaller than <to>(%d)", from, to));
		}
		double curRotate = from;
		Image curImage;
		while (curRotate <= to) {
			if (!scoreForAngle.containsKey(curRotate)) {
				curImage = ImageEditing.rotate(img, curRotate, Color.WHITE);
				curImage = ImageUtils.cropToContent(curImage, Color.BLACK);
				scoreForAngle.put(curRotate, new LineOrientationScore(curImage));
			}
			curRotate += step;
		}
	}

	/**
	 * Recognises lines in an image.
	 *
	 * @param img the image to search and recognize lines in
	 * @return a list with the recognized lines
	 */
	public static List<RecognizedLine> recognizeLines(final Image img) {
		final List<RecognizedLine> recognizedLines = new ArrayList<>();
		int y = 0;
		Optional<RecognizedLine> line;
		while ((line = getNextLine(img, y)).isPresent()) {
			recognizedLines.add(line.get());
			y = line.get().getBottomY() + 1;
		}

		return recognizedLines;
	}

	private static Optional<RecognizedLine> getNextLine(final Image img, final int startY) {
		final OptionalInt lineStart = ImageUtils.getFirstSliceWithColor(img, Color.BLACK, startY, Direction
				.POSITIVE_Y);
		if (!lineStart.isPresent()) {
			return Optional.empty();
		}

		final int lineStartY = lineStart.getAsInt();
		final OptionalInt lineEnd =
				ImageUtils.getFirstSliceWithoutColor(img, Color.BLACK, lineStartY + 1, Direction.POSITIVE_Y);

		return Optional.of(new RecognizedLine(lineStartY, lineEnd.orElse(((int) img.getHeight()) - 1)));
	}

	/**
	 * Recognizes single characters in a recognized line.
	 *
	 * @param img the image to search and recognize chars in
	 * @param lines a list with the recognized lines
	 * @return a list with the recognized characters
	 */
	public static List<List<RecognizedChar>> recognizeChars(final Image img, final List<RecognizedLine> lines) {
		final List<List<RecognizedChar>> lineChars = new ArrayList<>();
		for (final RecognizedLine line : lines) {
			final List<RecognizedChar> chars = new ArrayList<>();
			chars.addAll(recognizeChars(img, line));
			lineChars.add(chars);
		}
		return lineChars;
	}

	/**
	 * Recognizes single characters in a recognized line.
	 *
	 * @param img the image to search and recognize chars in
	 * @param line the recognized line where the character is in
	 * @return a list with the recognized characters, which contains several characters for each line
	 */
	private static List<RecognizedChar> recognizeChars(final Image img, final RecognizedLine line) {
		final List<RecognizedChar> recognizedChars = new ArrayList<>();

		Optional<RecognizedChar> recognizedChar = getNextChar(img, line, 0);
		while (recognizedChar.isPresent()) {
			recognizedChars.add(recognizedChar.get());
			final int nextX = recognizedChar.get().getBoundingBox().getBottomRight().getX() + 1;
			recognizedChar = getNextChar(img, line, nextX);
		}

		return recognizedChars;
	}

	private static Optional<RecognizedChar> getNextChar(final Image img, final RecognizedLine line, final int startX) {
		int lastXIdx = (int) img.getWidth() - 1;
		if (startX > lastXIdx) {
			return Optional.empty();
		}
		Area charStartXSearchArea = new Area(startX, lastXIdx, line.getTopY(), line.getBottomY());
		final OptionalInt charStartXOptional = ImageUtils.getFirstSliceWithColor(img,
				charStartXSearchArea, Color.BLACK, Direction.POSITIVE_X);

		if (!charStartXOptional.isPresent()) {
			return Optional.empty();
		}

		final int charStartX = charStartXOptional.getAsInt();

		Area charEndXSearchArea = new Area(charStartX, lastXIdx, line.getTopY(), line.getBottomY());
		final int charEndX = findCharEndX(img, charEndXSearchArea);


		Area verticalSearchArea = new Area(charStartX, charEndX, line.getTopY(), line.getBottomY());
		final int charStartY = ImageUtils
				.getFirstSliceWithColor(img, verticalSearchArea, Color.BLACK, Direction.POSITIVE_Y)
				.getAsInt();

		final int charEndY = ImageUtils
				.getFirstSliceWithColor(img, verticalSearchArea, Color.BLACK, Direction.NEGATIVE_Y)
				.getAsInt();


		final Point2DInt topLeft = new Point2DInt(charStartX, charStartY);
		final Point2DInt bottomRight = new Point2DInt(charEndX, charEndY);

		return Optional.of(new RecognizedChar(new Area(topLeft, bottomRight)));
	}

	private static int findCharEndX(final Image img, final Area searchArea) {

		OptionalInt potentialCharEnd = ImageUtils.getFirstSliceWithoutColor(img, searchArea, Color.BLACK,
				Direction.POSITIVE_X);
		while (potentialCharEnd.isPresent()) {

			Area charStartSearchArea = new Area(potentialCharEnd.getAsInt(), searchArea.getRight(), searchArea
					.getTop(),
					searchArea.getBottom());
			final OptionalInt nextCharStart = ImageUtils
					.getFirstSliceWithColor(img, charStartSearchArea, Color.BLACK,
							Direction.POSITIVE_X);
			if (!nextCharStart.isPresent()) {
				break;
			}

			final int spaceWidth = nextCharStart.getAsInt() - potentialCharEnd.getAsInt();
			if (spaceWidth > MIN_SPACE_WIDTH) {
				break;
			}

			Area charEndSearchArea = new Area(nextCharStart.getAsInt(), searchArea.getRight(), searchArea.getTop(),
					searchArea.getBottom());
			potentialCharEnd =
					ImageUtils.getFirstSliceWithoutColor(img, charEndSearchArea, Color.BLACK,
							Direction.POSITIVE_X);
		}

		final int width = (int) img.getWidth();
		return potentialCharEnd.orElse(width) - 1;
	}

	/**
	 * @param img the image
	 * @param recognizedLineChars each recognized characters
	 * @param characterSet the character set from the database
	 * @return the matched character as a string
	 */
	public static String matchCharacters(final Image img, final List<List<RecognizedChar>> recognizedLineChars,
										 final CharacterSet characterSet, final boolean pixelDeviationEnabled,
										 final double pixelDeviationPercent, final int pixelDeviationAllowed,
										 final boolean dimensionDeviationEnabled,
										 final double dimensionDeviationPercent,
										 final int dimensionDeviationAllowed, final boolean eulerNumberEnabled) {
		final List<MatchingCriterion> matchingCriteria = new ArrayList<>();
		if (pixelDeviationEnabled) {
			matchingCriteria.add(new PixelAmountCriterion(pixelDeviationPercent, pixelDeviationAllowed));
		}
		if (dimensionDeviationEnabled) {
			matchingCriteria.add(new DimensionsCriterion(dimensionDeviationPercent, dimensionDeviationAllowed));
		}
		if (eulerNumberEnabled) {
			matchingCriteria.add(new EulerNumberCriterion());
		}

		return matchCharacters(img, recognizedLineChars, characterSet, matchingCriteria);
	}

	private static String matchCharacters(final Image img, final List<List<RecognizedChar>> recognizedLineChars,
										  final CharacterSet characterSet,
										  final List<MatchingCriterion> matchingCriteria) {
		final StringBuilder sb = new StringBuilder();
		for (final List<RecognizedChar> lineChars : recognizedLineChars) {
			double prevRightX = Double.MAX_VALUE;
			int charInRow = 1;
			for (final RecognizedChar recognizedChar : lineChars) {
				addSpaces(sb, prevRightX, recognizedChar, characterSet);
				prevRightX = recognizedChar.getBoundingBox().getBottomRight().getX();

				final Area boundingBox = recognizedChar.getBoundingBox();
				final Point2DInt topLeft = boundingBox.getTopLeft();
				final Image charImage =
						new WritableImage(img.getPixelReader(), topLeft.getX(), topLeft.getY(),
								boundingBox.getWidth(), boundingBox.getHeight());
				final Optional<Character> matchedChar = matchCharacter(charImage, characterSet, matchingCriteria);

				if (matchedChar.isPresent()) {
					sb.append(matchedChar.get());
				} else {
					sb.append(CHARACTER_NOT_RECOGNIZED_INDICATOR);
				}
				charInRow++;
			}
			sb.append('\n');
		}

		return sb.toString();
	}

	private static void addSpaces(final StringBuilder sb, final double prevRightX, final RecognizedChar recognizedChar,
								  final CharacterSet characterSet) {
		final double leftX = recognizedChar.getBoundingBox().getBottomLeft().getX();
		final int distanceToLastChar = (int) (leftX - prevRightX);
		final int spaceCount = distanceToLastChar / characterSet.getSpaceWidth();
		for (int i = 0; i < spaceCount; ++i) {
			sb.append(' ');
		}
	}

	private static Optional<Character> matchCharacter(final Image charToMatch, final CharacterSet characterSet,
													  final List<MatchingCriterion> matchingCriteria) {
		return characterSet.getCharacters().stream()
				.filter((potentialChar) ->
						matchingCriteria.stream()
								.allMatch(
										(criterion) -> criterion.matches(charToMatch, potentialChar.getSourceImage())
								)
				)
				.min(new PixelDifferenceComparator(charToMatch))
				.map(TemplateChar::getRepresentedChar);
	}

	private ImageOcr() {
	}

}
