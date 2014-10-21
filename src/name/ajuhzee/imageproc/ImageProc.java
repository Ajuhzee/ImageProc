package name.ajuhzee.imageproc;

import java.awt.image.BufferedImage;

public class ImageProc {

	private static BufferedImage image;

	public static void setImage(BufferedImage img) {
		image = img;
		ImageDisplayer.showImage(image);
	}
}
