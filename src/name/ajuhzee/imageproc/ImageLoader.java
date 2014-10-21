package name.ajuhzee.imageproc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class ImageLoader {

	public static void loadFile() {
		FileChooser fc = new FileChooser();

		File file = fc.showOpenDialog(null);

		try {
			BufferedImage img = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(img, null);
		} catch (IOException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}