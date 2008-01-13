/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.exception;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.seasar.framework.exception.SRuntimeException;

public class FileUploadRuntimeException extends SRuntimeException {

	private static final long serialVersionUID = -4519684364519402697L;

	public FileUploadRuntimeException(final FileUploadException cause) {
		super(messageCode(cause), args(cause), cause);
	}

	private static String messageCode(final FileUploadException cause) {
		final String messageCode;
		if (cause instanceof SizeLimitExceededException) {
			messageCode = "ECUB0202";
		} else {
			messageCode = "ECUB0201";
		}
		return messageCode;
	}

	private static Object[] args(final FileUploadException cause) {
		final Object[] args;
		if (cause instanceof SizeLimitExceededException) {
			final SizeLimitExceededException sle = (SizeLimitExceededException) cause;
			args = new Object[] { sle.getPermittedSize(), sle.getActualSize() };
		} else {
			args = new Object[] { cause };
		}
		return args;
	}
}
