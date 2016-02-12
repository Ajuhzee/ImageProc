package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.base.image.process.filter.LinearFilterPlugin;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterType;

/**
 * Adds an image plugin, that provides a seperated 3x3 mean filter for image processing purposes.
 *
 * @author Ajuhzee
 */
public class Mean3x3Seperated extends LinearFilterPlugin {

	/**
	 * Mean3x3Threaded
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public Mean3x3Seperated(ImagePluginContext context) throws PluginLoadException {
		super("mean3x3Seperated", "Mittelwert 3x3 separiert", LinearFilterType.MEAN_3X3_SEPERATED.getFilterChain(), context);
	}

}
