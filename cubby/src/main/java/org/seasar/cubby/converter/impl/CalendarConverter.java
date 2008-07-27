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
import java.util.Calendar;
import java.util.Date;


/**
 * {@link Calendar}への変換を行うコンバータです。
 * <p>
 * 変換元オブジェクトの文字列表現をフォーマットに従って{@link Calendar}に変換した結果を変換先とします。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class CalendarConverter extends AbstractFormatPatternConverter {

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return Calendar.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value, final Class<?> objectType) {
		if (value == null) {
			return null;
		}
		final DateFormat dateFormat = getFormatPattern().getDateFormat();
		return toCalendar((String) value, dateFormat);
	}

	/**
	 * 文字列を{@link Calendar}に変換して返します。
	 * 
	 * @param date
	 *            変換元のオブジェクトの文字列表現
	 * @param dateFormat
	 *            フォーマット
	 * @return 変換した結果の{@link Calendar}
	 */
	protected Calendar toCalendar(final String date, final DateFormat dateFormat) {
		if (date == null || date.length() == 0) {
			return null;
		}
		try {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateFormat.parse(date));
			return calendar;
		} catch (final ParseException e) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value) {
		if (value == null) {
			return null;
		}
		final DateFormat formatter = getFormatPattern().getDateFormat();
		return formatter.format(new Date(((Calendar) value).getTimeInMillis()));
	}

}
