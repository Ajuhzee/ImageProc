package name.ajuhzee.imageproc.plugin.image.process;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

public class Binarize extends ImagePlugin {

	public Binarize(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("process", "Bearbeiten", 100).then("binarize", "Binarisieren").get(), context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("Binarize", "Binarize");
	}

}
