package name.ajuhzee.imageproc.preferences;

import java.util.Optional;
import java.util.Set;

import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.ImagePlugin;

/**
 * Gives access to core and image plugins.
 * 
 * @author Ajuhzee
 *
 */
public interface SettingsManager {

	/**
	 * 
	 * @return access to all given core plugins
	 */
	public Set<Class<? extends CorePlugin>> getCorePlugins();

	/**
	 * 
	 * @return access to all given image plugins
	 */
	public Set<Class<? extends ImagePlugin>> getImagePlugins();

	public <T extends Object> Optional<T> getValue(String string);

}
