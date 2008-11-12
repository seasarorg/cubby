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
import static org.easymock.EasyMock.replay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.mock.MockActionContext;

public class SendErrorTest {

	private HttpServletRequest request;

	private HttpServletResponse response;

	@Before
	public void setupMock() {
		request = createMock(HttpServletRequest.class);
		response = createMock(HttpServletResponse.class);
	}

	@Test
	public void sendError() throws Exception {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		replay(request, response);

		SendError sendError = new SendError(HttpServletResponse.SC_NOT_FOUND);
		ActionContext actionContext = new MockActionContext(null, null, null);
		sendError.execute(actionContext, request, response);
	}

	@Test
	public void sendErrorWithMessage() throws Exception {
		response.sendError(HttpServletResponse.SC_NOT_FOUND, "NOT FOUND");
		replay(request, response);

		SendError sendError = new SendError(HttpServletResponse.SC_NOT_FOUND,
				"NOT FOUND");
		ActionContext actionContext = new MockActionContext(null, null, null);
		sendError.execute(actionContext, request, response);
	}
}
