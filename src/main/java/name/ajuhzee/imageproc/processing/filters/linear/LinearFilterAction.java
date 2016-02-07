/**
 *
 */
package name.ajuhzee.imageproc.processing.filters.linear;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import name.ajuhzee.imageproc.processing.filters.Filtering;

import java.util.concurrent.RecursiveTask;

/**
 * Filters the image with a given filter mask, while splitting up the image into multiple parts, in order to work wit
 * multiple threads.
 *
 * @author Ajuhzee
 */
public class LinearFilterAction extends RecursiveTask<Image> {

	private static final int MAX_WORK_SIZE = 100_000;

	private static final long serialVersionUID = 1L;

	private final Image toFilter;

	private final WritableImage newImage;

	private final int startX;

	private final int endX;

	private final LinearFilterChain linearFilterChain;

	private LinearFilterAction(Image toFilter, WritableImage newImage, LinearFilterChain linearFilterChain, int startX,
							   int endX) {
		this.toFilter = toFilter;
		this.newImage = newImage;
		this.linearFilterChain = linearFilterChain;
		this.startX = startX;
		this.endX = endX;
	}

	/**
	 * Creates a new filter action to be executed in a ForkJoinPool.
	 *
	 * @param toFilter the image
	 * @param linearFilterChain the filters that will be applied
	 */
	public LinearFilterAction(Image toFilter, LinearFilterChain linearFilterChain) {
		this.toFilter = toFilter;
		newImage = new WritableImage((int) toFilter.getWidth(), (int) toFilter.getHeight());
		this.linearFilterChain = linearFilterChain;
		startX = 0;
		endX = (int) (toFilter.getWidth());
	}

	private Image filter(Image toBeFiltered) {

		int startIndex = 0;
		int endIndex = (int) toFilter.getWidth();
		return Filtering.filterLinearPartial(toBeFiltered, linearFilterChain, startIndex, endIndex);
	}

	@Override
	protected final Image compute() {
		int size = endX - startX;
		if (size < MAX_WORK_SIZE) {
			return filter(toFilter);
		}
		splitFilterExecution();
		return newImage;
	}

	private void splitFilterExecution() {
		int size = endX - startX;
		int mid = size / 2 + startX;

		LinearFilterAction left = new LinearFilterAction(toFilter, newImage, linearFilterChain, startX, mid);
		LinearFilterAction right = new LinearFilterAction(toFilter, newImage, linearFilterChain, mid, endX);
		invokeAll(left, right);
	}
}
