package name.ajuhzee.imageproc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides Control over the callbacks
 *
 * @author Ajuhzee
 */
public class CallbackManager implements Callbacks {

	private final List<Runnable> functionsToRun = new ArrayList<>();

	/**
	 * Supports callbacks of type Runnable
	 *
	 * @param callback
	 */
	@Override
	public void addCallback(Runnable callback) {
		functionsToRun.add(callback);
	}

	@Override
	public void removeCallback(Runnable callback) {
		functionsToRun.remove(callback);
	}

	/**
	 * Executes the callbacks.
	 */
	public void executeCallbacks() {
		for (Runnable function : functionsToRun) {
			function.run();
		}
	}
}
