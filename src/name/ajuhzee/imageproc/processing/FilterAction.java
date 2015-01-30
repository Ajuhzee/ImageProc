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

	private final int kernelX;

	private final int kernelY;

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
	 * @param kernelX
	 *            kernel size of the filter mask in x direction
	 * @param kernelY
	 *            kernel size of the filter mask in y direction
	 */

	public FilterAction(Image toFilter, double[] filterMask1, double[] filterMask2, int kernelX, int kernelY,
			boolean threaded) {
		this.toFilter = toFilter;
		this.startIdx = 0;
		this.filterMask1 = filterMask1;
		this.filterMask2 = filterMask2;
		this.threaded = threaded;
		this.kernelX = kernelX;
		this.kernelY = kernelY;
	}

	private Image filter(Image toFilter) {
		WritableImage filtered = new WritableImage((int) toFilter.getWidth(), (int) toFilter.getHeight());
		for (int x = startIdx; x < toFilter.getWidth(); x++) {
			for (int y = startIdx; y < toFilter.getHeight(); y++) {
				// toFilter.getPixelReader().getColor(x, y).getBlue();
				int newRedValueSum = 0;
				int newGreenValueSum = 0;
				int newBlueValueSum = 0;
				for (int i = -1 * (kernelX / 2); i <= (kernelX / 2); i++) {
					for (int j = -1 * (kernelY / 2); j <= (kernelY / 2); j++) {
						newRedValueSum += (int) (255d * getPaddedColor(toFilter, x + i, y + j).getRed());
						newGreenValueSum += (int) (255d * getPaddedColor(toFilter, x + i, y + j).getGreen());
						newBlueValueSum += (int) (255d * getPaddedColor(toFilter, x + i, y + j).getBlue());
					}
				}

				int newRedValue = newRedValueSum / filterMask1.length;
				System.out.println(newRedValue);
				int newGreenValue = newGreenValueSum / filterMask1.length;
				int newBlueValue = newBlueValueSum / filterMask1.length;

				Color color = Color.rgb(newRedValue, newGreenValue, newBlueValue);

				filtered.getPixelWriter().setColor(x, y, color);
			}

		}
		return filtered;
	}

	/**
	 * @param toFilter
	 * @param x
	 * @param y
	 * @param i
	 * @param j
	 * @return
	 */
	private Color getPaddedColor(Image toFilter, int x, int y) {
		if (x < 0 || x >= toFilter.getWidth() || y < 0 || y >= toFilter.getHeight()) return Color.BLACK;

		return toFilter.getPixelReader().getColor(x, y);
	}

	public static void main(String[] args) throws IOException {
		File file = new File("C:/users/angi/desktop/10723650_1514147105507758_1714830986_n.jpg");
		BufferedImage img = ImageIO.read(file);
		Image fxImage = SwingFXUtils.toFXImage(img, null);
		Image out = ImageProcessing.filter(fxImage, "mean3x3");
		BufferedImage outBuf = SwingFXUtils.fromFXImage(out, null);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(outBuf)));
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	protected Image compute() {

		return filter(toFilter);
	}

}
