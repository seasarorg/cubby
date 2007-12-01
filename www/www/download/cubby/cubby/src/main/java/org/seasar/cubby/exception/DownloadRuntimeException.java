package org.seasar.cubby.exception;

import org.seasar.framework.exception.SRuntimeException;

public class DownloadRuntimeException extends SRuntimeException {

	private static final long serialVersionUID = 8194033901086045201L;

	public DownloadRuntimeException(final String messageCode,
			final Object[] args, final Throwable cause) {
		super(messageCode, args, cause);
	}

	public DownloadRuntimeException(final String messageCode,
			final Object[] args) {
		super(messageCode, args);
	}

	public DownloadRuntimeException(final String messageCode) {
		super(messageCode);
	}

}
