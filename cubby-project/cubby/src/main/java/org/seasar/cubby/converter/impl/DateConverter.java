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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.seasar.cubby.converter.ConversionHelper;

/**
 * 任意のオブジェクトから{@link Date}への変換を行うコンバータです。
 * <p>
 * 変換元のオブジェクトの文字列表現をフォーマットに従って{@link Date}に変換した結果を変換先とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class DateConverter extends AbstractConverter {

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return Date.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value,
			final Class<?> objectType, final ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		final DateFormat dateFormat = helper.getFormatPattern().getDateFormat();
		return toDate(value.toString(), dateFormat);
	}

	/**
	 * 文字列を{@link Date}に変換して返します。
	 * 
	 * @param date
	 *            変換元のオブジェクトの文字列表現
	 * @param dateFormat
	 *            フォーマット
	 * @return 変換した結果の{@link Date}
	 */
	protected Date toDate(final String date, final DateFormat dateFormat) {
		if (date == null || date.length() == 0) {
			return null;
		}
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value,
			final ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		final DateFormat formatter = helper.getFormatPattern().getDateFormat();
		return formatter.format((Date) value);
	}

}
