package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

public class Mean3x3 extends ImagePlugin {

	public Mean3x3(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("process", "Bearbeiten", 100).then("filter", "Filter")
				.then("mean3x3", "Mittelwert 3x3").get(), context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("Mean3x3", "Mean3x3");
	}

}
