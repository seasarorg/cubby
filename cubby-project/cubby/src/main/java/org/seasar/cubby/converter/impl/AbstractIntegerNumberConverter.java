/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import java.math.BigInteger;

import org.seasar.cubby.converter.ConversionException;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.validator.MessageInfo;

/**
 * 整数への変換を行うコンバータの抽象クラスです。
 * <p>
 * 変換元のオブジェクトの文字列表現を値とする{@link BigInteger}からサブクラスが変換した結果を変換先とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public abstract class AbstractIntegerNumberConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value,
			final Class<?> objectType, final ConversionHelper helper)
			throws ConversionException {
		if (value == null) {
			return null;
		}
		return convert(value.toString());
	}

	/**
	 * 数を表す文字列から数値に変換して返します。
	 * <p>
	 * 型変換に失敗した場合はメッセージのキーを <code>valid.number</code> とした
	 * {@link ConversionException} をスローします。
	 * </p>
	 * 
	 * @param value
	 *            数を表す文字列
	 * @return 変換結果の数値
	 * @throws ConversionException
	 *             型変換に失敗した場合
	 */
	protected Number convert(final String value) throws ConversionException {
		if (value == null || value.length() == 0) {
			return null;
		}

		final BigInteger integer;
		try {
			integer = new BigInteger(value);
		} catch (final NumberFormatException e) {
			try {
				new BigDecimal(value);
				final MessageInfo messageInfo = new MessageInfo();
				messageInfo.setKey("valid.integer");
				throw new ConversionException(messageInfo);
			} catch (final NumberFormatException e1) {
				final MessageInfo messageInfo = new MessageInfo();
				messageInfo.setKey("valid.number");
				throw new ConversionException(messageInfo);
			}
		}

		final BigInteger min = this.getMinValue();
		final BigInteger max = this.getMaxValue();
		if ((min != null && min.compareTo(integer) > 0)
				|| (max != null && max.compareTo(integer) < 0)) {
			final MessageInfo messageInfo = new MessageInfo();
			messageInfo.setKey("valid.range");
			messageInfo.setArguments(min, max);
			throw new ConversionException(messageInfo);
		}

		return convert(integer);
	}

	/**
	 * 数値を変換して返します。
	 * 
	 * @param integer
	 *            変換元の数値
	 * @return 変換結果の数値
	 */
	protected abstract Number convert(BigInteger integer);

	/**
	 * 最小値を取得します。
	 * <p>
	 * 最小値をチェックしない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @return 最小値
	 */
	protected abstract BigInteger getMinValue();

	/**
	 * 最大値を取得します。
	 * <p>
	 * 最大値をチェックしない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @return 最大値
	 */
	protected abstract BigInteger getMaxValue();

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value,
			final ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		return value.toString();
	}

}