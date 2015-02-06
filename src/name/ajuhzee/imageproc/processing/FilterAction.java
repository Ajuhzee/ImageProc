/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import java.util.List;
import java.util.concurrent.RecursiveTask;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.filters.FilterChain;
import name.ajuhzee.imageproc.processing.filters.FilterMask;

/**
 * Filters the image with a given filter mask.
 * 
 * @author Ajuhzee
 *
 */
public class FilterAction extends RecursiveTask<Image> {

	private static final long serialVersionUID = 1L;

	private final Image toFilter;

	private final int startIdx;

	private final FilterChain filterChain;

	boolean threaded;

	/**
	 * Creates a new filter action to be executed in a ForkJoinPool.
	 * 
	 * @param toFilter
	 *            the image
	 * @param filterChain
	 *            the filters that will be applied
	 * @param threaded
	 *            true if the filter is applied with multiple threads
	 */
	public FilterAction(Image toFilter, FilterChain filterChain, boolean threaded) {
		this.toFilter = toFilter;
		startIdx = 0;
		this.filterChain = filterChain;
		this.threaded = threaded;
	}

	private WritableImage filterWith(Image image, FilterMask mask) {
		WritableImage newImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

		int xRadius = mask.getKernelX() / 2;
		int yRadius = mask.getKernelY() / 2;

		for (int x = startIdx; x < image.getWidth(); x++) {
			for (int y = startIdx; y < image.getHeight(); y++) {
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

				newImage.getPixelWriter().setColor(x, y, color);
			}

		}
		return newImage;
	}

	private Image filter(Image toFilter) {
		WritableImage filteredImage;

		List<FilterMask> masks = filterChain.getFilterMasks();

		filteredImage = filterWith(toFilter, masks.get(0));

		for (int i = 1; i != masks.size(); ++i) {
			filteredImage = filterWith(filteredImage, masks.get(i));
		}

		return filteredImage;
	}

	private Color getPaddedColor(Image toFilter, int x, int y) {
		if (isOutsideOfImage(toFilter, x, y)) {
			return Color.BLACK;
		}

		return toFilter.getPixelReader().getColor(x, y);
	}

	private boolean isOutsideOfImage(Image toFilter, int x, int y) {
		return x < 0 || x >= toFilter.getWidth() || y < 0 || y >= toFilter.getHeight();
	}

	@Override
	protected Image compute() {

		return filter(toFilter);
	}

}
