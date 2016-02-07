package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.base.image.process.filter.MorphologicalFilterPlugin;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.morphological.MinimumFilter;

/**
 * Minimum filter plugin
 */
public class Minimum3x3 extends MorphologicalFilterPlugin {

	public Minimum3x3(ImagePluginContext context) throws PluginLoadException {
		super("min3x3", "Minimum 3x3", new MinimumFilter(), context);
	}
}
