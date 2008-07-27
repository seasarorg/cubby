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

import java.math.BigDecimal;

/**
 * {@link Number 数}への変換を行うコンバータの抽象クラスです。
 * <p>
 * 変換元のオブジェクトの文字列表現を値とする{@link BigDecimal}からサブクラスが変換した結果を変換先とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public abstract class AbstractNumberConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value, final Class<?> objectType) {
		if (value == null) {
			return null;
		}
		return convert(value.toString());
	}

	/**
	 * 数値を変換して返します。
	 * 
	 * @param number
	 *            変換元の数値
	 * @return 変換結果の数値
	 */
	protected abstract Number convert(Number number);

	/**
	 * 数を表す文字列から数値に変換して返します。
	 * 
	 * @param number
	 *            数を表す文字列
	 * @return 変換結果の数値
	 */
	protected Number convert(final String number) {
		if (number == null || number.length() == 0) {
			return null;
		}
		final BigDecimal decimal = new BigDecimal(number);
		return convert(decimal);
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
