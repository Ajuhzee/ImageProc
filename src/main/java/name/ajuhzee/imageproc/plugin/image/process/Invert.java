package name.ajuhzee.imageproc.plugin.image.process;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageEditing;

/**
 * Adds an image plugin, that provides a method to invert an image for image processing purposes.
 *
 * @author Ajuhzee
 *
 */
public class Invert extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Invertieren", true);

	private Image oldImage;

	/**
	 * Creates the Invert plugin and positions a Menu-button for it.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public Invert(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("invert", INFO).get(), context);
		// TODO Auto-generated constructor stub
	}

	private void invert() {
		final Image newImage = ImageEditing.invert(oldImage);
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		oldImage = context().getImageControl().getImage();
		invert();
	}

}
