/**
 *
 */
package name.ajuhzee.imageproc.plugin.control;

/**
 * Provides some control over the plugin menu.
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
