package name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.util.ImageUtils;
import name.ajuhzee.imageproc.util.Point2DInt;

import java.util.*;

public class EulerNumberCompute {

	public static final int MINIMUM_PIXELS_IN_AREA = 3;

	public static int getEulerNumber(Image characterImage) {
		OptionalInt[][] areas =
				getEmpty2DOptionalIntArray((int) characterImage.getWidth(), (int) characterImage.getHeight());
		Map<Integer, Integer> foundAreas = new TreeMap<>();

		markBackground(characterImage, areas);

		Optional<Point2DInt> potentialPoint;
		while ((potentialPoint = getNextUninitializedPoint(areas)).isPresent()) {
			Point2DInt point = potentialPoint.get();
			int numberToSet = getNewAreaNumber(characterImage, foundAreas.keySet(), point);
			fillArea(foundAreas, characterImage, areas, (int) point.getX(), (int) point.getY(), numberToSet);
		}

		int eulerNumber = getEulerNumber(foundAreas);
		return eulerNumber;
	}

	private static void markBackground(Image img, OptionalInt[][] areas) {
		PixelReader reader = img.getPixelReader();
		int maxX = (int) img.getWidth() - 1;
		int maxY = (int) img.getHeight() - 1;
		ImageUtils.forEach2DArrayElement(areas, (x, y) -> {
			boolean xAtTheEdge = x == 0 || x == maxX;
			boolean yAtTheEdge = y == 0 || y == maxY;
			if (!xAtTheEdge && !yAtTheEdge) {
				return;
			}

			if (reader.getColor(x, y).equals(Color.WHITE)) {
				fillArea(new HashMap<>(), img, areas, x, y, 0);
			}
		});
	}

	private static OptionalInt[][] getEmpty2DOptionalIntArray(int width, int height) {
		OptionalInt[][] array = new OptionalInt[height][width];
		ImageUtils.forEach2DArrayElement(array, (x, y) -> {
			array[y][x] = OptionalInt.empty();
		});
		return array;
	}


	private static int getNewAreaNumber(Image img, Set<Integer> foundAreas, Point2DInt point) {
		PixelReader pixelReader = img.getPixelReader();
		Color color = pixelReader.getColor(point.getX(), point.getY());
		if (color.equals(Color.WHITE)) {
			return getNextNegative(foundAreas);
		} else {
			return getNextPositive(foundAreas);
		}
	}

	private static int getNextNegative(Set<Integer> foundAreas) {
		for (int i = -1; i > Integer.MIN_VALUE; --i) {
			if (!foundAreas.contains(i)) {
				return i;
			}
		}
		throw new RuntimeException("DAFUQ, the image has more objects than numbers in integers");
	}

	private static int getNextPositive(Set<Integer> foundAreas) {
		for (int i = 1; i < Integer.MAX_VALUE; ++i) {
			if (!foundAreas.contains(i)) {
				return i;
			}
		}
		throw new RuntimeException("DAFUQ, the image has more objects than numbers in integers");
	}


	private static Optional<Point2DInt> getNextUninitializedPoint(OptionalInt[][] areas) {
		for (int y = 0; y < areas.length; ++y) {
			for (int x = 0; x < areas[0].length; ++x) {
				if (!areas[y][x].isPresent()) {
					return Optional.of(new Point2DInt(x, y));
				}
			}
		}
		return Optional.empty();
	}

	private static int getEulerNumber(Map<Integer, Integer> foundAreas) {
		Integer eulerNumber = foundAreas.keySet().stream().reduce(0, (runningTotal, curAreaNumber) -> {
			if (curAreaNumber == 0 || foundAreas.get(curAreaNumber) < MINIMUM_PIXELS_IN_AREA) {
				return runningTotal;
			}
			return runningTotal + (curAreaNumber > 0 ? 1 : -1);
		});

		return eulerNumber;
	}

	private static void fillArea(Map<Integer, Integer> foundAreas, Image img, OptionalInt[][] area, int startX,
								 int startY, int numberToSet) {
		if (area[startY][startX].isPresent()) {
			return;
		}
		PixelReader reader = img.getPixelReader();
		Color toCount = numberToSet > 0 ? Color.BLACK : Color.WHITE;
		if (!reader.getColor(startX, startY).equals(toCount)) {
			return;
		}

		area[startY][startX] = OptionalInt.of(numberToSet);
		Integer prevAmount = foundAreas.get(numberToSet);
		if (prevAmount == null) {
			prevAmount = 0;
		}
		foundAreas.put(numberToSet, prevAmount + 1);


		int left = startX - 1;
		if (isInImageBounds(img, left, startY)) {
			fillArea(foundAreas, img, area, left, startY, numberToSet);
		}
		int right = startX + 1;
		if (isInImageBounds(img, right, startY)) {
			fillArea(foundAreas, img, area, right, startY, numberToSet);
		}
		int top = startY - 1;
		if (isInImageBounds(img, startX, top)) {
			fillArea(foundAreas, img, area, startX, top, numberToSet);
		}
		int bottom = startY + 1;
		if (isInImageBounds(img, startX, bottom)) {
			fillArea(foundAreas, img, area, startX, bottom, numberToSet);
		}

		if (isInImageBounds(img, left, top)) {
			fillArea(foundAreas, img, area, left, top, numberToSet);
		}
		if (isInImageBounds(img, left, bottom)) {
			fillArea(foundAreas, img, area, left, bottom, numberToSet);
		}
		if (isInImageBounds(img, right, top)) {
			fillArea(foundAreas, img, area, right, top, numberToSet);
		}
		if (isInImageBounds(img, right, bottom)) {
			fillArea(foundAreas, img, area, right, bottom, numberToSet);
		}
	}

	private static boolean isInImageBounds(Image img, int x, int y) {
		return x >= 0 && y >= 0 && y < img.getHeight() && x < img.getWidth();
	}
}
