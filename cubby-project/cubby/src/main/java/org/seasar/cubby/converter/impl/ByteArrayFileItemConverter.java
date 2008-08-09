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

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.converter.Converter;

/**
 * {@link FileItem}から byte の配列へ変換する{@link Converter}です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ByteArrayFileItemConverter extends AbstractFileItemConverter {

	/**
	 * {@inheritDoc}
	 * 
	 * @return byteの配列のクラスを返します。
	 */
	public Class<?> getObjectType() {
		return byte[].class;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定された{@link FileItem}から取得したbyte配列を返します。
	 * </p>
	 * 
	 * @return 指定された{@link FileItem}から取得した byte 配列
	 * @see FileItem#get()
	 */
	@Override
	protected Object convert(final FileItem fileItem) {
		return fileItem.get();
	}

}
