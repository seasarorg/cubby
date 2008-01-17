/*
 * Copyright 2006-2008 the Seasar Foundation and the Others.
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
package org.seasar.cubby.dxo.converter.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.seasar.framework.log.Logger;

public class InputStreamConverter extends AbstractFileItemConverter {

	private static final Logger logger = Logger
			.getLogger(InputStreamConverter.class);

	@SuppressWarnings("unchecked")
	public Class getDestClass() {
		return InputStream.class;
	}

	@Override
	protected Object convert(final FileItem fileItem) {
		try {
			return fileItem.getInputStream();
		} catch (final IOException e) {
			logger.log(e);
			return null;
		}
	}


}
