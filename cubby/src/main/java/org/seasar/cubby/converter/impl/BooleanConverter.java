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
 * {@link Boolean}への変換を行うコンバータです。
 * <p>
 * 変換元オブジェクトの文字列表現が<code>yes</code>、<code>y</code>、<code>true</code>、<code>on</code>、<code>1</code>なら<code>true</code>、
 * そうでなければ<code>false</code>とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class BooleanConverter implements Converter {

	/** <code>true</code>に評価する文字列の配列です。 */
	private static final String[] TRUE_STRINGS = new String[] { "yes", "y",
			"true", "on", "1", };

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getConversionClass() {
		return Boolean.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value) {
		if (value == null) {
			return null;
		}
		return toBoolean(value.toString());
	}

	/**
	 * 文字列を{@link Boolean}に変換して返します。
	 * 
	 * @param value
	 *            変換元の文字列表現
	 * @return 変換した結果の{@link Boolean}
	 */
	protected Object toBoolean(final String value) {
		for (final String trueString : TRUE_STRINGS) {
			if (trueString.equalsIgnoreCase(value)) {
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

}
