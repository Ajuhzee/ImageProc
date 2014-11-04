package name.ajuhzee.imageproc.plugin.control;

/**
 * Provides a class to give access to those program recources, that are
 * necessary for the plugin to work with or modify the image.
 * 
 * @author Ajuhzee
 *
 */
public interface ImagePluginContext {

	/**
	 * 
	 * @return access to some general methods
	 */
	public GeneralControl getGeneralControl();

	/**
	 * 
	 * @return access to some methods to manipulate or process the image
	 */
	public ImageControl getImageControl();

	/**
	 * 
	 * @return access to control the side menu
	 */
	public ContentControl getSideMenuControl();

}
