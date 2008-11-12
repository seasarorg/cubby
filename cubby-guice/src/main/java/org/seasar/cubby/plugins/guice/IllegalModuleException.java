package org.seasar.cubby.plugins.guice;

public class IllegalModuleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalModuleException() {
		super();
	}

	public IllegalModuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalModuleException(String message) {
		super(message);
	}

	public IllegalModuleException(Throwable cause) {
		super(cause);
	}

}
