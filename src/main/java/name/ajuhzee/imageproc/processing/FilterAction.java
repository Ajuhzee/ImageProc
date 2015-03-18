/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import java.util.concurrent.RecursiveTask;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import name.ajuhzee.imageproc.processing.filters.FilterChain;

/**
 * Filters the image with a given filter mask, while splitting up the image into multiple parts, in order to work wit
 * multiple threads.
 * 
 * @author Ajuhzee
 *
 */
public class FilterAction extends RecursiveTask<Image> {

	private static final int MAX_WORK_SIZE = 100_000;

	private static final long serialVersionUID = 1L;

	private final Image toFilter;

	private final WritableImage newImage;

	private final int startX;

	private final int endX;

	private final FilterChain filterChain;

	private FilterAction(Image toFilter, WritableImage newImage, FilterChain filterChain, int startX, int endX) {
		this.toFilter = toFilter;
		this.newImage = newImage;
		this.filterChain = filterChain;
		this.startX = startX;
		this.endX = endX;
	}

	/**
	 * Creates a new filter action to be executed in a ForkJoinPool.
	 * 
	 * @param toFilter
	 *            the image
	 * @param filterChain
	 *            the filters that will be applied
	 */
	public FilterAction(Image toFilter, FilterChain filterChain) {
		this.toFilter = toFilter;
		newImage = new WritableImage((int) toFilter.getWidth(), (int) toFilter.getHeight());
		this.filterChain = filterChain;
		startX = 0;
		endX = (int) (toFilter.getWidth());
	}

	private Image filter(Image toFilter) {

		int startX = 0;
		int endX = (int) toFilter.getWidth();
		return ImageEditing.filterPartial(toFilter, filterChain, startX, endX);
	}

	@Override
	protected Image compute() {
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

		FilterAction left = new FilterAction(toFilter, newImage, filterChain, startX, mid);
		FilterAction right = new FilterAction(toFilter, newImage, filterChain, mid, endX);
		invokeAll(left, right);
	}
}
