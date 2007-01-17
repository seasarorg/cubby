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
import org.seasar.cubby.controller.MockController;
import org.seasar.cubby.controller.MockMultipartRequestParser;
import org.seasar.cubby.controller.impl.ActionContextImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class ValidationFilterTest extends TestCase {
	MockMultipartRequestParser parser = new MockMultipartRequestParser();

	InitializeFilter f1 = new InitializeFilter(parser);

	MockServletContextImpl context = new MockServletContextImpl("/cubby");

	MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(
			context, "dummy");

	MockHttpServletResponseImpl response = new MockHttpServletResponseImpl(
			request);

	MockController controller = new MockController();

	ActionFilterChain chain = new ActionFilterChain();

	String[] uriConvertNames = new String[] {};

	ActionContext action;

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
		Method actionMethod = ClassUtils.getMethod(MockController.class,
				"dummy1", new Class[] {});
		assertNotNull(actionMethod);
		ActionMethod holder = new ActionMethod(actionMethod, chain,
				uriConvertNames);
		HashMap<String, Object> uriParams = new LinkedHashMap<String, Object>();
		uriParams.put("id", "1");
		uriParams.put("userId", "seasar");
		action = new ActionContextImpl(request, response, controller, holder);
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
		f1.doBeforeFilter(action);
		validator.processValidationResult = false;
		Forward result = (Forward) f2.doFilter(action, chain);
		assertTrue("入力検証エラーフラグ=true", (Boolean) request
				.getAttribute(ATTR_VALIDATION_FAIL));
		assertEquals("errorPageにフォワード", "error.jsp", result.getResult());
		assertTrue("入力検証が発生してもフォームオブジェクトへのバインドは可能な限り実行される", populator.populateProcessed);
	}

	public void testDoFilter_validationSuccess() throws Throwable {
		f1.doBeforeFilter(action);
		validator.processValidationResult = true;
		f3.setResult(new Forward("success.jsp"));
		Forward result = (Forward) f2.doFilter(action, chain);
		assertNull("入力検証エラーフラグ=null", request.getAttribute(ATTR_VALIDATION_FAIL));
		assertEquals("errorPageにフォワード", "success.jsp", result.getResult());
		assertTrue("フォームオブジェクトへのバインドは実行される", populator.populateProcessed);
	}

	public void testDoFilter_validationNotFound() throws Throwable {
		Method actionMethod = ClassUtils.getMethod(MockController.class,
				"dummy3", new Class[] {});
		assertNotNull(actionMethod);
		ActionMethod holder = new ActionMethod(actionMethod, chain,
				uriConvertNames);
		HashMap<String, Object> uriParams = new LinkedHashMap<String, Object>();
		uriParams.put("id", "1");
		uriParams.put("userId", "seasar");
		action = new ActionContextImpl(request, response, controller, holder);

		f1.doBeforeFilter(action);
		validator.processValidationResult = true;
		f3.setResult(new Forward("success.jsp"));
		try {
			f2.doFilter(action, chain);
			fail();
		} catch (RuntimeException e) {
			assertEquals(RuntimeException.class, e.getClass());
		}
	}
}
