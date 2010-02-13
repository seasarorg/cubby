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

package org.seasar.cubby.internal.controller.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertSame;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ActionResultWrapper;

public class ActionResultWrapperTest {

	@Test
	public void execute() throws Exception {
		HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		ActionResult actionResult = createMock(ActionResult.class);
		ActionContext actionContext = createMock(ActionContext.class);
		actionResult.execute(actionContext, request, response);
		replay(request, response, actionContext, actionResult);

		ActionResultWrapper wrapper = new ActionResultWrapperImpl(actionResult,
				actionContext);
		assertSame(actionResult, wrapper.getActionResult());
		wrapper.execute(request, response);

		verify(request, response, actionContext, actionResult);
	}

}
