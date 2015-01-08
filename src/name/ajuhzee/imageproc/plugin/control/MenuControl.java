/**
 * 
 */
package name.ajuhzee.imageproc.plugin.control;

/**
 * @author Ajuhzee
 *
 */
public interface MenuControl {

	/**
	 * Enables menu items that require an image, when needed.
	 */
	public void enablePlugins();

	/**
	 * Disables menu items that require an image, when needed.
	 */
	public void disablePlugins();

}
