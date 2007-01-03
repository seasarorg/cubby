package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_ALL_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTROLLER;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FIELD_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.annotation.Session;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.MultipartRequestParser;
import org.seasar.cubby.controller.impl.ActionErrorsImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.FlashHashMap;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.cubby.util.ParameterMap;
import org.seasar.cubby.util.RequestMap;
import org.seasar.cubby.util.ResourceBundleUtils;
import org.seasar.cubby.util.SessionMap;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うフィルターです。
 * {@link Controller#initialize()}、{@link Controller#prerender()}メソッドの実行も、
 * このフィルターが行います。
 * 
 *　TODO {@link Controller#prerender()}メソッドの実行をInvocationFilterに移動させる
 * 
 * @author agata
 * @since 1.0
 */
public class InitializeFilter extends AroundFilter {

	private final MultipartRequestParser multipartRequestParser;

	public InitializeFilter(final MultipartRequestParser multipartRequestParser) {
		this.multipartRequestParser = multipartRequestParser;
	}

	@Override
	protected void doBeforeFilter(final ActionContext action) {
		setupErrors(action);
		setupLocale(action);
		setupImplicitVariable(action);
		setupParams(action);
		setupRequest(action);
		setupSession(action);
		setupFlash(action);
		action.getController().initialize();
	}

	@Override
	protected void doAfterFilter(final ActionContext action,
			final ActionResult result) {
		if (CubbyUtils.isForwardResult(result)) {
			action.getController().prerender();
		}
		bindAttributes(action);
	}

	void setupErrors(final ActionContext action) {
		action.getController().setErrors(new ActionErrorsImpl());
	}

	void setupLocale(final ActionContext action) {
		HttpServletRequest req = action.getRequest();
		LocaleHolder.setLocale(req.getLocale());
	}

	void setupImplicitVariable(final ActionContext action) {
		HttpServletRequest req = action.getRequest();
		req.setAttribute("contextPath", req.getContextPath());
		ResourceBundle resource = ResourceBundle.getBundle(RES_MESSAGES,
				LocaleHolder.getLocale());
		Map messagesMap = ResourceBundleUtils.toMap(resource);
		req.setAttribute("messages", messagesMap);
	}

	void setupParams(final ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.putAll(action.getUriParams());
		paramMap.putAll(getMultipartSupportParameterMap(request));
		ParameterMap params = new ParameterMap(paramMap);
		controller.setParams(params);
		request.setAttribute(ATTR_PARAMS, controller.getParams());
	}

	void setupRequest(final ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		RequestMap requestMap = new RequestMap(request);
		controller.setRequest(requestMap);
		setupRequestScopeFields(action);
	}

	private void setupRequestScopeFields(final ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		for (Field f : controller.getClass().getFields()) {
			if (!isSessionScope(f)) {
				Object value = request.getAttribute(f.getName());
				if (value != null) {
					ClassUtils.setFieldValue(f, controller, value);
				}
			}
		}
	}

	void setupSession(final ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		SessionMap sessionMap = new SessionMap(request.getSession());
		controller.setSession(sessionMap);
		setupSessionScopeFields(action);
	}

	private void setupSessionScopeFields(final ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		HttpSession session = request.getSession();
		for (Field f : controller.getClass().getFields()) {
			if (isSessionScope(f)) {
				Object value = session.getAttribute(f.getName());
				if (value != null) {
					ClassUtils.setFieldValue(f, controller, value);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	void setupFlash(final ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> flash = (Map<String, Object>) session
				.getAttribute(ATTR_FLASH);
		if (flash == null) {
			flash = new FlashHashMap();
			session.setAttribute(CubbyConstants.ATTR_FLASH, flash);
		}
		controller.setFlash(flash);
	}

	void bindAttributes(final ActionContext action) {
		// set controller
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		request.setAttribute(ATTR_CONTROLLER, controller);

		// set actioneErrors
		request.setAttribute(ATTR_ERRORS, controller.getErrors());
		request.setAttribute(ATTR_ALL_ERRORS, controller.getErrors()
				.getAllErrors());
		request.setAttribute(ATTR_ACTION_ERRORS, controller.getErrors()
				.getActionErrors());
		request.setAttribute(ATTR_FIELD_ERRORS, controller.getErrors()
				.getFieldErrors());

		// set field value
		HttpSession session = request.getSession();
		for (Field f : controller.getClass().getFields()) {
			Object value = ClassUtils.getFieldValue(f, controller);
			if (isSessionScope(f)) {
				session.setAttribute(f.getName(), value);
			} else {
				request.setAttribute(f.getName(), value);
			}
		}
	}

	static boolean isSessionScope(final Field f) {
		return f.getAnnotation(Session.class) != null;
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	Map<String, Object> getMultipartSupportParameterMap(
			final HttpServletRequest request) {
		if (multipartRequestParser.isMultipart(request)) {
			return multipartRequestParser.getMultipartParameterMap(request);
		} else {
			return request.getParameterMap();
		}
	}
}
