package name.ajuhzee.imageproc.plugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadPluginExecutor implements PluginExecutor {

	private final ExecutorService pluginThread = Executors.newSingleThreadExecutor();

	@Override
	public void execute(PluginBase pluginBase) {
		pluginThread.execute(pluginBase::started);
	}

}
