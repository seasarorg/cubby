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
package org.seasar.cubby.spike;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.seasar.cubby.action.ActionResult;
import org.seasar.framework.util.ClassUtil;

public class DeclaredClassTest extends TestCase {

	public void testA() {
		Object obj = new ConcreteOne();
		Method method1 = ClassUtil.getMethod(obj.getClass(), "method1", new Class[0]);
		Method method2 = ClassUtil.getMethod(obj.getClass(), "method2", new Class[0]);

		System.out.println(method1.getDeclaringClass());
		System.out.println(method2.getDeclaringClass());
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
