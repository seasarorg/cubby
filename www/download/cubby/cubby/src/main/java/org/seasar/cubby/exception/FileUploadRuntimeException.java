package org.seasar.cubby.exception;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.seasar.framework.exception.SRuntimeException;

public class FileUploadRuntimeException extends SRuntimeException {

	private static final long serialVersionUID = -4519684364519402697L;

	public FileUploadRuntimeException(FileUploadException cause) {
		super(messageCode(cause), args(cause), cause);
	}

	private static String messageCode(FileUploadException cause) {
		final String messageCode;
		if (cause instanceof SizeLimitExceededException) {
			messageCode = "ECUB0202";
		} else {
			messageCode = "ECUB0201";
		}
		return messageCode;
	}

	private static Object[] args(FileUploadException cause) {
		final Object[] args;
		if (cause instanceof SizeLimitExceededException) {
			SizeLimitExceededException sle = (SizeLimitExceededException) cause;
			args = new Object[] { sle.getPermittedSize(), sle.getActualSize() };
		} else {
			args = new Object[] { cause };
		}
		return args;
	}
}
