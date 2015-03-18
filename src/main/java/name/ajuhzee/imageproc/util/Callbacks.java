package name.ajuhzee.imageproc.util;

public interface Callbacks {

	/**
	 * Supports callbacks of type Runnable
	 * 
	 * @param callback
	 */
	public void addCallback(Runnable callback);

	public void removeCallback(Runnable callback);
}
