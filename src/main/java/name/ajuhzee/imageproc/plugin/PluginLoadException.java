package name.ajuhzee.imageproc.plugin;

/**
 * Exception that gets thrown when a plugin fails to load.
 * 
 * @author Ajuhzee
 *
 */
public class PluginLoadException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an exception.
	 */
	public PluginLoadException() {
	}

	/**
	 * Creates an exception.
	 * 
	 * @param message
	 */
	public PluginLoadException(String message) {
		super(message);
	}

	/**
	 * Creates an exception.
	 * 
	 * @param message
	 * @param cause
	 */
	public PluginLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates an exception.
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PluginLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Creates an exception.
	 * 
	 * @param cause
	 */
	public PluginLoadException(Throwable cause) {
		super(cause);
	}

}
