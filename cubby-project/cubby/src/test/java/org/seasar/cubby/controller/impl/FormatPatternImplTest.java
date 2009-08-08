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
package org.seasar.cubby.controller.impl;

import static java.util.Calendar.SEPTEMBER;
import static org.junit.Assert.assertEquals;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.controller.impl.DefaultFormatPattern;

public class FormatPatternImplTest {

	private FormatPattern formatPattern;

	@Before
	public void setupFormatPattern() {
		DefaultFormatPattern formatPattern = new DefaultFormatPattern();
		formatPattern.setDatePattern("yyyy-MM-dd");
		formatPattern.setTimePattern("HH:mm:ss");
		formatPattern.setTimestampPattern("yyyy-MM-dd HH:mm:ss");
		this.formatPattern = formatPattern;
	}

	@Test
	public void defaultPattern() {
		FormatPattern formatPattern = new DefaultFormatPattern();
		assertEquals("yyyy-MM-dd", formatPattern.getDatePattern());
		assertEquals("HH:mm:ss", formatPattern.getTimePattern());
		assertEquals("yyyy-MM-dd HH:mm:ss", formatPattern.getTimestampPattern());
	}

	@Test
	public void date() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, Calendar.SEPTEMBER, 2);
		Date date = new Date(calendar.getTimeInMillis());

		String pattern = formatPattern.getDatePattern();
		System.out.println(pattern);
		assertEquals("yyyy-MM-dd", pattern);

		DateFormat dateFormat = new SimpleDateFormat(formatPattern
				.getDatePattern());
		String actual = dateFormat.format(date);
		System.out.println(actual);
		assertEquals("2007-09-02", actual);
	}

	@Test
	public void sqlDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2);
		java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());

		String pattern = formatPattern.getDatePattern();
		System.out.println(pattern);
		assertEquals("yyyy-MM-dd", pattern);

		DateFormat dateFormat = new SimpleDateFormat(formatPattern
				.getDatePattern());
		String actual = dateFormat.format(date);
		System.out.println(actual);
		assertEquals("2007-09-02", actual);
	}

	@Test
	public void time() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2, 8, 5, 6);
		Time time = new Time(calendar.getTimeInMillis());

		String pattern = formatPattern.getTimePattern();
		System.out.println(pattern);
		assertEquals("HH:mm:ss", pattern);

		DateFormat dateFormat = new SimpleDateFormat(formatPattern
				.getTimePattern());
		String actual = dateFormat.format(time);
		System.out.println(actual);
		assertEquals("08:05:06", actual);
	}

	@Test
	public void time2() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2, 18, 5, 6);
		Time time = new Time(calendar.getTimeInMillis());

		String pattern = formatPattern.getTimePattern();
		System.out.println(pattern);
		assertEquals("HH:mm:ss", pattern);

		DateFormat dateFormat = new SimpleDateFormat(formatPattern
				.getTimePattern());
		String actual = dateFormat.format(time);
		System.out.println(actual);
		assertEquals("18:05:06", actual);
	}

	@Test
	public void timestamp() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2007, SEPTEMBER, 2, 8, 5, 6);
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

		String pattern = formatPattern.getTimestampPattern();
		System.out.println(pattern);
		assertEquals("yyyy-MM-dd HH:mm:ss", pattern);

		DateFormat dateFormat = new SimpleDateFormat(formatPattern
				.getTimestampPattern());
		String actual = dateFormat.format(timestamp);
		System.out.println(actual);
		assertEquals("2007-09-02 08:05:06", actual);
	}

}
