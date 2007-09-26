package org.seasar.cubby.exception;

import org.seasar.framework.exception.SRuntimeException;

public class ActionRuntimeException extends SRuntimeException {

	private static final long serialVersionUID = 5185053339795669542L;

	public ActionRuntimeException(final String messageCode,
			final Object[] args, final Throwable cause) {
		super(messageCode, args, cause);
	}

	public ActionRuntimeException(final String messageCode, final Object[] args) {
		super(messageCode, args);
	}

	public ActionRuntimeException(final String messageCode) {
		super(messageCode);
	}

}
