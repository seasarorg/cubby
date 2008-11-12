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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockJsonProvider;

public class JsonTest {

	@Test
	public void execute() throws Exception {
		final MockAction action = new MockAction();

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		final StringWriter writer = new StringWriter();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/javascript; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		expect(response.getWriter()).andReturn(new PrintWriter(writer));

		replay(request, response);

		Method method = action.getClass().getMethod("dummy1");

		Json json = new Json(createBean());
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);
		json.execute(actionContext, request, response);
		assertEquals(MockJsonProvider.JSON_STRING, writer.toString());

		verify(request, response);
	}

	@Test
	public void executeWithContentTypeAndEncoding() throws Exception {
		final MockAction action = new MockAction();

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		final StringWriter writer = new StringWriter();
		response.setCharacterEncoding("Shift_JIS");
		response.setContentType("text/javascript+json; charset=Shift_JIS");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		expect(response.getWriter()).andReturn(new PrintWriter(writer));
		replay(request, response);

		Method method = action.getClass().getMethod("dummy1");

		Json json = new Json(createBean()).contentType("text/javascript+json").encoding(
				"Shift_JIS");
		ActionContext actionContext = new MockActionContext(action,
				MockAction.class, method);
		json.execute(actionContext, request, response);
		assertEquals(MockJsonProvider.JSON_STRING, writer.toString());

		verify(request, response);
	}

	private Foo createBean() {
		Foo bean = new Foo();
		bean.setName("カビー");
		bean.setAge(30);
		return bean;
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
