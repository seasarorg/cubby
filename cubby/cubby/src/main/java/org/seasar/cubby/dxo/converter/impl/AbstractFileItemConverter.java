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
package org.seasar.cubby.dxo.converter.impl;

import org.apache.commons.fileupload.FileItem;
import org.seasar.extension.dxo.converter.ConversionContext;
import org.seasar.extension.dxo.converter.impl.AbstractConverter;

/**
 * {@link FileItem}を他の型のオブジェクトに変換するクラスの抽象クラウスです。
 * 
 * @author baba
 */
public abstract class AbstractFileItemConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link FileItem}のクラスと{@link FileItem}配列のクラスを返します。
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	public Class[] getSourceClasses() {
		return new Class[] { FileItem.class, FileItem[].class };
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object convert(final Object source, final Class destClass,
			final ConversionContext context) {
		if (source == null) {
			return null;
		}
		if (source instanceof FileItem) {
			final FileItem fileItem = (FileItem) source;
			return convert(fileItem);
		}
		if (source instanceof FileItem[]) {
			final FileItem[] fileItems = (FileItem[]) source;
			return convert(fileItems[0]);
		}
		return null;
	}

	protected abstract Object convert(final FileItem fileItem);

}
