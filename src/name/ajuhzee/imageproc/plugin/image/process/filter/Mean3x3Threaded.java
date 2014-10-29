package name.ajuhzee.imageproc.plugin.image.process.filter;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

public class Mean3x3Threaded extends ImagePlugin {

	public Mean3x3Threaded(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("process", "Bearbeiten", 100).then("filter", "Filter")
				.then("mean3x3Threaded", "Mittelwert 3x3 threaded").get(), context);
	}

	@Override
	public void started() {
		context().getGeneralControl().showPopup("Mean3x3Threaded", "Mean3x3Threaded");
	}

}
