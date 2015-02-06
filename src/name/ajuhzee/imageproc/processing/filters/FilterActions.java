package name.ajuhzee.imageproc.processing.filters;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.FilterAction;

public enum FilterActions {
	IDENTITY(Filters.IDENTITY, false),

	MEAN_3X3(Filters.MEAN_3X3, false),

	MEAN_3X3_SEPERATED(Filters.MEAN_3X3_SEPERATED, false),

	MEAN_3X3_THREADED(Filters.MEAN_3X3, true),

	LAPLACE_3X3(Filters.LAPLACE_3X3, false);

	private final FilterChain filter;
	private final boolean threaded;

	private FilterActions(FilterChain filter, boolean threaded) {
		this.filter = filter;
		this.threaded = threaded;
	}

	public FilterAction getFilterAction(Image image) {
		return new FilterAction(image, filter, threaded);
	}

}