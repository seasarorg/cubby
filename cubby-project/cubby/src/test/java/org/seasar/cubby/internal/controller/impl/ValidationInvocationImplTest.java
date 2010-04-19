package org.seasar.cubby.internal.controller.impl;

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
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl.ValidationInvocationImpl;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRules;

public class ValidationInvocationImplTest {

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

	private final ValidationRules rules = new DefaultValidationRules() {
	};

	private final ActionResult actionResult = new Forward("foo");

	@Validation(rules = "rules", errorPage = "index.jsp")
	public ActionResult noErrorAction() {
		return actionResult;
	}

	@Validation(rules = "rules", errorPage = "index.jsp")
	public ActionResult errorAction() {
		throw new ValidationException();
	}

	public ValidationRules getRules() {
		return rules;
	}

	@Test
	public void invokeActionWithNoError() throws Exception {
		Map<String, Object[]> params = new LinkedHashMap<String, Object[]>();
		Method actionMethod = this.getClass().getMethod("noErrorAction");

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

		final ValidationInvocationImpl invocation = new ValidationInvocationImpl(
				request, response, actionContext);
		ActionResult actual = invocation.proceed();

		assertSame(actionResult, actual);

		verify(request, response, actionContext, actionErrors);
	}

	@Test
	public void invokeActionWithValidationException() throws Exception {
		Map<String, Object[]> params = new LinkedHashMap<String, Object[]>();
		Method actionMethod = this.getClass().getMethod("errorAction");

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

		final ValidationInvocationImpl invocation = new ValidationInvocationImpl(
				request, response, actionContext);
		ActionResult actual = invocation.proceed();

		assertTrue(actual instanceof Forward);
		Forward forward = (Forward) actual;
		assertEquals("index.jsp", forward.getPath("UTF-8"));

		verify(request, response, actionContext, actionErrors);
	}

}
