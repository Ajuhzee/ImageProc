package name.ajuhzee.imageproc.plugin;

public class PluginLoadException extends Exception {

	private static final long serialVersionUID = 1L;

	public PluginLoadException() {
	}

	public PluginLoadException(String message) {
		super(message);
	}

	public PluginLoadException(String message, Throwable cause) {
		super(message, cause);
	}

	public PluginLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PluginLoadException(Throwable cause) {
		super(cause);
	}

}
