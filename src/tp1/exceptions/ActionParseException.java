package tp1.exceptions;

public class ActionParseException extends GameParseException {

	private static final long serialVersionUID = 1L;

	public ActionParseException() {
		super();
	}

	public ActionParseException(String message) {
		super(message);
	}

	public ActionParseException(Throwable cause) {
		super(cause);
	}

	public ActionParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
