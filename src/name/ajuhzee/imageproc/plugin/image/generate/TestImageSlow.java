package name.ajuhzee.imageproc.plugin.image.generate;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

/**
 * Adds an image plugin, which generates a Testimage(slow method).
 * 
 * @author Ajuhzee
 *
 */
public class TestImageSlow extends ImagePlugin {

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public TestImageSlow(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("generate", "Generieren", 100).subMenu("testImageFast", "Testbild (langsam)").get(),
				context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("TestImageSlow", "TestImageSlow");
	}

}
