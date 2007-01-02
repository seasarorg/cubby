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
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionHolder;
import org.seasar.cubby.controller.MockController;
import org.seasar.cubby.controller.MockMultipartRequestParser;
import org.seasar.cubby.controller.impl.ActionContextImpl;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.controller.results.Redirect;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.cubby.util.ParameterMap;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class InitializeFilterTest extends TestCase {
	MockMultipartRequestParser parser = new MockMultipartRequestParser();
	InitializeFilter f = new InitializeFilter(parser);
	MockServletContextImpl context = new MockServletContextImpl("/cubby");
	MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(context, "dummy");
	MockHttpServletResponseImpl response = new MockHttpServletResponseImpl(request);
	MockController controller = new MockController();
	ActionFilterChain chain = new ActionFilterChain();
	String[] uriConvertNames = new String[]{};
	ActionContext action;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		request.setLocale(Locale.JAPANESE);
		request.addParameter("param1", "value1");
		request.addParameter("param2", "value2");
		Method actionMethod = ClassUtils.getMethod(MockController.class, "dummy1", new Class[]{});
		assertNotNull(actionMethod);
		ActionHolder holder = new ActionHolder(actionMethod,  chain, uriConvertNames);
		HashMap<String, Object> uriParams = new LinkedHashMap<String, Object>();
		uriParams.put("id", "1");
		uriParams.put("userId", "seasar");
		action = new ActionContextImpl(request, response, controller, holder, uriParams);
	}
	
	public void testConstractor() throws Exception {
		assertNotNull(f);
	}
	
	public void testDoBeforeFilter() throws Exception {
		assertFalse("実行前", controller.executedInitalizeMethod);
		f.doBeforeFilter(action);
		assertTrue("controller.initalizeメソッドが実行される", controller.executedInitalizeMethod);
	}
	
	public void testDoAfterFilter_forward() throws Exception {
		assertFalse("実行前", controller.executedPrerenderMethod);
		f.doBeforeFilter(action);
		f.doAfterFilter(action, new Forward("sample.jsp"));
		assertTrue("結果がForwardの場合、prerenderメソッドが実行される", 
				controller.executedPrerenderMethod);
	}

	public void testDoAfterFilter_notForward() throws Exception {
		assertFalse("実行前", controller.executedPrerenderMethod);
		f.doBeforeFilter(action);
		f.doAfterFilter(action, new Redirect("/list"));
		assertFalse("結果がForward以外の場合、prerenderメソッドは実行されない", 
				controller.executedPrerenderMethod);
	}
	
	public void testSetupErrors() throws Exception {
		assertNull(controller.getErrors());
		f.setupErrors(action);
		assertTrue(controller.getErrors().isEmpty());
	}

	public void testSetupLocale() throws Exception {
		// locale=ja
		request.setLocale(Locale.JAPANESE);
		f.setupLocale(action);
		assertEquals("requestのロケールがLocaleHolder（ThreadLocal）にセットされる", 
				Locale.JAPANESE, LocaleHolder.getLocale());

		// locale=en
		request.setLocale(Locale.ENGLISH);
		f.setupLocale(action);
		assertEquals("requestのロケールがLocaleHolder（ThreadLocal）にセットされる",
				Locale.ENGLISH, LocaleHolder.getLocale());
	}
	
	public void testSetupImplicitVariable() throws Exception {
		assertNull("実行前はnull", request.getAttribute("contextPath"));
		assertNull("実行前はnull", request.getAttribute("messages"));

		LocaleHolder.setLocale(Locale.JAPANESE);
		f.setupImplicitVariable(action);
		assertEquals("request.getContextPathと同値", 
				"/cubby", request.getAttribute("contextPath"));
		Map messageMap = (Map) request.getAttribute("messages");
		assertNotNull("messages.propertiesをMap化したオブジェクト（JSPのELからアクセスするため）", 
				messageMap);
		assertEquals("messages.propertiesの値を取得", 
				"{0}は必須です。", messageMap.get("valid.required"));
	}
	
	public void testSetupParams() throws Exception {
		assertNull("実行前はnull", controller.getParams());

		f.setupParams(action);
		ParameterMap params =  controller.getParams();
		assertEquals("コントローラーのParameterMapはリクエストにも格納されている", params, request.getAttribute(ATTR_PARAMS));
		assertEquals("URIに埋め込まれたパラメータ(id,userId)とリクエストパラメータ(param1,param2)の集合", 4, params.size());
		assertEquals("1", params.get("id"));
		assertEquals("seasar", params.get("userId"));
		assertEquals("value1", params.get("param1"));
		assertEquals("value2", params.get("param2"));
	}
	
	public void testSetupRequest() throws Exception {
		assertNull("実行前はnull", controller.getRequest());
		assertNull("実行前はnull", controller.attr1);
		assertNull("実行前はnull", controller.attr2);
		assertNull("実行前はnull", controller.attr3);

		request.setAttribute("attr1", "v1");
		request.setAttribute("attr2", "v2");
		request.setAttribute("attr3", "v3");
		f.setupRequest(action);
		assertNotNull("request.attributeをMap化したものがControllerにセット済み", 
				controller.getRequest());
		assertEquals("request.attributeをMap化したもの値を取得", 
				"v1", controller.getRequest().get("attr1"));
		assertEquals("request.attributeの値はControllerのpublicフィールドにバインド", 
				"v1", controller.attr1);
		assertEquals("request.attributeの値はControllerのpublicフィールドにバインド", 
				"v2", controller.attr2);
		assertNull("@Session(セッションスコープの指定)はバインド対象外", controller.attr3);
	}

	public void testSetupSession() throws Exception {
		assertNull("実行前はnull", controller.getSession());
		assertNull("実行前はnull", controller.attr1);
		assertNull("実行前はnull", controller.attr2);
		assertNull("実行前はnull", controller.attr3);

		request.getSession().setAttribute("attr1", "v1");
		request.getSession().setAttribute("attr2", "v2");
		request.getSession().setAttribute("attr3", "v3");
		f.setupSession(action);
		assertNotNull("session.attributeをMap化したものがControllerにセット済み", 
				controller.getSession());
		assertNull("@Session(セッションスコープの指定)が付いていないものはバインド対象外", 
				controller.attr1);
		assertNull("@Session(セッションスコープの指定)が付いていないものはバインド対象外", 
				controller.attr2);
		assertEquals("@Session(セッションスコープの指定)が付いていたら、session.attributeの値をバインド", 
				"v3", controller.attr3);
	}

	public void testSetupFlashSessionNew() throws Exception {
		assertNull("実行前はnull", controller.getFlash());
		assertNull("実行前はnull", request.getSession().getAttribute(ATTR_FLASH));

		f.setupFlash(action);
		assertNotNull("session.attributeをMap化したものがControllerにセット済み", 
				controller.getFlash());
	}

	public void testSetupFlashSessionRestore() throws Exception {
		assertNull("実行前はnull", controller.getFlash());
		assertNull("実行前はnull", request.getSession().getAttribute(ATTR_FLASH));
		
		HashMap<String, Object> flash = new HashMap<String, Object>();
		flash.put("f1", "v1");
		request.getSession().setAttribute(ATTR_FLASH, flash);
		f.setupFlash(action);
		assertEquals("既にsession中にflashオブジェクトが存在した場合、そのflashオブジェクトを引き継ぐ", 
				flash, controller.getFlash());
		assertNotNull(request.getSession().getAttribute(ATTR_FLASH));
	}

	public void testBindAttributes() throws Exception {
		controller.attr1 = "v1";
		controller.attr2 = "v2";
		controller.attr3 = "v3";
		f.setupErrors(action);

		assertNull("実行前はnull", request.getAttribute(ATTR_CONTROLLER));
		assertNull("実行前はnull", request.getAttribute(ATTR_ERRORS));
		assertNull("実行前はnull", request.getAttribute(ATTR_ALL_ERRORS));
		assertNull("実行前はnull", request.getAttribute(ATTR_ACTION_ERRORS));
		assertNull("実行前はnull", request.getAttribute(ATTR_FIELD_ERRORS));
		assertNull("実行前はnull", request.getAttribute("attr1"));
		assertNull("実行前はnull", request.getAttribute("attr2"));
		assertNull("実行前はnull", request.getSession().getAttribute("attr3"));
		
		f.bindAttributes(action);

		// set controller
		assertEquals("controller自身がrequest.attributeにバインド", 
				controller, request.getAttribute(ATTR_CONTROLLER));

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
				controller.getErrors(), request.getAttribute(ATTR_ERRORS));
		assertEquals("'allErrros'にcontroller.getErrors().getAllErrors()をバインド", 
				controller.getErrors().getAllErrors(), request.getAttribute(ATTR_ALL_ERRORS));
		assertEquals("'actionErrros'にcontroller.getErrors().getActionErrors()をバインド", 
				controller.getErrors().getActionErrors(), request.getAttribute(ATTR_ACTION_ERRORS));
		assertEquals("'filedErrors'にcontroller.getErrors().getFiledErrors()をバインド", 
				controller.getErrors().getFieldErrors(), request.getAttribute(ATTR_FIELD_ERRORS));

	}
	
	public void testIsSessionScape() throws Exception {
		assertFalse(InitializeFilter.isSessionScope(MockController.class.getField("attr1")));
		assertFalse(InitializeFilter.isSessionScope(MockController.class.getField("attr2")));
		assertTrue(InitializeFilter.isSessionScope(MockController.class.getField("attr3")));
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
