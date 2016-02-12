package name.ajuhzee.imageproc.util;

public interface Callbacks {

	/**
	 * Supports callbacks of type Runnable
	 *
	 * @param callback
	 */
	public void addCallback(Runnable callback);

	/**
	 * Supports callbacks of type Runnable
	 *
	 * @param callback
	 */
	public void removeCallback(Runnable callback);
}
