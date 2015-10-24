package name.ajuhzee.imageproc.util;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class ImageUtils {

	@FunctionalInterface
	public static interface PixelAction {

		public void forPixels(int x, int y);
	}

	private ImageUtils() {
	}

	public static Image loadImage(File file) throws Exception {
		BufferedImage img = ImageIO.read(file);
		Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();

		return fxImage;
	}

	public static Image loadImage(InputStream file) throws Exception {
		BufferedImage img = ImageIO.read(file);
		Image fxImage = SwingFXUtils.toFXImage(img, null);

		if (fxImage.isError()) throw fxImage.getException();

		return fxImage;
	}

	public static void saveImage(File file, Image fxImage) throws IOException {
		BufferedImage img = SwingFXUtils.fromFXImage(fxImage, null);

		File saveTo = addPngExtension(file);
		ImageIO.write(img, "PNG", saveTo);
	}

	public static void saveImage(Path path, Image fxImage) throws IOException {
		File file = path.toFile();
		saveImage(file, fxImage);
	}

	private static File addPngExtension(File file) {
		String filename = file.getName();
		if (FilenameUtils.getExtension(filename).equals("")) {
			filename += ".png";
		}

		return new File(file.getParent(), filename);
	}

	public static Image increaseCanvasSize(Image img, int desiredWidth, int desiredHeight, Color fillColor) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		if (width >= desiredWidth && height >= desiredHeight) {
			return img;
		}

		WritableImage result = new WritableImage(desiredWidth, desiredHeight);
		fillWith(result, fillColor);
		PixelWriter resultWriter = result.getPixelWriter();

		PixelReader srcReader = img.getPixelReader();
		int offsetX = desiredWidth - width;
		int offsetY = desiredHeight - height;

		resultWriter.setPixels(offsetX, offsetY, width, height, srcReader, 0, 0);

		return result;
	}

	private static void fillWith(WritableImage img, Color fillColor) {
		PixelWriter writer = img.getPixelWriter();
		forEachPixel(img, (x, y) -> {
			writer.setColor(x, y, fillColor);
		});
	}

	public static void forEachPixel(int startX, int endX, int startY, int endY, PixelAction action) {
		for (int x = startX; x < endX; ++x) {
			for (int y = startY; y < endY; ++y) {
				action.forPixels(x, y);
			}
		}
	}

	@FunctionalInterface
	public interface Array2DPointAction {

		void execute(int x, int y);
	}

	public static <T> void forEach2DArrayElement(T[][] array, Array2DPointAction action) {
		for (int y = 0; y < array.length; ++y) {
			for (int x = 0; x < array[0].length; ++x) {
				action.execute(x, y);
			}
		}
	}

	public static void forEachPixel(Image img, PixelAction action) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		forEachPixel(0, width, 0, height, action);
	}
}
