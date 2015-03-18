package name.ajuhzee.imageproc.util;

import java.util.ArrayList;
import java.util.List;

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

	public void executeCallbacks() {
		for (Runnable function : functionsToRun) {
			function.run();
		}
	}
}
