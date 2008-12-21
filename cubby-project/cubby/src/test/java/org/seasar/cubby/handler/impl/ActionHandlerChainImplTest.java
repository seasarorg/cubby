package org.seasar.cubby.handler.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class ActionHandlerChainImplTest {

	private boolean handle1 = false;

	private boolean handle2 = false;

	private boolean handle3 = false;

	@Test
	public void chain() throws Exception {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		replay(request, response, context);

		ActionHandler[] handlers = new ActionHandler[] { new Handler1(),
				new Handler2(), new Handler3() };
		ActionHandlerChain chain = new ActionHandlerChainImpl(Arrays
				.asList(handlers));

		chain.chain(request, response, context);

		assertTrue(handle1);
		assertTrue(handle2);
		assertTrue(handle3);

		verify(request, response, context);
	}

	private class Handler1 implements ActionHandler {

		public ActionResult handle(HttpServletRequest request,
				HttpServletResponse response, ActionContext actionContext,
				ActionHandlerChain actionHandlerChain) throws Exception {
			assertFalse(handle1);
			assertFalse(handle2);
			assertFalse(handle3);
			handle1 = true;
			return actionHandlerChain.chain(request, response, actionContext);
		}

	}

	private class Handler2 implements ActionHandler {

		public ActionResult handle(HttpServletRequest request,
				HttpServletResponse response, ActionContext actionContext,
				ActionHandlerChain actionHandlerChain) throws Exception {
			assertTrue(handle1);
			assertFalse(handle2);
			assertFalse(handle3);
			handle2 = true;
			return actionHandlerChain.chain(request, response, actionContext);
		}

	}

	private class Handler3 implements ActionHandler {

		public ActionResult handle(HttpServletRequest request,
				HttpServletResponse response, ActionContext actionContext,
				ActionHandlerChain actionHandlerChain) throws Exception {
			assertTrue(handle1);
			assertTrue(handle2);
			assertFalse(handle3);
			handle3 = true;
			return null;
		}

	}

}
