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

import org.apache.commons.fileupload.FileItem;

/**
 * {@link FileItem}から byte の配列へ変換する{@link org.seasar.extension.dxo.converter.Converter}です。
 * 
 * @author baba
 */
public class ByteArrayConverter extends AbstractFileItemConverter {

	/**
	 * {@inheritDoc}
	 * 
	 * @return byteの配列のクラスを返します。
	 */
	@SuppressWarnings("unchecked")
	public Class getDestClass() {
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
