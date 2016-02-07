package name.ajuhzee.imageproc.plugin.base.image.process.filter;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.Filtering;
import name.ajuhzee.imageproc.processing.filters.morphological.MorphologicalFilter;

import java.time.Duration;
import java.time.Instant;

/**
 * The base for morphological filter plugins
 */
public class MorphologicalFilterPlugin extends ImagePlugin {


	private final MorphologicalFilter filter;

	/**
	 * Creates a morphological filter plugin
	 *
	 * @param key the key for the plugin
	 * @param name the name of the plugin
	 * @param filter the filter to be used as a morphological filter
	 * @param context the plugin context
	 * @throws PluginLoadException
	 */
	public MorphologicalFilterPlugin(String key, String name, MorphologicalFilter filter, ImagePluginContext context)
			throws PluginLoadException {
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("filter", "Filter")
				.subMenu(key, new PluginInformation(name, true)).get(), context);

		this.filter = filter;
	}


	private void applyFilter(Image oldImage) {
		final Image newImage = Filtering.filterMorph(oldImage, filter);
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		applyFilter(context().getImageControl().getImage());
	}
}
