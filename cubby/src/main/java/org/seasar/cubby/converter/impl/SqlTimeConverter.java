/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.Date;

import org.seasar.cubby.converter.ConversionException;
import org.seasar.cubby.converter.ConversionHelper;

/**
 * {@link java.sql.Time}への変換を行うコンバータです。
 * <p>
 * 変換元のオブジェクトの文字列表現をフォーマットに従って{@link java.sql.Time}に変換した結果を変換先とします。
 * </p>
 * 
 * @author baba
 */
public class SqlTimeConverter extends AbstractDateConverter {

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getObjectType() {
		return java.sql.Time.class;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object convertToObject(final Object value,
			final Class<?> objectType, final ConversionHelper helper)
			throws ConversionException {
		if (value == null) {
			return null;
		}
		final String pattern = helper.getFormatPattern().getTimePattern();
		final Date date = toDate(value.toString(), pattern);
		return new java.sql.Time(date.getTime());
	}

	/**
	 * {@inheritDoc}
	 */
	public String convertToString(final Object value,
			final ConversionHelper helper) {
		if (value == null) {
			return null;
		}
		final String pattern = helper.getFormatPattern().getTimePattern();
		return toString((java.sql.Time) value, pattern);
	}

}
