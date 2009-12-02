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
package org.seasar.cubby.internal.util;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;
import org.seasar.cubby.action.Path;

public class MetaUtilsTest {

	@Test
	public void getActionPath() throws Exception {
		assertEquals("/hoge/m1", MetaUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("m1")));
		assertEquals("/hoge/m/m2", MetaUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("m2")));
		assertEquals("/hoge/", MetaUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("index")));
		assertEquals("/hoge/index2", MetaUtils.getActionPath(Hoge1Action.class,
				Hoge1Action.class.getMethod("index2")));
		assertEquals("/hoge2/m1", MetaUtils.getActionPath(Hoge2Action.class,
				Hoge2Action.class.getMethod("m1")));
		assertEquals("/hoge/m2", MetaUtils.getActionPath(Hoge2Action.class,
				Hoge2Action.class.getMethod("m2")));
		assertEquals("/", MetaUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("index")));
		assertEquals("/dummy1", MetaUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("dummy1")));
		assertEquals("/dummy2", MetaUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("dummy2")));
		assertEquals("/todo/lists", MetaUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("todolist")));
		assertEquals("/tasklists", MetaUtils.getActionPath(MockAction.class,
				MockAction.class.getMethod("tasklist")));
	}

	@Test
	public void getActionClassName() {
		assertEquals("hoge", MetaUtils.getActionDirectory(Hoge1Action.class));
		assertEquals("hoge2", MetaUtils.getActionDirectory(Hoge2Action.class));
	}

	@Test
	public void gGetPriority() throws Exception {
		Method method = TestGetPriprity.class.getMethod("m1", new Class[0]);
		assertEquals(Integer.MAX_VALUE, MetaUtils.getPriority(method));

		method = TestGetPriprity.class.getMethod("m2", new Class[0]);
		assertEquals(Integer.MAX_VALUE, MetaUtils.getPriority(method));

		method = TestGetPriprity.class.getMethod("m3", new Class[0]);
		assertEquals(0, MetaUtils.getPriority(method));
	}

	static class TestGetPriprity {
		public void m1() {
		}

		@Path
		public void m2() {

		}

		@Path(value = "", priority = 0)
		public void m3() {

		}
	}

}
