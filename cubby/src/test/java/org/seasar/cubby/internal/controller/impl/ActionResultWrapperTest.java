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
