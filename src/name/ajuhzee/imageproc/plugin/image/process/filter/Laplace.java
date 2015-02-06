package name.ajuhzee.imageproc.plugin.image.process.filter;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageProcessing;
import name.ajuhzee.imageproc.processing.filters.FilterActions;

/**
 * Adds an image plugin, that provides a 3x3 laplace filter for image processing purposes.
 * 
 * @author Ajuhzee
 *
 */
public class Laplace extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Laplace 3x3", true);

	private Image oldImage;

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public Laplace(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("filter", "Filter")
				.subMenu("laplace3x3", INFO).get(), INFO, context);
	}

	private void applyFilter() {
		final Image newImage = ImageProcessing.filter(oldImage, FilterActions.LAPLACE_3X3);
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		oldImage = context().getImageControl().getImage();
		applyFilter();
	}

}
