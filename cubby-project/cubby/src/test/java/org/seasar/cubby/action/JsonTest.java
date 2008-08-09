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
package org.seasar.cubby.action;

import java.lang.reflect.Method;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.util.ClassUtil;

public class JsonTest extends S2TestCase {

	public void testExecute() throws Exception {
		final MockAction action = new MockAction();
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Json json = createBean();
		json.execute(action, MockAction.class, method, request, response);
		assertEquals("text/javascript; charset=utf-8", response
				.getContentType());
		// assertEquals("{age:30,name:\"カビー\"}", response.getResponseString());
		Hoge result = evaluate(response.getResponseString());
		assertEquals("カビー", result.getName());
		assertEquals(new Integer(30), result.getAge());
	}

	public void testExecuteWithContentTypeAndEncoding() throws Exception {
		final MockAction action = new MockAction();
		MockHttpServletRequest request = this.getRequest();
		MockHttpServletResponse response = this.getResponse();
		Method method = ClassUtil.getMethod(action.getClass(), "dummy1", null);

		Json json = createBean().contentType("text/javascript+json").encoding(
				"Shift_JIS");
		json.execute(action, MockAction.class, method, request, response);
		assertEquals("text/javascript+json", json.getContentType());
		assertEquals("Shift_JIS", json.getEncoding());
		assertEquals("text/javascript+json; charset=Shift_JIS", response
				.getContentType());
		// assertEquals("{age:30,name:\"カビー\"}", response.getResponseString());
		Hoge result = evaluate(response.getResponseString());
		assertEquals("カビー", result.getName());
		assertEquals(new Integer(30), result.getAge());
	}

	private Json createBean() {
		Hoge bean = new Hoge();
		bean.setName("カビー");
		bean.setAge(30);
		Json json = new Json(bean);
		return json;
	}

	private static Hoge evaluate(String responseString) {
		ContextFactory contextFactory = new ContextFactory();
		Context context = contextFactory.enterContext();
		Scriptable scope = context.initStandardObjects();
		Hoge result = new Hoge();
		scope.put("result", scope, result);
		String source = "var data = eval(" + responseString + ");"
				+ "result.name = data.name;" + "result.age = data.age";
		context.evaluateString(scope, source, null, 0, null);
		Context.exit();
		return result;
	}

	public static class Hoge {
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
