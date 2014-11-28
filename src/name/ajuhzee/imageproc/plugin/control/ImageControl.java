package name.ajuhzee.imageproc.plugin.control;

import javafx.scene.image.Image;
import javafx.util.Callback;

/**
 * Provides some features to display the image.
 * 
 * @author Ajuhzee
 *
 */
public interface ImageControl {

	/**
	 * Calls the callback, when the image is changed.
	 * 
	 * @param callback
	 *            to be added
	 */
	public void addImageChangedCallback(Callback<Image, Void> callback);

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
