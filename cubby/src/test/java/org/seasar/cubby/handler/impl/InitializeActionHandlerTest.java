package org.seasar.cubby.handler.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class InitializeActionHandlerTest {

	@Test
	public void handle() throws Exception {
		ActionResult result = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		context.invokeInitializeMethod();
		expectLastCall();
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		expect(chain.chain(request, response, context)).andReturn(result);
		replay(request, response, context, chain);

		ActionHandler handler = new InitializeActionHandler();
		assertSame(result, handler.handle(request, response, context, chain));

		verify(request, response, context, chain);
	}

}
