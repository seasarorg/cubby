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
package org.seasar.cubby.tags;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.cubby.controller.ThreadContext;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class CubbyFunctionsTest extends TestCase {

	public void testContains() {
		List<String> action = new ArrayList<String>();
		action.add("unvalidate");
		action.add("validateRecord");
		action.add("branch");
		assertFalse(CubbyFunctions.contains(action, "validate"));
		action.add("validate");
		assertTrue(CubbyFunctions.contains(action, "validate"));
	}

	public void testUrl() throws Exception {
		MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(new MockServletContextImpl("/"), "/");
		request.setCharacterEncoding("UTF-8");
		ThreadContext.setRequest(request);
		assertEquals("abc%20%E3%81%82%E3%81%84%E3%81%86%22", CubbyFunctions.url("abc あいう\""));
		request.setCharacterEncoding("Windows-31J");
		ThreadContext.setRequest(request);
		assertEquals("abc%20%82%A0%82%A2%82%A4%22", CubbyFunctions.url("abc あいう\""));
	}

}
