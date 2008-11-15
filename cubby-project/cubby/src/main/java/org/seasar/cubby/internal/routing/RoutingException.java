package org.seasar.cubby.internal.routing;

public class RoutingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoutingException() {
		super();
	}

	public RoutingException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoutingException(String message) {
		super(message);
	}

	public RoutingException(Throwable cause) {
		super(cause);
	}

}
