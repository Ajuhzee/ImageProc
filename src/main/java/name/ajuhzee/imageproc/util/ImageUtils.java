package name.ajuhzee.imageproc.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

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
		PixelWriter resultWriter = result.getPixelWriter();
		fillWith(result, fillColor);

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

	public static void forEachPixel(Image img, PixelAction action) {
		int width = (int) img.getWidth();
		int height = (int) img.getHeight();
		forEachPixel(0, 0, width, height, action);
	}
}
