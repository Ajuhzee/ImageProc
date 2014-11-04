package name.ajuhzee.imageproc.plugin.image.process;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

/**
 * Adds an image plugin, that provides a method to invert an image for image
 * processing purposes.
 * 
 * @author Ajuhzee
 *
 */
public class Binarize extends ImagePlugin {

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public Binarize(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("binarize", "Binarisieren").get(), context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("Binarize", "Binarize");
	}

}
