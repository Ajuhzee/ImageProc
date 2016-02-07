package name.ajuhzee.imageproc.plugin.image.process.filter;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageEditing;
import name.ajuhzee.imageproc.processing.filters.FilterType;

/**
 * Adds an image plugin, that provides a 3x3 mean filter for image processing purposes.
 * 
 * @author Ajuhzee
 *
 */
public class Mean3x3 extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Mittelwert 3x3", true);

	private Image oldImage;

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public Mean3x3(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("filter", "Filter")
				.subMenu("mean3x3", INFO).get(), INFO, context);
	}

	private void applyFilter() {
		final Image newImage = ImageEditing.filter(oldImage, FilterType.MEAN_3X3.getFilterChain());
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		oldImage = context().getImageControl().getImage();
		applyFilter();
	}

}
