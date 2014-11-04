package name.ajuhzee.imageproc;

@SuppressWarnings("javadoc")
public class InitializationException extends Exception {

	private static final long serialVersionUID = 1L;

	public InitializationException() {
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

}
