package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.base.image.process.filter.MorphologicalFilterPlugin;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.morphological.MedianFilter;

/**
 * Adds an image plugin, that provides a 3x3 Medium filter for image processing purposes.
 *
 * @author Ajuhzee
 */
public class Median3x3 extends MorphologicalFilterPlugin {

	/**
	 * Creates the Median3x3 filter plugin and positions a Menu-button for it.
	 *
	 * @param context the plugin context
	 * @throws PluginLoadException
	 */
	public Median3x3(ImagePluginContext context) throws PluginLoadException {
		super("median3x3", "Median 3x3", new MedianFilter(), context);
	}
}
