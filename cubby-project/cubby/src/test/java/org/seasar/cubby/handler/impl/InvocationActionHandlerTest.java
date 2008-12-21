package org.seasar.cubby.handler.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;

public class InvocationActionHandlerTest {

	@Test
	public void handle() throws Exception {
		final ActionResult result = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

		Object action = new Object() {
			public ActionResult method1() {
				return result;
			}
		};
		Method actionMethod = action.getClass().getMethod("method1");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		expect(context.getAction()).andReturn(action);
		expect(context.getActionMethod()).andReturn(actionMethod);
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		replay(request, response, context, chain);

		ActionHandler handler = new InvocationActionHandler();
		assertSame(result, handler.handle(request, response, context, chain));

		verify(request, response, context, chain);
	}

	@Test
	public void handleWithErrorThrown() throws Exception {
		Object action = new Object() {
			public ActionResult method1() {
				throw new Error("expect Error thrown");
			}
		};
		Method actionMethod = action.getClass().getMethod("method1");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		expect(context.getAction()).andReturn(action);
		expect(context.getActionMethod()).andReturn(actionMethod);
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		replay(request, response, context, chain);

		ActionHandler handler = new InvocationActionHandler();
		try {
			handler.handle(request, response, context, chain);
			fail();
		} catch (Error e) {
			assertEquals("expect Error thrown", e.getMessage());
		}

		verify(request, response, context, chain);
	}

	@Test
	public void handleWithRuntimeExceptionThrown() throws Exception {
		Object action = new Object() {
			public ActionResult method1() {
				throw new RuntimeException("expect RuntimeException thrown");
			}
		};
		Method actionMethod = action.getClass().getMethod("method1");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		expect(context.getAction()).andReturn(action);
		expect(context.getActionMethod()).andReturn(actionMethod);
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		replay(request, response, context, chain);

		ActionHandler handler = new InvocationActionHandler();
		try {
			handler.handle(request, response, context, chain);
			fail();
		} catch (RuntimeException e) {
			assertEquals("expect RuntimeException thrown", e.getMessage());
		}

		verify(request, response, context, chain);
	}

	@Test
	public void handleWithExceptionThrown() throws Exception {
		Object action = new Object() {
			public ActionResult method1() throws Exception {
				throw new Exception("expect Exception thrown");
			}
		};
		Method actionMethod = action.getClass().getMethod("method1");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		expect(context.getAction()).andReturn(action);
		expect(context.getActionMethod()).andReturn(actionMethod);
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		replay(request, response, context, chain);

		ActionHandler handler = new InvocationActionHandler();
		try {
			handler.handle(request, response, context, chain);
			fail();
		} catch (Exception e) {
			assertEquals("expect Exception thrown", e.getMessage());
		}

		verify(request, response, context, chain);
	}

}
