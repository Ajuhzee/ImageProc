package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

/**
 * Adds an image plugin, that provides a threaded 3x3 mean filter for image
 * processing purposes.
 * 
 * @author Ajuhzee
 *
 */
public class Mean3x3Threaded extends ImagePlugin {

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public Mean3x3Threaded(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("filter", "Filter")
				.subMenu("mean3x3Threaded", "Mittelwert 3x3 threaded").get(), context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("Mean3x3Threaded", "Mean3x3Threaded");
	}

}
