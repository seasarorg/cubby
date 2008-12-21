package org.seasar.cubby.handler.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.TestUtils;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class ParameterBindingActionHandlerTest {

	@Before
	public void setup() {
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						throw new LookupException();
					}

				}));
	}

	@After
	public void teardownProvider() {
		ProviderFactory.clear();
	}

	@Test
	public void handle() throws Exception {
		ActionResult result = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

		Object formObject = new Object();
		Map<String, Object[]> params = new HashMap<String, Object[]>();
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andReturn(
				params);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		expect(context.getFormBean()).andReturn(formObject);
		expectLastCall();
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		expect(chain.chain(request, response, context)).andReturn(result);
		RequestParameterBinder binder = createMock(RequestParameterBinder.class);
		binder.bind(params, formObject, context);
		replay(request, response, context, chain, binder);

		ActionHandler handler = new ParameterBindingActionHandler();
		TestUtils.bind(handler, binder);

		handler.handle(request, response, context, chain);

		verify(request, response, context, chain, binder);
	}

	@Test
	public void handleWithNoBind() throws Exception {
		ActionResult result = new ActionResult() {

			public void execute(ActionContext actionContext,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			}

		};

		HttpServletRequest request = createMock(HttpServletRequest.class);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionContext context = createMock(ActionContext.class);
		expect(context.getFormBean()).andReturn(null);
		expectLastCall();
		ActionHandlerChain chain = createMock(ActionHandlerChain.class);
		expect(chain.chain(request, response, context)).andReturn(result);
		RequestParameterBinder binder = createMock(RequestParameterBinder.class);
		replay(request, response, context, chain, binder);

		ActionHandler handler = new ParameterBindingActionHandler();
		TestUtils.bind(handler, binder);

		handler.handle(request, response, context, chain);

		verify(request, response, context, chain, binder);
	}

}
