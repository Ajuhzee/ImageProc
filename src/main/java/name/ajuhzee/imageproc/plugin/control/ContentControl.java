package name.ajuhzee.imageproc.plugin.control;

import javafx.scene.Node;

/**
 * Provides a class to set the content of a container.
 * 
 * @author Ajuhzee
 *
 */
public interface ContentControl {

	/**
	 * Sets the content.
	 * 
	 * @param content
	 */
	public void setContent(Node content);

	/**
	 * Clears the content.
	 */
	public void clearContent();
}
