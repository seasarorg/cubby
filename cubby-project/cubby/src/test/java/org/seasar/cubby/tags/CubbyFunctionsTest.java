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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;

public class CubbyFunctionsTest {

	@Test
	public void contains() {
		List<String> action = new ArrayList<String>();
		action.add("unvalidate");
		action.add("validateRecord");
		action.add("branch");
		assertFalse(CubbyFunctions.contains(action, "validate"));
		action.add("validate");
		assertTrue(CubbyFunctions.contains(action, "validate"));
	}

	@Test
	public void url1() throws Exception {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("UTF-8");
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				assertEquals("abc+%E3%81%82%E3%81%84%E3%81%86", CubbyFunctions
						.url("abc あいう"));
				return null;
			}

		});

		verify(request, response);
	}

	@Test
	public void url2() throws Exception {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getCharacterEncoding()).andStubReturn("Windows-31J");
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				assertEquals("abc+%82%A0%82%A2%82%A4", CubbyFunctions
						.url("abc あいう"));
				return null;
			}

		});

		verify(request, response);
	}

}
