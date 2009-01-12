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
package org.seasar.cubby.plugins.s2.spi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;

public class S2JsonProviderTest {

	private S2JsonProvider jsonProvider = new S2JsonProvider();

	@Test
	public void execute() throws Exception {
		Foo bean = new Foo();
		bean.setName("カビー");
		bean.setAge(30);

		String json = jsonProvider.toJson(bean);

		Foo result = evaluate(json);

		assertEquals("カビー", result.getName());
		assertEquals(new Integer(30), result.getAge());
	}

	private static Foo evaluate(String responseString) {
		ContextFactory contextFactory = new ContextFactory();
		Context context = contextFactory.enterContext();
		Scriptable scope = context.initStandardObjects();
		Foo result = new Foo();
		scope.put("result", scope, result);
		String source = "var data = eval(" + responseString + ");"
				+ "result.name = data.name;" + "result.age = data.age";
		context.evaluateString(scope, source, null, 0, null);
		Context.exit();
		return result;
	}

	public static class Foo {
		private String name;
		private Integer age;

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
	}

}
