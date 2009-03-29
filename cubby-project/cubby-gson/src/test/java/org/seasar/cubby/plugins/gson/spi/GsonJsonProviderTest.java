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
import org.seasar.cubby.plugin.BinderPlugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonJsonProviderTest {

	private JsonProvider jsonProvider = new GsonJsonProvider();

	private abstract class MockContainerProvider implements ContainerProvider {
		public Container getContainer() {
			return new Container() {

				public <T> T lookup(Class<T> type) throws LookupException {
					if (Gson.class.equals(type)) {
						return type.cast(createGson());
					}
					throw new LookupException();
				}

			};
		}

		protected abstract Gson createGson();
	}

	@Test
	public void execute() throws Exception {
		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider() {

					@Override
					protected Gson createGson() {
						throw new LookupException();
					}
				});
		PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		pluginRegistry.register(binderPlugin);

		Foo bean = new Foo();
		bean.setName("\u30ab\u30d3\u30fc"); // unicode
		bean.setAge(30);
		bean.field = "field";
		Calendar calendar = Calendar.getInstance(Locale.JAPAN);
		calendar.clear();
		calendar.set(2009, Calendar.FEBRUARY, 2);
		bean.setDate(new Date(calendar.getTimeInMillis()));

		String json = jsonProvider.toJson(bean);
		System.out.println(json);
		Gson gson = new Gson();
		Foo result = gson.fromJson(json, Foo.class);

		assertEquals("\u30ab\u30d3\u30fc", result.getName());
		assertEquals(new Integer(30), result.getAge());
		assertEquals("field", result.field);
		assertEquals(calendar.getTimeInMillis(), result.getDate().getTime());

		pluginRegistry.clear();
	}

	@Test
	public void executeByCustomizedGson() throws Exception {
		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider() {

					@Override
					protected Gson createGson() {
						Gson gson = new GsonBuilder().setDateFormat(
								"yyyy-MM-dd").create();
						return gson;
					}
				});
		PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		pluginRegistry.register(binderPlugin);

		Foo bean = new Foo();
		bean.setName("\u30ab\u30d3\u30fc"); // unicode
		bean.setAge(30);
		bean.field = "field";
		Calendar calendar = Calendar.getInstance(Locale.JAPAN);
		calendar.clear();
		calendar.set(2009, Calendar.FEBRUARY, 2);
		bean.setDate(new Date(calendar.getTimeInMillis()));

		String json = jsonProvider.toJson(bean);
		System.out.println(json);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Foo result = gson.fromJson(json, Foo.class);

		assertEquals("\u30ab\u30d3\u30fc", result.getName());
		assertEquals(new Integer(30), result.getAge());
		assertEquals("field", result.field);
		assertEquals(calendar.getTimeInMillis(), result.getDate().getTime());

		pluginRegistry.clear();
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
