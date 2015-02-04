/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.RecursiveTask;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Filters the image with a given filter mask.
 * 
 * @author Ajuhzee
 *
 */
public class FilterAction extends RecursiveTask<Image> {

	private static final long serialVersionUID = 1L;

	private final Image toFilter;

	private final int startIdx;

	private final double[] filterMask1;

	private final double[] filterMask2;

	boolean threaded;

	private final int kernelX1;

	private final int kernelY1;

	private final int kernelX2;

	private final int kernelY2;

	/**
	 * Creates a new binarize action to be executed in a ForkJoinPool.
	 * 
	 * @param toFilter
	 *            the image
	 * @param threaded
	 *            true if the filter is applied with multiple threads
	 * @param filterMask1
	 *            the filtermask for the filter
	 * @param filterMask2
	 *            the optional 2nd filtermask (null if not needed)
	 * @param kernelX1
	 *            kernel size of the first filter mask in x direction
	 * @param kernelY1
	 *            kernel size of the first filter mask in y direction
	 * @param kernelX2
	 *            kernel size of the second filter mask in x direction (null if not needed)
	 * @param kernelY2
	 *            kernel size of the second filter mask in y direction (null if not needed)
	 */
	public FilterAction(Image toFilter, double[] filterMask1, double[] filterMask2, int kernelX1, int kernelY1,
			int kernelX2, int kernelY2, boolean threaded) {
		this.toFilter = toFilter;
		this.startIdx = 0;
		this.filterMask1 = filterMask1;
		this.filterMask2 = filterMask2;
		this.threaded = threaded;
		this.kernelX1 = kernelX1;
		this.kernelY1 = kernelY1;
		this.kernelX2 = kernelX2;
		this.kernelY2 = kernelY2;
	}

	private WritableImage filterWith(Image image, double[] filterMask, int kernelX, int kernelY) {
		WritableImage newImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		for (int x = startIdx; x < image.getWidth(); x++) {
			for (int y = startIdx; y < image.getHeight(); y++) {
				int newRedValue = 0;
				int newGreenValue = 0;
				int newBlueValue = 0;
				int filterMaskPosition = 0;
				for (int i = -1 * (kernelX / 2); i <= (kernelX / 2); i++) {
					for (int j = -1 * (kernelY / 2); j <= (kernelY / 2); j++) {
						newRedValue += (int) (255d * getPaddedColor(image, x + i, y + j).getRed())
								* filterMask[filterMaskPosition];
						newGreenValue += (int) (255d * getPaddedColor(image, x + i, y + j).getGreen())
								* filterMask[filterMaskPosition];
						newBlueValue += (int) (255d * getPaddedColor(image, x + i, y + j).getBlue())
								* filterMask[filterMaskPosition];
						filterMaskPosition++;
					}
				}

				Color color = Color.rgb(Math.floorMod(newRedValue, 256), Math.floorMod(newGreenValue, 256),
						Math.floorMod(newBlueValue, 256));

				newImage.getPixelWriter().setColor(x, y, color);
			}

		}
		return newImage;
	}

	private Image filter(Image toFilter) {
		WritableImage filteredImage = filterWith(toFilter, filterMask1, kernelX1, kernelY1);

		if (filterMask2 != null) {
			filteredImage = filterWith(filteredImage, filterMask2, kernelX2, kernelY2);
		}
		return filteredImage;
	}

	private Color getPaddedColor(Image toFilter, int x, int y) {
		if (x < 0 || x >= toFilter.getWidth() || y < 0 || y >= toFilter.getHeight()) return Color.BLACK;

		return toFilter.getPixelReader().getColor(x, y);
	}

	public static void main(String[] args) throws IOException {
		File file = new File("C:/users/ajuhzee/desktop/dormer.jpg");
		BufferedImage img = ImageIO.read(file);
		Image fxImage = SwingFXUtils.toFXImage(img, null);
		String filter = "mean3x3seperated";
		Image out = ImageProcessing.filter(fxImage, filter);
		BufferedImage outBuf = SwingFXUtils.fromFXImage(out, null);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(outBuf)));
		frame.pack();
		frame.setVisible(true);
		frame.setTitle(filter);
	}

	@Override
	protected Image compute() {

		return filter(toFilter);
	}

}
