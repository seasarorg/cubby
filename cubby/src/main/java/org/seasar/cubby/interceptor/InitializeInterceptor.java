package org.seasar.cubby.interceptor;

import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
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
import org.seasar.cubby.controller.Populator;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.framework.util.ResourceBundleUtil;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うインターセプタです。
 * {@link Action#initialize()}、{@link Action#prerender()} の実行を行います。
 * 
 * @author agata
 * @author baba
 * @since 1.0
 */
public class InitializeInterceptor implements MethodInterceptor {

	private RequestParser requestParser;

	private ActionContext context;

	private HttpServletRequest request;

	public void setRequestParser(final RequestParser requestParser) {
		this.requestParser = requestParser;
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

		final ActionResult result = (ActionResult) invocation.proceed();
		if (result != null) {
			result.prerender(context, request);
		}

		return result;
	}

	void setupLocale(final ActionContext context) {
		LocaleHolder.setLocale(request.getLocale());
	}

	void setupImplicitVariable(final ActionContext context) {
		request.setAttribute(ATTR_CONTEXT_PATH, request.getContextPath());
		final ResourceBundle resource = ResourceBundleUtil.getBundle(
				RES_MESSAGES, LocaleHolder.getLocale());
		final Map<?, ?> messagesMap = ResourceBundleUtil.convertMap(resource);
		request.setAttribute(ATTR_MESSAGES, messagesMap);
	}

	void setupParams(final ActionContext context) {
		final Map<String, Object> parameterMap;
		if (requestParser == null) {
			parameterMap = this.getParameterMap(request);
		} else {
			parameterMap = requestParser.getParameterMap(request);
		}
		request.setAttribute(ATTR_PARAMS, parameterMap);
	}

	private void setupForm(final ActionContext context) {
		final Object formBean = context.getFormBean();
		if (formBean != null) {
			final Populator populator = context.getPopulator();
			final Map<String, Object> params = getParams();
			populator.populate(params, formBean);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getParams() {
		return (Map<String, Object>) request.getAttribute(ATTR_PARAMS);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getParameterMap(final HttpServletRequest request) {
		return request.getParameterMap();
	}

}
