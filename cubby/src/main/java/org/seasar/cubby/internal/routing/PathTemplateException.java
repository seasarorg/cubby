package org.seasar.cubby.internal.routing;

public class PathTemplateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PathTemplateException() {
		super();
	}

	public PathTemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	public PathTemplateException(String message) {
		super(message);
	}

	public PathTemplateException(Throwable cause) {
		super(cause);
	}

}
