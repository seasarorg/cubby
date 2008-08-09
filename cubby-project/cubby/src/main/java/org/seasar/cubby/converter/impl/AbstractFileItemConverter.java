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
import org.seasar.cubby.converter.ConversionHelper;

/**
 * {@link FileItem}を他の型のオブジェクトに変換するクラスの抽象クラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public abstract class AbstractFileItemConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canConvert(Class<?> parameterType, Class<?> objectType) {
		if (parameterType == null) {
			return false;
		}
		if (!FileItem.class.isAssignableFrom(parameterType)) {
			return false;
		}
		return super.canConvert(parameterType, objectType);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value, final Class<?> objectType, ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		final FileItem fileItem = (FileItem) value;
		return convert(fileItem);
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value, ConversionHelper helper) {
		return null;
	}

	/**
	 * {@link FileItem} を特定の型に変換します。
	 * 
	 * @param fileItem
	 *            変換元のインスタンス
	 * @return <code>fileItem</code>を変換したインスタンス
	 */
	protected abstract Object convert(final FileItem fileItem);

}
