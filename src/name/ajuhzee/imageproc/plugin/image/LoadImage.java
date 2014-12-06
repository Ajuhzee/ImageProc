package name.ajuhzee.imageproc.plugin.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Adds an image plugin, which loads an image.
 * 
 * @author Ajuhzee
 *
 */
public class LoadImage extends ImagePlugin {

	private static final Logger logger = LogManager.getLogger();

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public LoadImage(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("file", "Datei", 0).subMenu("load", "Bild laden").get(), context);
	}

	private Void fileChosen(File file) {
		context().getSideMenuControl().clearContent();
		try {
			BufferedImage img = ImageIO.read(file);
			Image fxImage = SwingFXUtils.toFXImage(img, null);

			if (fxImage.isError()) throw fxImage.getException();

			Platform.runLater(() -> {
				context().getImageControl().showImage(fxImage);
			});
		} catch (final Exception ex) {
			logger.fatal("File loading failed", ex);
		}
		return null;
	}

	@Override
	public void started() {
		context().getGeneralControl().fileChooser(this::fileChosen);
	}
}