package name.ajuhzee.imageproc.plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executes a plugin with one single thread.
 * 
 * @author Ajuhzee
 *
 */
public class SingleThreadPluginExecutor implements PluginExecutor {

	private final ExecutorService pluginThread = Executors.newSingleThreadExecutor();

	@Override
	public void execute(PluginBase pluginBase) {
		pluginThread.execute(pluginBase::started);
	}

}
