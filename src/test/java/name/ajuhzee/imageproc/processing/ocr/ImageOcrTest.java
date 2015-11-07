package name.ajuhzee.imageproc.processing.ocr;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.ImageUtils;
import org.junit.Test;

import java.nio.file.Paths;

public class ImageOcrTest {

	@Test
	public void testAdjustImage() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("testAdjustImage.png"));
		Image adjusted = ImageOcr.adjustImage(img);
		ImageUtils.saveImage(Paths.get("C:/output.png"), adjusted);
	}
}
