package name.ajuhzee.imageproc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class ImageLoader {

	private static final Logger logger = Logger.getLogger(ImageLoader.class.getName());

	public static void loadFile() {
		FileChooser fc = new FileChooser();

		File file = fc.showOpenDialog(null);
		try {
			BufferedImage img = ImageIO.read(file);
			ImageProc.setImage(img);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "File loading failed", ex);
		}

	}
}