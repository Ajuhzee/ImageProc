package name.ajuhzee.imageproc.processing;

import com.google.common.util.concurrent.AtomicDouble;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageProcessingTest {

	private static Image template;

	private Image img;

	@BeforeClass
	public static void loadTemplateImage() throws Exception {
		BufferedImage img = ImageIO.read(ImageProcessingTest.class
				.getClassLoader().getResourceAsStream("donny.jpg"));
		Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) {
			throw fxImage.getException();
		}
		template = fxImage;
	}

	@Before
	public void createImage() {
		img = new WritableImage(template.getPixelReader(),
				(int) template.getWidth(), (int) template.getHeight());
	}

	@Ignore("Only Performance")
	@Test
	public void Binarize_Performance() {
		AtomicDouble threshold = new AtomicDouble(127);
		for (int i = 0; i != 100; ++i) {
			ImageEditing.binarizeDynamic(img, threshold);
		}
		long start = System.nanoTime();
		for (int i = 0; i != 100; ++i) {
			ImageEditing.binarizeDynamic(img, threshold);
		}
		long end = System.nanoTime();
		// nano -> milli -> milliAvg
		double durInMs = (end - start) / (1_000_000d * 100d);

		System.out.println("Binarize took in avg " + durInMs + " ms.");
	}
}
