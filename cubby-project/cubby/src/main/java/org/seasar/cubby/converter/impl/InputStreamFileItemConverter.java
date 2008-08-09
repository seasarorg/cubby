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
package org.seasar.cubby.converter.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.converter.Converter;
import org.seasar.framework.log.Logger;

/**
 * {@link FileItem}から{@link InputStream}へ変換する{@link Converter}です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class InputStreamFileItemConverter extends AbstractFileItemConverter {

	/** ロガー。 */
	private static final Logger logger = Logger
			.getLogger(InputStreamFileItemConverter.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @return {@link InputStream}のクラスを返します。
	 */
	public Class<?> getObjectType() {
		return InputStream.class;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定された{@link FileItem}から取得した{@link InputStream}を返します。変換時に{@link IOException}が発生した場合は
	 * <code>null</code> を返します。
	 * </p>
	 * 
	 * @return 指定された{@link FileItem}から取得した{@link InputStream}
	 * @see FileItem#getInputStream()
	 */
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
