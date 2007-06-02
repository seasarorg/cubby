package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;

import junit.framework.TestCase;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.controller.MockAction;
import org.seasar.cubby.controller.MockMultipartRequestParser;
import org.seasar.cubby.controller.impl.ActionContextImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.framework.exception.NoSuchFieldRuntimeException;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class ValidationFilterTest extends TestCase {
	MockMultipartRequestParser parser = new MockMultipartRequestParser();

	InitializeFilter f1 = new InitializeFilter(parser);

	MockServletContextImpl servletContext = new MockServletContextImpl("/cubby");

	MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(
			servletContext, "dummy");

	MockHttpServletResponseImpl response = new MockHttpServletResponseImpl(
			request);

	MockAction action = new MockAction();

	ActionFilterChain chain = new ActionFilterChain();

	String[] uriConvertNames = new String[] {};

	ActionContext context;

	private MockInvocationActionFilter f3;

	private MockActionValidator validator;

	private MockPopulator populator;

	private ValidationFilter f2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		request.setLocale(Locale.JAPANESE);
		request.addParameter("param1", "value1");
		request.addParameter("param2", "value2");
		Method actionMethod = ClassUtils.getMethod(MockAction.class,
				"dummy1", new Class[] {});
		assertNotNull(actionMethod);
		ActionMethod holder = new ActionMethod(actionMethod, chain,
				uriConvertNames);
		HashMap<String, Object> uriParams = new LinkedHashMap<String, Object>();
		uriParams.put("id", "1");
		uriParams.put("userId", "seasar");
		context = new ActionContextImpl(request, response, action, holder);
		validator = new MockActionValidator();
		populator = new MockPopulator();
		f2 = new ValidationFilter(validator, populator);
		f3 = new MockInvocationActionFilter();
		chain.add(f3);

	}

	public void testConstractor() throws Exception {
		assertNotNull(f1);
	}

	public void testDoFilter_validationFail() throws Throwable {
		f1.doBeforeFilter(context);
		validator.processValidationResult = false;
		Forward result = (Forward) f2.doFilter(context, chain);
		assertTrue("入力検証エラーフラグ=true", (Boolean) request
				.getAttribute(ATTR_VALIDATION_FAIL));
		assertEquals("errorPageにフォワード", "error.jsp", result.getResult());
		assertTrue("入力検証が発生してもフォームオブジェクトへのバインドは可能な限り実行される", populator.populateProcessed);
	}

	public void testDoFilter_validationSuccess() throws Throwable {
		f1.doBeforeFilter(context);
		validator.processValidationResult = true;
		f3.setResult(new Forward("success.jsp"));
		Forward result = (Forward) f2.doFilter(context, chain);
		assertNull("入力検証エラーフラグ=null", request.getAttribute(ATTR_VALIDATION_FAIL));
		assertEquals("errorPageにフォワード", "success.jsp", result.getResult());
		assertTrue("フォームオブジェクトへのバインドは実行される", populator.populateProcessed);
	}

	public void testDoFilter_validationNotFound() throws Throwable {
		Method actionMethod = ClassUtils.getMethod(MockAction.class,
				"dummy3", new Class[] {});
		assertNotNull(actionMethod);
		ActionMethod holder = new ActionMethod(actionMethod, chain,
				uriConvertNames);
		HashMap<String, Object> uriParams = new LinkedHashMap<String, Object>();
		uriParams.put("id", "1");
		uriParams.put("userId", "seasar");
		context = new ActionContextImpl(request, response, action, holder);

		f1.doBeforeFilter(context);
		validator.processValidationResult = true;
		f3.setResult(new Forward("success.jsp"));
		try {
			f2.doFilter(context, chain);
			fail();
		} catch (RuntimeException e) {
			assertEquals(NoSuchFieldRuntimeException.class, e.getClass());
		}
	}
}
