package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.base.image.process.filter.MorphologicalFilterPlugin;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.morphological.MinimumFilter;

/**
 * Adds an image plugin, that provides a 3x3 Minimum filter for image processing purposes.
 *
 * @author Ajuhzee
 */
public class Minimum3x3 extends MorphologicalFilterPlugin {

	/**
	 * Creates the Minimum3x3 filter plugin and positions a Menu-button for it.
	 *
 	 * @param context the plugin context
	 * @throws PluginLoadException
	 */
	public Minimum3x3(ImagePluginContext context) throws PluginLoadException {
		super("min3x3", "Minimum 3x3", new MinimumFilter(), context);
	}
}
