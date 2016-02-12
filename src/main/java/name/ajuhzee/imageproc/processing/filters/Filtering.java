package name.ajuhzee.imageproc.processing.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.GlobalThreadPool;
import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterAction;
import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterChain;
import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterMask;
import name.ajuhzee.imageproc.processing.filters.morphological.MorphologicalFilter;

import java.util.ArrayList;
import java.util.List;


/**
 * Provides methods that can apply filters to images
 *
 * @author Ajuhzee
 */
public class Filtering {

	private static final int MORPHOLOGICAL_NEIGHBORHOOD_SIZE = 1;

	/**
	 * Filters a specific part of the image with given filter types.
	 *
	 * @param toFilter the source image
	 * @param linearFilterChain a chain of filters that are going to be applied
	 * @param startX the column to start the filtering
	 * @param endX the column to stop the filtering
	 * @return the filtered image
	 */
	public static Image filterLinearPartial(Image toFilter, LinearFilterChain linearFilterChain, int startX, int
			endX) {
		WritableImage newImage = new WritableImage((int) toFilter.getWidth(), (int) toFilter.getHeight());
		List<LinearFilterMask> masks = linearFilterChain.getFilterMasks();
		applyFilterMask(toFilter, newImage, masks.get(0), startX, endX);

		for (int i = 1; i != masks.size(); ++i) {
			applyFilterMask(newImage, newImage, masks.get(i), startX, endX);
		}
		return newImage;
	}

	/**
	 * Filters a given image with a given filter type by using one thread.
	 *
	 * @param toFilter the source image
	 * @param linearFilterChain a chain of filters that are going to be applied
	 * @return the filtered image
	 */
	public static Image filterLinear(Image toFilter, LinearFilterChain linearFilterChain) {
		int startX = 0;
		int endX = (int) toFilter.getWidth();
		return Filtering.filterLinearPartial(toFilter, linearFilterChain, startX, endX);
	}

	private static void applyFilterMask(Image image, WritableImage newImage, LinearFilterMask mask, int startX,
										int endX) {
		PixelWriter pixelWriter = newImage.getPixelWriter();
		int xRadius = mask.getKernelX() / 2;
		int yRadius = mask.getKernelY() / 2;

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = startX; x < endX; x++) {
				int newRedValue = 0;
				int newGreenValue = 0;
				int newBlueValue = 0;


				for (int maskX = -1 * xRadius; maskX <= xRadius; maskX++) {
					for (int maskY = -1 * yRadius; maskY <= yRadius; maskY++) {
						newRedValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getRed())
								* mask.getMultiplier(maskX, maskY);
						newGreenValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getGreen())
								* mask.getMultiplier(maskX, maskY);
						newBlueValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getBlue())
								* mask.getMultiplier(maskX, maskY);
					}
				}

				Color color = Color.rgb(Math.max(Math.min(newRedValue, 255), 0),
						Math.max(Math.min(newGreenValue, 255), 0), Math.max(Math.min(newBlueValue, 255), 0));

				pixelWriter.setColor(x, y, color);
			}

		}
	}

	private static Color getPaddedColor(Image toFilter, int x, int y) {
		if (isOutsideOfImage(toFilter, x, y)) {
			return Color.BLACK;
		}

		return toFilter.getPixelReader().getColor(x, y);
	}

	private static boolean isOutsideOfImage(Image toFilter, int x, int y) {
		return x < 0 || x >= toFilter.getWidth() || y < 0 || y >= toFilter.getHeight();
	}

	/**
	 * Filters a given image with a given filter type by using multiple threads.
	 *
	 * @param toFilter the source image
	 * @param linearFilterChain a chain of filters that are going to be applied
	 * @return the filtered image
	 */
	public static Image filterLinearThreaded(Image toFilter, LinearFilterChain linearFilterChain) {
		return GlobalThreadPool.FORK_JOIN_POOL.invoke(new LinearFilterAction(toFilter, linearFilterChain));
	}

	/**
	 * Applies the given morphological filter to the given image.
	 *
	 * @param toFilter
	 * @param filter
	 * @return the filtered image
	 */
	public static Image filterMorph(Image toFilter, MorphologicalFilter filter) {
		return applyMorphFilter(toFilter, filter, 0, (int) toFilter.getWidth(), 0, (int) toFilter.getHeight());
	}

	private static Image applyMorphFilter(Image toFilter, MorphologicalFilter filter, int startX, int endX, int startY,
										  int endY) {

		WritableImage newImage = new WritableImage((int) toFilter.getWidth(), (int) toFilter.getHeight());
		PixelWriter pixelWriter = newImage.getPixelWriter();

		for (int y = startY; y != endY; ++y) {
			for (int x = startX; x != endX; ++x) {
				int neighborhoodSizeY = MORPHOLOGICAL_NEIGHBORHOOD_SIZE;
				int neighborhoodSizeX = MORPHOLOGICAL_NEIGHBORHOOD_SIZE;

				List<Color> pixelList = getPixelList(toFilter, x, y, neighborhoodSizeY, neighborhoodSizeX);
				Color newColor = filter.apply(pixelList);

				pixelWriter.setColor(x, y, newColor);
			}
		}

		return newImage;
	}

	private static List<Color> getPixelList(Image toFilter, int curPixelX, int curPixelY, int neighborhoodSizeY,
											int neighborhoodSizeX) {
		PixelReader pixelReader = toFilter.getPixelReader();

		List<Color> colors = new ArrayList<>((neighborhoodSizeX + 1) * (neighborhoodSizeY + 1));

		int minX = curPixelX - neighborhoodSizeX;
		int maxX = curPixelX + neighborhoodSizeX + 1;
		int minY = curPixelY - neighborhoodSizeY;
		int maxY = curPixelY + neighborhoodSizeY + 1;

		for (int y = minY; y != maxY; ++y) {
			for (int x = minX; x != maxX; ++x) {
				if (!isOutsideOfImage(toFilter, x, y)) {
					colors.add(pixelReader.getColor(x, y));
				}
			}
		}

		return colors;
	}

}
