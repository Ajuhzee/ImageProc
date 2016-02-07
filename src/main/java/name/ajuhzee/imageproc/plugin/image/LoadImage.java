package name.ajuhzee.imageproc.plugin.image;

import javafx.application.Platform;
import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.preferences.SettingsManager;
import name.ajuhzee.imageproc.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Optional;

/**
 * Adds an image plugin, which loads an image.
 *
 * @author Ajuhzee
 */
public class LoadImage extends ImagePlugin {

	private static final Logger logger = LogManager.getLogger();

	private static final PluginInformation INFO = new PluginInformation("Bild laden", false);

	/**
	 * Positions a Menu-button for the plugin.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public LoadImage(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("file", "Datei", 0).subMenu("load", INFO).get(), context);
		SettingsManager settings = context.getSettings();
		Optional<String> imagePath = settings.getValue("imageproc.loadimage.startimage");

		if (imagePath.isPresent()) {
			fileChosen(new File(imagePath.get()));
		}
	}

	private void fileChosen(File file) {
		context().getSideMenuControl().clearContent();
		try {
			Image fxImage = ImageUtils.loadImage(file);
			Platform.runLater(() -> {
				context().getImageControl().showImage(fxImage);
				context().getMenuControl().enablePlugins();
			});
		} catch (Exception e) {
			logger.fatal("File loading failed", e);
		}
	}

	@Override
	public void started() {
		context().getGeneralControl().openImageDialog(this::fileChosen);
	}
}
