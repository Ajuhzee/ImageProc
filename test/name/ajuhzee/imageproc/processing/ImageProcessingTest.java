package name.ajuhzee.imageproc.processing;

import java.awt.image.BufferedImage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.common.util.concurrent.AtomicDouble;

public class ImageProcessingTest {

	private static Image template;

	private Image img;

	@BeforeClass
	public static void loadTemplateImage() throws Exception {
		BufferedImage img = ImageIO.read(ImageProcessingTest.class.getClassLoader().getResourceAsStream("donny.jpg"));
		Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();
		template = fxImage;
	}

	@Before
	public void createImage() {
		img = new WritableImage(template.getPixelReader(), (int) template.getWidth(), (int) template.getHeight());
	}

	@Test
	public void Binarize_Performance() {
		AtomicDouble threshold = new AtomicDouble(127);
		for (int i = 0; i != 10; ++i) {
			ImageProcessing.concurrentBinarize(img, threshold);
		}
		long start = System.nanoTime();
		for (int i = 0; i != 10; ++i) {
			ImageProcessing.concurrentBinarize(img, threshold);
		}
		long end = System.nanoTime();
		double durInMs = (end - start) / (10000000d); // nano -> milli -> milliAvg

		System.out.println("Binarize took in avg " + durInMs + " ms.");
	}
}
