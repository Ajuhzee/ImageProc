package name.ajuhzee.imageproc.plugin.base.image.process.filter;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.Filtering;
import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterChain;

/**
 * Base class for a linear filter plugin
 */
public class LinearFilterPlugin extends ImagePlugin {


	private final LinearFilterChain filterChain;

	/**
	 * Creates a linear filter plugin
	 *
	 * @param key the key for the plugin
	 * @param name the name of the plugin
	 * @param filterChain the filter chain to be used as a linear filter
	 * @param context the plugin context
	 * @throws PluginLoadException
	 */
	public LinearFilterPlugin(String key, String name, LinearFilterChain filterChain, ImagePluginContext context)
			throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("filter", "Filter")
				.subMenu(key, new PluginInformation(name, true)).get(), context);

		this.filterChain = filterChain;
	}

	private void applyFilter(Image oldImage) {
		final Image newImage = Filtering.filterLinear(oldImage, filterChain);
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		applyFilter(context().getImageControl().getImage());
	}
}
