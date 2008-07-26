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

import org.seasar.cubby.converter.Converter;

/**
 * {@link Character}の配列への変換を行うコンバータです。
 * <p>
 * 変換元オブジェクトの文字列表現が持つ文字配列を変換先とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class CharArrayConverter implements Converter {

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getConversionClass() {
		return char[].class;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value) {
		if (value == null) {
			return null;
		}
		return value.toString().toCharArray();
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value) {
		if (value == null) {
			return null;
		}
		return new String((char[]) value);
	}

}
