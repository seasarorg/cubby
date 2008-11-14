package org.seasar.cubby.container;

public class LookupException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LookupException() {
		super();
	}

	public LookupException(String message, Throwable cause) {
		super(message, cause);
	}

	public LookupException(String message) {
		super(message);
	}

	public LookupException(Throwable cause) {
		super(cause);
	}

}
