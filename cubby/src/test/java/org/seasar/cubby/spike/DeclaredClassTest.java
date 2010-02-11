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

package org.seasar.cubby.spike;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;
import org.seasar.cubby.action.ActionResult;

public class DeclaredClassTest {

	@Test
	public void a() throws Exception {
		Object obj = new ConcreteOne();
		Method method1 = obj.getClass().getMethod("method1");
		Method method2 = obj.getClass().getMethod("method2");

		assertEquals(DeclaredClassTest.AbstractOne.class, method1
				.getDeclaringClass());
		assertEquals(DeclaredClassTest.ConcreteOne.class, method2
				.getDeclaringClass());
	}

	static abstract class AbstractOne {
		public ActionResult method1() {
			return null;
		}
	}

	static class ConcreteOne extends AbstractOne {
		public ActionResult method2() {
			return null;
		}
	}
}
