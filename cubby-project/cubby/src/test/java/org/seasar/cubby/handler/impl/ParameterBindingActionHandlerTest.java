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
import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.controller.impl.DefaultFormatPattern;
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
		final FormatPattern formatPattern = new DefaultFormatPattern();
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						if (FormatPattern.class.equals(type)) {
							return type.cast(formatPattern);
						}
						throw new LookupException(type.getName());
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
