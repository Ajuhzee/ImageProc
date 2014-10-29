package name.ajuhzee.imageproc.plugin.image.generate;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

public class TestImageFast extends ImagePlugin {

	public TestImageFast(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("generate", "Generieren", 100).then("testImageSlow", "Testbild (schnell)").get(),
				context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("TestImageFast", "TestImageFast");
	}

}
