package name.ajuhzee.imageproc.plugin.control;

import java.util.function.Consumer;

import javafx.scene.image.Image;

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
	public void addImageChangedCallback(Consumer<Image> callback);

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
