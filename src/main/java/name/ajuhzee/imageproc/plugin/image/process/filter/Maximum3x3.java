package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.base.image.process.filter.MorphologicalFilterPlugin;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.morphological.MaximumFilter;

/**
 * Maximum filter plugin
 */
public class Maximum3x3 extends MorphologicalFilterPlugin {

	/**
	 * Creates the plugin
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public Maximum3x3(ImagePluginContext context) throws PluginLoadException {
		super("max3x3", "Maximum 3x3", new MaximumFilter(), context);
	}
}
