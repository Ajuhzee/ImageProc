package name.ajuhzee.imageproc.plugin.control;

import javafx.scene.image.Image;

/**
 * Provides some features to display the image.
 * 
 * @author Ajuhzee
 *
 */
public interface ImageControl {

	/**
	 * 
	 * @return the loaded image
	 */
	public Image getImage();

	/**
	 * 
	 * @param toSet
	 *            the image to be displayed
	 */
	public void showImage(Image toSet);
}
