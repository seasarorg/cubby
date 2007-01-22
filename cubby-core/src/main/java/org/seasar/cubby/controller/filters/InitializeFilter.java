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
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Session;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.MultipartRequestParser;
import org.seasar.cubby.controller.impl.ActionErrorsImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.FlashHashMap;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.framework.util.ResourceBundleUtil;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うフィルターです。
 * {@link Action#initialize()}、{@link Action#prerender()}メソッドの実行も、
 * このフィルターが行います。
 * 
 *　TODO {@link Action#prerender()}メソッドの実行をInvocationFilterに移動させる
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
	protected void doBeforeFilter(final ActionContext context) {
		setupErrors(context);
		setupLocale(context);
		setupImplicitVariable(context);
		setupParams(context);
		setupRequestScopeFields(context);
		setupSessionScopeFields(context);
		setupFlash(context);
		context.getAction().initialize();
	}

	@Override
	protected void doAfterFilter(final ActionContext context,
			final ActionResult result) {
		if (CubbyUtils.isForwardResult(result)) {
			context.getAction().prerender();
		}
		bindAttributes(context);
	}

	void setupErrors(final ActionContext context) {
		context.getAction().setErrors(new ActionErrorsImpl());
	}

	void setupLocale(final ActionContext context) {
		HttpServletRequest req = context.getRequest();
		LocaleHolder.setLocale(req.getLocale());
	}

	void setupImplicitVariable(final ActionContext context) {
		HttpServletRequest req = context.getRequest();
		req.setAttribute("contextPath", req.getContextPath());
		ResourceBundle resource = ResourceBundle.getBundle(RES_MESSAGES,
				LocaleHolder.getLocale());
		Map messagesMap = ResourceBundleUtil.convertMap(resource);
		req.setAttribute("messages", messagesMap);
	}

	void setupParams(final ActionContext context) {
		HttpServletRequest request = context.getRequest();
		request.setAttribute(ATTR_PARAMS, getMultipartSupportParameterMap(request));
	}

	void setupRequestScopeFields(final ActionContext context) {
		Action controller = context.getAction();
		HttpServletRequest request = context.getRequest();
		for (Field f : controller.getClass().getFields()) {
			if (!isSessionScope(f)) {
				Object value = request.getAttribute(f.getName());
				if (value != null) {
					ClassUtils.setFieldValue(f, controller, value);
				}
			}
		}
	}

	void setupSessionScopeFields(final ActionContext context) {
		Action controller = context.getAction();
		HttpServletRequest request = context.getRequest();
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
	void setupFlash(final ActionContext context) {
		Action controller = context.getAction();
		HttpServletRequest request = context.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> flash = (Map<String, Object>) session
				.getAttribute(ATTR_FLASH);
		if (flash == null) {
			flash = new FlashHashMap();
			session.setAttribute(CubbyConstants.ATTR_FLASH, flash);
		}
		controller.setFlash(flash);
	}

	void bindAttributes(final ActionContext context) {
		// set controller
		Action action = context.getAction();
		HttpServletRequest request = context.getRequest();
		request.setAttribute(ATTR_CONTROLLER, action); // support legacy
		request.setAttribute(CubbyConstants.ATTR_ACTION, action);

		// set actioneErrors
		request.setAttribute(ATTR_ERRORS, action.getErrors());
		request.setAttribute(ATTR_ALL_ERRORS, action.getErrors()
				.getAllErrors());
		request.setAttribute(ATTR_ACTION_ERRORS, action.getErrors()
				.getActionErrors());
		request.setAttribute(ATTR_FIELD_ERRORS, action.getErrors()
				.getFieldErrors());

		// set field value
		HttpSession session = request.getSession();
		for (Field f : action.getClass().getFields()) {
			Object value = ClassUtils.getFieldValue(f, action);
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
