package name.ajuhzee.imageproc.plugin.image;

import java.awt.image.RenderedImage;
import java.io.File;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Adds an image plugin, which saves an image.
 * 
 * @author Ajuhzee
 *
 */
public class SaveImage extends ImagePlugin {

	private static final Logger logger = LogManager.getLogger();
	private static final PluginInformation INFO = new PluginInformation("Bild speichern", true);

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public SaveImage(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("file", "Datei", 0).subMenu("save", INFO).get(), INFO, context);
		context().getGeneralControl().showPopup("TEST", "TEST");
	}

	private Void saveImage(File file) {
		context().getSideMenuControl().clearContent();
		try {
			Image fxImage = context().getImageControl().getImage();
			RenderedImage img = SwingFXUtils.fromFXImage(fxImage, null);
			ImageIO.write(img, "JPG", file);
			if (fxImage.isError()) throw fxImage.getException();
		} catch (final Exception ex) {
			logger.fatal("File saving failed", ex);
		}

		return null;
	}

	@Override
	public void started() {
		context().getGeneralControl().saveDialog(this::saveImage);
	}
}