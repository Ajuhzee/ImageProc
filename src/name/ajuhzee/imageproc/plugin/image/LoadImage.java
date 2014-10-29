package name.ajuhzee.imageproc.plugin.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

public class LoadImage extends ImagePlugin {

	private static final Logger logger = LogManager.getLogger();

	public LoadImage(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.top("file", "Datei", 0).then("load", "Bild laden").get(), context);
	}

	private Void fileChosen(File file) {
		try {
			final BufferedImage img = ImageIO.read(file);
			final Image fxImage = SwingFXUtils.toFXImage(img, null);

			Platform.runLater(() -> {
				context().getImageControl().showImage(fxImage);
			});
		} catch (final IOException ex) {
			logger.fatal("File loading failed", ex);
		}

		return null;
	}

	@Override
	public void started() {
		context().getGeneralControl().fileChooser(this::fileChosen);
	}
}