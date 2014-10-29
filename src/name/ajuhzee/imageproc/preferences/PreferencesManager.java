package name.ajuhzee.imageproc.preferences;

import java.util.Set;

import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.ImagePlugin;

public interface PreferencesManager {

	public Set<Class<? extends CorePlugin>> getCorePlugins();

	public Set<Class<? extends ImagePlugin>> getImagePlugins();

}
