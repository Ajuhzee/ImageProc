package name.ajuhzee.imageproc.plugin.image.process;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

public class Invert extends ImagePlugin {

	public Invert(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("process", "Bearbeiten", 100).then("invert", "Invertieren").get(), context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("Invert", "Invert");
	}

}
