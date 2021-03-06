package name.ajuhzee.imageproc.plugin.image.process.filter;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.filters.Filtering;
import name.ajuhzee.imageproc.processing.filters.linear.LinearFilterType;

/**
 * Adds an image plugin, that provides a 3x3 Laplace filter for image processing purposes.
 *
 * @author Ajuhzee
 */
public class Laplace extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Laplace 3x3", true);

	private Image oldImage;

	/**
	 * Creates the Laplace filter plugin and positions a Menu-button for it.
	 *
	 * @param context the plugin context
	 * @throws PluginLoadException
	 */
	public Laplace(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("filter", "Filter")
				.subMenu("laplace3x3", INFO).get(), context);
	}

	private void applyFilter() {
		final Image newImage = Filtering.filterLinear(oldImage, LinearFilterType.LAPLACE_3X3.getFilterChain());
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		oldImage = context().getImageControl().getImage();
		applyFilter();
	}

}
