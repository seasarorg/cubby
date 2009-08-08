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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.action.MessageInfo;
import org.seasar.cubby.converter.ConversionException;

/**
 * 文字列を日付型のオブジェクトに変換するクラスの抽象クラスです。
 * 
 * @author baba
 */
public abstract class AbstractDateConverter extends AbstractConverter {

	/**
	 * 文字列を{@link Date}に変換して返します。
	 * 
	 * @param value
	 *            変換元のオブジェクトの文字列表現
	 * @param pattern
	 *            日付と時刻のフォーマットを記述するパターン
	 * @return 変換した結果の{@link Date}
	 * @throws ConversionException
	 *             {@link ParseException} が発生した場合
	 * @see SimpleDateFormat#parse(String, ParsePosition)
	 */
	protected Date toDate(final String value, final String pattern)
			throws ConversionException {
		if (value == null || value.length() == 0) {
			return null;
		}

		final ParsePosition parsePosition = new ParsePosition(0);
		final DateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false);
		final Date date = dateFormat.parse(value, parsePosition);
		if (date != null && parsePosition.getIndex() == value.length()) {
			return date;
		}

		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey("valid.dateFormat");
		throw new ConversionException(messageInfo);
	}

	/**
	 * 指定された日付を文字列に変換します。
	 * 
	 * @param date
	 *            日付
	 * @param pattern
	 *            日付と時刻のフォーマットを記述するパターン
	 * @return 指定された日付をフォーマットした文字列
	 */
	protected String toString(final Date date, final String pattern) {
		final DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

}
