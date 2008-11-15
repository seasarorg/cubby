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
package org.seasar.cubby.internal.converter.impl;

import org.seasar.cubby.internal.converter.ConversionHelper;

/**
 * 任意のオブジェクトから{@link Character}への変換を行うコンバータです。
 * <p>
 * 変換元のオブジェクトの文字列表現の先頭の文字を表す{@link Character}へ変換します。 そうでない場合は
 * <code>null</code> とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class CharacterConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return Character.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value, final Class<?> objectType, ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		return toCharacter(value.toString());
	}

	/**
	 * 文字列を{@link Character}に変換します。
	 * 
	 * @param value
	 *            文字列
	 * @return 文字列を変換した{@link Character}
	 */
	protected Object toCharacter(final String value) {
		if (value == null || value.length() == 0) {
			return 0;
		}
		return value.charAt(0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value, ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		return new String(new char[] { (Character) value });
	}

}
