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

import javax.servlet.http.HttpServletResponse;

import junit.framework.TestCase;

import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContext;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class SendErrorTest extends TestCase {

	public void testSendError() throws Exception {
		MockServletContext context = new MockServletContextImpl("test");
		MockHttpServletRequest request = context.createRequest("foo");
		MockHttpServletResponse response = new MockHttpServletResponseImpl(
				request);

		SendError sendError = new SendError(HttpServletResponse.SC_NOT_FOUND);
		sendError.execute(null, null, null, request, response);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
	}

	public void testSendErrorWithMessage() throws Exception {
		MockServletContext context = new MockServletContextImpl("test");
		MockHttpServletRequest request = context.createRequest("foo");
		MockHttpServletResponse response = new MockHttpServletResponseImpl(
				request);

		SendError sendError = new SendError(HttpServletResponse.SC_NOT_FOUND,
				"NOT FOUND");
		sendError.execute(null, null, null, request, response);

		assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
		assertEquals("NOT FOUND", response.getMessage());
	}
}
