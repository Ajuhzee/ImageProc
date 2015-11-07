package name.ajuhzee.imageproc.processing;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.util.ImageUtils;
import org.junit.Test;

import java.nio.file.Paths;

public class ImageEditingTest {

	@Test
	public void Rotate_ShouldRotateBy90Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, 90, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}

	@Test
	public void Rotate_ShouldRotateByMinus45Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, -45, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}

	@Test
	public void Rotate_ShouldRotateByMinus80Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, -80, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}

	@Test
	public void Rotate_ShouldRotateByMinus120Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, -120, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}

	@Test
	public void Rotate_ShouldRotateBy110Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, 110, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}

	@Test
	public void Rotate_ShouldRotateBy30Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, 30, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}


	@Test
	public void Rotate_ShouldRotateBy180Degrees() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotateTest.png"));
		Image rotated = ImageEditing.rotate(img, 180, Color.WHITE);
		ImageUtils.saveImage(Paths.get("C:/output.png"), rotated);
	}
}
