package org.seasar.cubby.plugins;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.plugin.ActionInvocation;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRules;

public class ValidationPluginTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	@Before
	public void setupProvider() {
		final BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardownProvider() {
		pluginRegistry.clear();
	}

	public ValidationRules rules = new DefaultValidationRules() {
	};

	@Validation(rules = "rules", errorPage = "index.jsp")
	public ActionResult dummyActionMethod() {
		return null;
	}

	@Test
	public void invokeActionWithNoError() throws Exception {
		Map<String, Object[]> params = new LinkedHashMap<String, Object[]>();
		Method actionMethod = this.getClass().getMethod("dummyActionMethod");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andReturn(
				params);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionErrors actionErrors = createMock(ActionErrors.class);
		expect(actionErrors.isEmpty()).andStubReturn(true);
		ActionContext actionContext = createMock(ActionContext.class);
		expect(actionContext.getFormBean()).andStubReturn(null);
		expect(actionContext.getActionMethod()).andStubReturn(actionMethod);
		expect(actionContext.getAction()).andStubReturn(this);
		expect(actionContext.getActionErrors()).andStubReturn(actionErrors);
		replay(request, response, actionContext, actionErrors);

		final ValidationPlugin plugin = new ValidationPlugin();
		final ActionResult actionResult = new Forward("foo");
		final TestAction testAction = new TestAction() {

			public ActionResult invoke() {
				return actionResult;
			}
		};
		final ActionInvocation invocation = new MActionInvocation(request,
				response, actionContext, testAction);
		ActionResult actual = plugin.invokeAction(invocation);

		assertSame(actionResult, actual);

		verify(request, response, actionContext, actionErrors);
	}

	@Test
	public void invokeActionWithValidationException() throws Exception {
		Map<String, Object[]> params = new LinkedHashMap<String, Object[]>();
		Method actionMethod = this.getClass().getMethod("dummyActionMethod");

		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_PARAMS)).andReturn(
				params);
		request.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, Boolean.TRUE);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		ActionErrors actionErrors = createMock(ActionErrors.class);
		expect(actionErrors.isEmpty()).andStubReturn(true);
		ActionContext actionContext = createMock(ActionContext.class);
		expect(actionContext.getFormBean()).andStubReturn(null);
		expect(actionContext.getActionMethod()).andStubReturn(actionMethod);
		expect(actionContext.getAction()).andStubReturn(this);
		expect(actionContext.getActionErrors()).andStubReturn(actionErrors);
		replay(request, response, actionContext, actionErrors);

		final ValidationPlugin plugin = new ValidationPlugin();
		final TestAction testAction = new TestAction() {

			public ActionResult invoke() {
				throw new ValidationException();
			}
		};
		final ActionInvocation invocation = new MActionInvocation(request,
				response, actionContext, testAction);
		ActionResult actual = plugin.invokeAction(invocation);

		assertTrue(actual instanceof Forward);
		Forward forward = (Forward) actual;
		assertEquals("index.jsp", forward.getPath("UTF-8"));

		verify(request, response, actionContext, actionErrors);
	}

	class MActionInvocation implements ActionInvocation {

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** アクションのコンテキスト。 */
		private final ActionContext actionContext;

		//
		// /** プラグインのイテレータ。 */
		// private final Iterator<Plugin> pluginsIterator;

		private final TestAction testAction;

		/**
		 * インスタンス化します。
		 * 
		 * @param request
		 *            要求
		 * @param response
		 *            応答
		 * @param actionContext
		 *            アクションのコンテキスト
		 */
		public MActionInvocation(final HttpServletRequest request,
				final HttpServletResponse response,
				final ActionContext actionContext, final TestAction testAction) {
			this.request = request;
			this.response = response;
			this.actionContext = actionContext;
			this.testAction = testAction;
			// final PluginRegistry pluginRegistry =
			// PluginRegistry.getInstance();
			// this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public ActionResult proceed() throws Exception {
			return testAction.invoke();
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletRequest getRequest() {
			return request;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletResponse getResponse() {
			return response;
		}

		/**
		 * {@inheritDoc}
		 */
		public ActionContext getActionContext() {
			return actionContext;
		}

	}

	interface TestAction {
		ActionResult invoke();
	}
}
