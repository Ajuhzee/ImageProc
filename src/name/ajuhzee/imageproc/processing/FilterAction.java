/**
 * 
 */
package name.ajuhzee.imageproc.processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import name.ajuhzee.imageproc.processing.filters.FilterActions;
import name.ajuhzee.imageproc.processing.filters.FilterChain;
import name.ajuhzee.imageproc.processing.filters.FilterMask;

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

	private final FilterChain filterChain;

	boolean threaded;

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
	public FilterAction(Image toFilter, FilterChain filterChain, boolean threaded) {
		this.toFilter = toFilter;
		this.startIdx = 0;
		this.filterChain = filterChain;
		this.threaded = threaded;
	}

	private WritableImage filterWith(Image image, FilterMask mask) {
		WritableImage newImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());

		int xRadius = mask.getKernelX() / 2;
		int yRadius = mask.getKernelY() / 2;

		for (int x = startIdx; x < image.getWidth(); x++) {
			for (int y = startIdx; y < image.getHeight(); y++) {
				int newRedValue = 0;
				int newGreenValue = 0;
				int newBlueValue = 0;
				int filterMaskPosition = 0;

				for (int maskX = -1 * xRadius; maskX <= xRadius; maskX++) {
					for (int maskY = -1 * yRadius; maskY <= yRadius; maskY++) {
						newRedValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getRed())
								* mask.getMultiplier(maskX, maskY);
						newGreenValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getGreen())
								* mask.getMultiplier(maskX, maskY);
						newBlueValue += (int) (255d * getPaddedColor(image, x + maskX, y + maskY).getBlue())
								* mask.getMultiplier(maskX, maskY);
						filterMaskPosition++;
					}
				}

				Color color = Color.rgb(Math.max(Math.min(newRedValue, 255), 0),
						Math.max(Math.min(newGreenValue, 255), 0), Math.max(Math.min(newBlueValue, 255), 0));

				newImage.getPixelWriter().setColor(x, y, color);
			}

		}
		return newImage;
	}

	private Image filter(Image toFilter) {
		WritableImage filteredImage;

		List<FilterMask> masks = filterChain.getFilterMasks();

		filteredImage = filterWith(toFilter, masks.get(0));

		for (int i = 1; i != masks.size(); ++i) {
			filteredImage = filterWith(filteredImage, masks.get(i));
		}

		return filteredImage;
	}

	private Color getPaddedColor(Image toFilter, int x, int y) {
		if (isOutsideOfImage(toFilter, x, y)) return Color.BLACK;

		return toFilter.getPixelReader().getColor(x, y);
	}

	private boolean isOutsideOfImage(Image toFilter, int x, int y) {
		return x < 0 || x >= toFilter.getWidth() || y < 0 || y >= toFilter.getHeight();
	}

	public static void main(String[] args) throws IOException {
		FilterActions f = FilterActions.LAPLACE_3X3;

		File file = new File(System.getProperty("image"));
		BufferedImage img = ImageIO.read(file);
		Image fxImage = SwingFXUtils.toFXImage(img, null);
		Image out = ImageProcessing.filter(fxImage, f);
		BufferedImage outBuf = SwingFXUtils.fromFXImage(out, null);
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(outBuf)));
		frame.pack();
		frame.setVisible(true);
		frame.setTitle(f.toString());
	}

	@Override
	protected Image compute() {

		return filter(toFilter);
	}

}
