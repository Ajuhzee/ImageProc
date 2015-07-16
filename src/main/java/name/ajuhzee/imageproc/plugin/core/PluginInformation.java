/**
 *
 */
package name.ajuhzee.imageproc.plugin.core;

/**
 *
 *
 * @author Ajuhzee
 */
public class PluginInformation {

	private final String name;
	private final boolean requiresImage;

	/**
	 * @param name
	 *            the name of the plugin
	 * @param requiresImage
	 *            showes if this plugin requires an image to work
	 */
	public PluginInformation(String name, boolean requiresImage) {
		super();
		this.name = name;
		this.requiresImage = requiresImage;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the requiresImage
	 */
	public boolean doesRequireImage() {
		return requiresImage;
	}

}
