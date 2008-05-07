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
package org.seasar.cubby.action.impl;

import static java.util.Calendar.SEPTEMBER;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.extension.unit.S2TestCase;

public class FormatPatternImplTest extends S2TestCase {

	public FormatPattern formatPattern;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testDefaultPattern() {
		FormatPattern formatPattern = new FormatPatternImpl();
		assertEquals("yyyy-MM-dd", formatPattern.getDatePattern());
		assertEquals("HH:mm:ss", formatPattern.getTimePattern());
		assertEquals("yyyy-MM-dd HH:mm:ss", formatPattern.getTimestampPattern());
	}

	public void testDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, Calendar.SEPTEMBER, 2);
		Date date = new Date(calendar.getTimeInMillis());

		String pattern = formatPattern.getDatePattern();
		System.out.println(pattern);
		assertEquals("yyyy-MM-dd", pattern);

		DateFormat dateFormat = formatPattern.getDateFormat();
		String actual = dateFormat.format(date);
		System.out.println(actual);
		assertEquals("2007-09-02", actual);
	}

	public void testSqlDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2);
		java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());

		String pattern = formatPattern.getDatePattern();
		System.out.println(pattern);
		assertEquals("yyyy-MM-dd", pattern);

		DateFormat dateFormat = formatPattern.getDateFormat();
		String actual = dateFormat.format(date);
		System.out.println(actual);
		assertEquals("2007-09-02", actual);
	}

	public void testTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2, 8, 5, 6);
		Time time = new Time(calendar.getTimeInMillis());

		String pattern = formatPattern.getTimePattern();
		System.out.println(pattern);
		assertEquals("HH:mm:ss", pattern);

		DateFormat dateFormat = formatPattern.getTimeFormat();
		String actual = dateFormat.format(time);
		System.out.println(actual);
		assertEquals("08:05:06", actual);
	}

	public void testTime2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2, 18, 5, 6);
		Time time = new Time(calendar.getTimeInMillis());

		String pattern = formatPattern.getTimePattern();
		System.out.println(pattern);
		assertEquals("HH:mm:ss", pattern);

		DateFormat dateFormat = formatPattern.getTimeFormat();
		String actual = dateFormat.format(time);
		System.out.println(actual);
		assertEquals("18:05:06", actual);
	}

	public void testTimestamp() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2, 8, 5, 6);
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		String pattern = formatPattern.getTimestampPattern();
		System.out.println(pattern);
		assertEquals("yyyy-MM-dd HH:mm:ss", pattern);

		DateFormat dateFormat = formatPattern.getTimestampFormat();
		String actual = dateFormat.format(timestamp);
		System.out.println(actual);
		assertEquals("2007-09-02 08:05:06", actual);
	}

}
