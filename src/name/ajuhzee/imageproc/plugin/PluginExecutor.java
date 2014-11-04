package name.ajuhzee.imageproc.plugin;

/**
 * Provides a class to execute a plugin.
 * 
 * @author Ajuhzee
 *
 */
public interface PluginExecutor {

	/**
	 * Executes the plugin.
	 * 
	 * @param pluginBase
	 */
	public void execute(PluginBase pluginBase);

}
