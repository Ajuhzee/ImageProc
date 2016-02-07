package name.ajuhzee.imageproc.plugin.image.generate;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.generating.ImageGenerating;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.PluginInformation;

/**
 * Adds an image plugin, which generates a Testimage.
 *
 * @author Ajuhzee
 *
 */
public class TestImage extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Testbild", false);

	/**
	 * Positions a Menu-button for the plugin.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public TestImage(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("generate", "Generieren", 100).subMenu("testImage", INFO).get(), 				context);
	}

	private void testImage() {
		final Image newImage = ImageGenerating.generate();
		context().getSideMenuControl().clearContent();
		context().getImageControl().showImage(newImage);
	}

	@Override
	public void started() {
		testImage();
		context().getMenuControl().enablePlugins();
	}

}
