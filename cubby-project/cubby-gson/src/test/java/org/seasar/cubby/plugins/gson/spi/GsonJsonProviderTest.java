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
package org.seasar.cubby.plugins.gson.spi;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.seasar.cubby.spi.JsonProvider;

import com.google.gson.Gson;

public class GsonJsonProviderTest {

	private JsonProvider jsonProvider = new GsonJsonProvider();

	@Test
	public void execute() throws Exception {
		Foo bean = new Foo();
		bean.setName("カビー");
		bean.setAge(30);
		bean.field = "field";
		Calendar calendar = Calendar.getInstance(Locale.JAPAN);
		calendar.setTimeInMillis(0L);
		calendar.set(2009, Calendar.FEBRUARY, 2);
		bean.setDate(new Date(calendar.getTimeInMillis()));

		String json = jsonProvider.toJson(bean);
		System.out.println(json);
		Gson gson = new Gson();
		Foo result = gson.fromJson(json, Foo.class);

		assertEquals("カビー", result.getName());
		assertEquals(new Integer(30), result.getAge());
		assertEquals("field", result.field);
		assertEquals(calendar.getTimeInMillis(), result.getDate().getTime());
	}

	public static class Foo {
		private String name;
		private Integer age;
		public String field;
		private Date date;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	}

}
