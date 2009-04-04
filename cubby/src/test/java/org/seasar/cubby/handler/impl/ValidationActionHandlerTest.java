/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import static org.easymock.EasyMock.anyBoolean;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Direct;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.plugin.BinderPlugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.NumberValidator;
import org.seasar.cubby.validator.validators.RangeValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class ValidationActionHandlerTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private MockAction action = new MockAction();

	private Map<String, Object[]> params = new HashMap<String, Object[]>();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private boolean validationFail = false;

	@Before
	public void setupProvider() {
		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						if (type.equals(MessagesBehaviour.class)) {
							return type.cast(new DefaultMessagesBehaviour());
						}
						throw new LookupException();
					}

				}));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardownProvider() {
		pluginRegistry.clear();
	}

	@Before
	public void setupMock() throws Exception {
		request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andStubReturn(
				params);
		expect(request.getLocale()).andStubReturn(null);
		request.setAttribute(eq(CubbyConstants.ATTR_VALIDATION_FAIL),
				anyBoolean());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				validationFail = (Boolean) getCurrentArguments()[1];
				return null;
			}

		});
		response = createMock(HttpServletResponse.class);
		replay(request, response);
	}

	@Test
	public void validate1() throws Exception {
		final List<ActionHandler> actionHandlers = Arrays
				.asList(new ActionHandler[] { new ValidationActionHandler() });
		final ActionHandlerChain actionHandlerChain = new ActionHandlerChainImpl(
				actionHandlers);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				ActionContext actionContext = new MockActionContext(action,
						MockAction.class, MockAction.class.getMethod("dummy"));
				ActionResult actionResult = actionHandlerChain.chain(request,
						response, actionContext);

				assertTrue(validationFail);

				ActionErrors errors = actionContext.getActionErrors();
				assertFalse(errors.isEmpty());
				assertEquals(1, errors.getFields().size());
				assertNotNull(errors.getFields().get("name"));

				assertTrue(actionResult instanceof Forward);
				Forward forward = (Forward) actionResult;
				assertEquals("error.jsp", forward.getPath("UTF-8"));

				return null;
			}

		});
	}

	@Test
	public void validate2() throws Exception {
		final List<ActionHandler> actionHandlers = Arrays
				.asList(new ActionHandler[] { new ValidationActionHandler() });
		final ActionHandlerChain actionHandlerChain = new ActionHandlerChainImpl(
				actionHandlers);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				params.put("name", new Object[] { "bob" });
				params.put("age", new Object[] { "bob" });

				ActionContext actionContext = new MockActionContext(action,

				MockAction.class, MockAction.class.getMethod("dummy"));
				ActionResult actionResult = actionHandlerChain.chain(request,
						response, actionContext);

				assertTrue(validationFail);

				ActionErrors errors = actionContext.getActionErrors();
				assertFalse(errors.isEmpty());
				assertEquals(1, errors.getFields().size());
				assertNotNull(errors.getFields().get("age"));

				assertTrue(Forward.class.isAssignableFrom(actionResult
						.getClass()));
				Forward forward = (Forward) actionResult;
				assertEquals("error.jsp", forward.getPath("UTF-8"));

				return null;
			}
		});
	}

	@Test
	public void validate3() throws Exception {
		final ActionResult succeedActionResult = new Direct();
		final List<ActionHandler> actionHandlers = Arrays
				.asList(new ActionHandler[] { new ValidationActionHandler(),
						new ActionHandler() {

							public ActionResult handle(
									HttpServletRequest request,
									HttpServletResponse response,
									ActionContext actionContext,
									ActionHandlerChain actionHandlerChain)
									throws Exception {
								return succeedActionResult;
							}

						} });
		final ActionHandlerChain actionHandlerChain = new ActionHandlerChainImpl(
				actionHandlers);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				params.put("name", new Object[] { "bob" });
				params.put("age", new Object[] { "5" });

				ActionContext actionContext = new MockActionContext(action,
						MockAction.class, MockAction.class.getMethod("dummy"));
				ActionResult actionResult = actionHandlerChain.chain(request,
						response, actionContext);

				assertFalse(validationFail);

				ActionErrors errors = actionContext.getActionErrors();
				assertTrue(errors.isEmpty());

				assertSame(succeedActionResult, actionResult);

				return null;
			}
		});
	}

	@Test
	public void handleValidationException() throws Exception {
		final List<ActionHandler> actionHandlers = Arrays
				.asList(new ActionHandler[] { new ValidationActionHandler(),
						new ActionHandler() {

							public ActionResult handle(
									HttpServletRequest request,
									HttpServletResponse response,
									ActionContext actionContext,
									ActionHandlerChain actionHandlerChain)
									throws Exception {
								throw new ValidationException("message",
										"field1");
							}

						} });

		final ActionHandlerChain actionHandlerChain = new ActionHandlerChainImpl(
				actionHandlers);
		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				ActionContext actionContext = new MockActionContext(action,
						MockAction.class, MockAction.class.getMethod("dummy"));
				ActionResult actionResult = actionHandlerChain.chain(request,
						response, actionContext);

				assertTrue(validationFail);

				ActionErrors errors = actionContext.getActionErrors();
				assertFalse(errors.isEmpty());
				assertEquals(1, errors.getFields().size());
				assertNotNull(errors.getFields().get("age"));

				assertTrue(actionResult instanceof Forward);
				Forward forward = (Forward) actionResult;
				assertEquals("error.jsp", forward.getPath("UTF-8"));
				assertTrue(validationFail);
				return null;
			}
		});
	}

	public static class MockAction extends Action {

		private ValidationRules validationRules = new DefaultValidationRules() {
			@Override
			public void initialize() {
				add("name", new RequiredValidator(), new MaxLengthValidator(10));
				add("age", new NumberValidator(), new RangeValidator(0, 10));
			}
		};

		public ValidationRules getValidationRules() {
			return validationRules;
		}

		@Path("/mock/dummy")
		@Validation(rules = "validationRules", errorPage = "error.jsp")
		public ActionResult dummy() {
			return null;
		}

	}

}
