package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_ALL_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTROLLER;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FIELD_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.controller.MockAction;
import org.seasar.cubby.controller.MockMultipartRequestParser;
import org.seasar.cubby.controller.impl.ActionContextImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class InitializeFilterTest extends TestCase {
	MockMultipartRequestParser parser = new MockMultipartRequestParser();
	InitializeFilter f = new InitializeFilter(parser);
	MockServletContextImpl servletContext = new MockServletContextImpl("/cubby");
	MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(servletContext, "dummy");
	MockHttpServletResponseImpl response = new MockHttpServletResponseImpl(request);
	MockAction action = new MockAction();
	ActionFilterChain chain = new ActionFilterChain();
	String[] uriConvertNames = new String[]{};
	ActionContext context;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		request.setLocale(Locale.JAPANESE);
		request.addParameter("param1", "value1");
		request.addParameter("param2", "value2");
		Method actionMethod = ClassUtils.getMethod(MockAction.class, "dummy1", new Class[]{});
		assertNotNull(actionMethod);
		ActionMethod holder = new ActionMethod(actionMethod,  chain, uriConvertNames);
		context = new ActionContextImpl(request, response, action, holder);
	}
	
	public void testConstractor() throws Exception {
		assertNotNull(f);
	}
	
	public void testDoBeforeFilter() throws Exception {
		assertFalse("実行前", action.executedInitalizeMethod);
		f.doBeforeFilter(context);
		assertTrue("controller.initalizeメソッドが実行される", action.executedInitalizeMethod);
	}
	
	public void testDoAfterFilter_forward() throws Exception {
		assertFalse("実行前", action.executedPrerenderMethod);
		f.doBeforeFilter(context);
		f.doAfterFilter(context, new Forward("sample.jsp"));
		assertTrue("結果がForwardの場合、prerenderメソッドが実行される", 
				action.executedPrerenderMethod);
	}

	public void testDoAfterFilter_notForward() throws Exception {
		assertFalse("実行前", action.executedPrerenderMethod);
		f.doBeforeFilter(context);
		f.doAfterFilter(context, new Redirect("/list"));
		assertFalse("結果がForward以外の場合、prerenderメソッドは実行されない", 
				action.executedPrerenderMethod);
	}
	
	public void testSetupErrors() throws Exception {
		assertNull(action.getErrors());
		f.setupErrors(context);
		assertTrue(action.getErrors().isEmpty());
	}

	public void testSetupLocale() throws Exception {
		// locale=ja
		request.setLocale(Locale.JAPANESE);
		f.setupLocale(context);
		assertEquals("requestのロケールがLocaleHolder（ThreadLocal）にセットされる", 
				Locale.JAPANESE, LocaleHolder.getLocale());

		// locale=en
		request.setLocale(Locale.ENGLISH);
		f.setupLocale(context);
		assertEquals("requestのロケールがLocaleHolder（ThreadLocal）にセットされる",
				Locale.ENGLISH, LocaleHolder.getLocale());
	}
	
	public void testSetupImplicitVariable() throws Exception {
		assertNull("実行前はnull", request.getAttribute("contextPath"));
		assertNull("実行前はnull", request.getAttribute("messages"));

		LocaleHolder.setLocale(Locale.JAPANESE);
		f.setupImplicitVariable(context);
		assertEquals("request.getContextPathと同値", 
				"/cubby", request.getAttribute("contextPath"));
		Map messageMap = (Map) request.getAttribute("messages");
		assertNotNull("messages.propertiesをMap化したオブジェクト（JSPのELからアクセスするため）", 
				messageMap);
		assertEquals("messages.propertiesの値を取得", 
				"{0}は必須です。", messageMap.get("valid.required"));
	}
	
	@SuppressWarnings("unchecked")
	public void testSetupParams() throws Exception {
		assertNull("実行前はnull", request.getAttribute(ATTR_PARAMS));

		f.setupParams(context);
		Map<String, Object> params =  (Map<String, Object>) request.getAttribute(ATTR_PARAMS);
		assertNotNull("リクエストに格納されている", params);
		assertEquals("value1", ((String[])params.get("param1"))[0]);
		assertEquals("value2", ((String[])params.get("param2"))[0]);
	}
	
	public void testSetupRequest() throws Exception {
		assertNull("実行前はnull", action.attr1);
		assertNull("実行前はnull", action.attr2);
		assertNull("実行前はnull", action.attr3);

		request.setAttribute("attr1", "v1");
		request.setAttribute("attr2", "v2");
		request.setAttribute("attr3", "v3");
		f.setupRequestScopeFields(context);
		assertEquals("request.attributeの値はControllerのpublicフィールドにバインド", 
				"v1", action.attr1);
		assertEquals("request.attributeの値はControllerのpublicフィールドにバインド", 
				"v2", action.attr2);
		assertNull("@Session(セッションスコープの指定)はバインド対象外", action.attr3);
	}

	public void testSetupSession() throws Exception {
		assertNull("実行前はnull", action.attr1);
		assertNull("実行前はnull", action.attr2);
		assertNull("実行前はnull", action.attr3);

		request.getSession().setAttribute("attr1", "v1");
		request.getSession().setAttribute("attr2", "v2");
		request.getSession().setAttribute("attr3", "v3");
		f.setupSessionScopeFields(context);
		assertNull("@Session(セッションスコープの指定)が付いていないものはバインド対象外", 
				action.attr2);
		assertEquals("@Session(セッションスコープの指定)が付いていたら、session.attributeの値をバインド", 
				"v3", action.attr3);
	}

	public void testSetupFlashSessionNew() throws Exception {
		assertNull("実行前はnull", action.getFlash());
		assertNull("実行前はnull", request.getSession().getAttribute(ATTR_FLASH));

		f.setupFlash(context);
		assertNotNull("session.attributeをMap化したものがControllerにセット済み", 
				action.getFlash());
	}

	public void testSetupFlashSessionRestore() throws Exception {
		assertNull("実行前はnull", action.getFlash());
		assertNull("実行前はnull", request.getSession().getAttribute(ATTR_FLASH));
		
		HashMap<String, Object> flash = new HashMap<String, Object>();
		flash.put("f1", "v1");
		request.getSession().setAttribute(ATTR_FLASH, flash);
		f.setupFlash(context);
		assertEquals("既にsession中にflashオブジェクトが存在した場合、そのflashオブジェクトを引き継ぐ", 
				flash, action.getFlash());
		assertNotNull(request.getSession().getAttribute(ATTR_FLASH));
	}

	public void testBindAttributes() throws Exception {
		action.attr1 = "v1";
		action.attr2 = "v2";
		action.attr3 = "v3";
		f.setupErrors(context);

		assertNull("実行前はnull", request.getAttribute(ATTR_CONTROLLER));
		assertNull("実行前はnull", request.getAttribute(ATTR_ERRORS));
		assertNull("実行前はnull", request.getAttribute(ATTR_ALL_ERRORS));
		assertNull("実行前はnull", request.getAttribute(ATTR_ACTION_ERRORS));
		assertNull("実行前はnull", request.getAttribute(ATTR_FIELD_ERRORS));
		assertNull("実行前はnull", request.getAttribute("attr1"));
		assertNull("実行前はnull", request.getAttribute("attr2"));
		assertNull("実行前はnull", request.getSession().getAttribute("attr3"));
		
		f.bindAttributes(context);

		// set controller
		assertEquals("controller自身がrequest.attributeにバインド", 
				action, request.getAttribute(ATTR_CONTROLLER));

		// set attribute
		assertEquals("controllerのpublicフィールドの値をrequest.attributeにバインド", 
				"v1", request.getAttribute("attr1"));
		assertEquals("controllerのpublicフィールドの値をrequest.attributeにバインド", 
				"v2", request.getAttribute("attr2"));
		assertEquals("controllerのpublicフィールド(@Session)の値をsession.attributeにバインド", 
				"v3", request.getSession().getAttribute("attr3"));

		assertNull("controllerのpublicフィールド(non @Session)の値はsession.attributeにはバインドされない", 
				request.getSession().getAttribute("attr1"));
		assertNull("controllerのpublicフィールド(non @Session)の値はsession.attributeにはバインドされない", 
				request.getSession().getAttribute("attr2"));
		assertNull("controllerのpublicフィールド(@Session)の値はrequest.attributeにはバインドされない", 
				request.getAttribute("attr3"));
		
		// set actionErrors
		assertEquals("'errors'にcontroller.getErrors()をバインド", 
				action.getErrors(), request.getAttribute(ATTR_ERRORS));
		assertEquals("'allErrros'にcontroller.getErrors().getAllErrors()をバインド", 
				action.getErrors().getAllErrors(), request.getAttribute(ATTR_ALL_ERRORS));
		assertEquals("'actionErrros'にcontroller.getErrors().getActionErrors()をバインド", 
				action.getErrors().getActionErrors(), request.getAttribute(ATTR_ACTION_ERRORS));
		assertEquals("'filedErrors'にcontroller.getErrors().getFiledErrors()をバインド", 
				action.getErrors().getFieldErrors(), request.getAttribute(ATTR_FIELD_ERRORS));

	}
	
	public void testIsSessionScape() throws Exception {
		assertFalse(InitializeFilter.isSessionScope(MockAction.class.getField("attr1")));
		assertFalse(InitializeFilter.isSessionScope(MockAction.class.getField("attr2")));
		assertTrue(InitializeFilter.isSessionScope(MockAction.class.getField("attr3")));
	}
	
	public void testGetMultipartSupportParameterMap() throws Exception {
		parser.isMultipart = false;
		assertEquals("Multipartでない場合、request.getParameterMap()が返る", 
				request.getParameterMap(), 
				f.getMultipartSupportParameterMap(request));

		parser.isMultipart = true;
		parser.getMultipartParameterMapResult = new HashMap<String, Object>();
		assertEquals("Multipartの場合、MultipartRequestParser.getMultipartParameterMapの結果が返る", 
				parser.getMultipartParameterMapResult, 
				f.getMultipartSupportParameterMap(request));
	}
}
