package org.seasar.cubby.interceptor;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_ALL_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FIELD_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_MESSAGES;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.MultipartRequestParser;
import org.seasar.cubby.controller.Populator;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.ResourceBundleUtil;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うフィルターです。 {@link Action#initialize()}、{@link Action#prerender()}メソッドの実行も、
 * このフィルターが行います。
 * 
 * TODO {@link Action#prerender()}メソッドの実行をInvocationFilterに移動させる
 * 
 * @author agata
 * @since 1.0
 */
public class InitializeInterceptor implements MethodInterceptor {

	private MultipartRequestParser multipartRequestParser;

	private Populator populator;

	private ActionContext context;

	private HttpServletRequest request;

	public void setMultipartRequestParser(
			final MultipartRequestParser multipartRequestParser) {
		this.multipartRequestParser = multipartRequestParser;
	}

	public void setPopulator(final Populator populator) {
		this.populator = populator;
	}

	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		setupLocale(context);
		setupImplicitVariable(context);
		setupParams(context);
		setupForm(context);

		final Action action = context.getAction();
		action.initialize();

		ActionResult result = (ActionResult) invocation.proceed();

		result.prerender(context);

		bindAttributes(context);

		return result;
	}

	void setupLocale(final ActionContext context) {
		LocaleHolder.setLocale(request.getLocale());
	}

	void setupImplicitVariable(final ActionContext context) {
		request.setAttribute(ATTR_CONTEXT_PATH, request.getContextPath());
		ResourceBundle resource = ResourceBundleUtil.getBundle(RES_MESSAGES,
				LocaleHolder.getLocale());
		Map<?, ?> messagesMap = ResourceBundleUtil.convertMap(resource);
		request.setAttribute(ATTR_MESSAGES, messagesMap);
	}

	void setupParams(final ActionContext context) {
		Map<String, Object> parameterMap = getMultipartSupportParameterMap(request);
		request.setAttribute(ATTR_PARAMS, parameterMap);
	}

	private void setupForm(ActionContext context) {
		Object formBean = context.getFormBean();
		if (formBean != null) {
			Map<String, Object> params = getParams();
			populator.populate(params, formBean);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getParams() {
		return (Map<String, Object>) request.getAttribute(ATTR_PARAMS);
	}

	void bindAttributes(final ActionContext context) {
		// set controller
		Action action = context.getAction();
		// request.setAttribute(ATTR_CONTROLLER, action); // support legacy
		request.setAttribute(ATTR_ACTION, action);

		// set actioneErrors
		request.setAttribute(ATTR_ERRORS, action.getErrors());
		request
				.setAttribute(ATTR_ALL_ERRORS, action.getErrors()
						.getAllErrors());
		request.setAttribute(ATTR_ACTION_ERRORS, action.getErrors()
				.getActionErrors());
		request.setAttribute(ATTR_FIELD_ERRORS, action.getErrors()
				.getFieldErrors());

		final BeanDesc beanDesc = BeanDescFactory
				.getBeanDesc(action.getClass());
		for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
			final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
			if (propertyDesc.isReadable()) {
				final String name = propertyDesc.getPropertyName();
				final Object value = propertyDesc.getValue(action);
				request.setAttribute(name, value);
			}
		}
	}

	Map<String, Object> getMultipartSupportParameterMap(
			final HttpServletRequest request) {
		if (multipartRequestParser.isMultipart(request)) {
			return multipartRequestParser.getMultipartParameterMap(request);
		} else {
			return getParameterMap(request);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getParameterMap(final HttpServletRequest request) {
		return request.getParameterMap();
	}

}
