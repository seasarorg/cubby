package org.seasar.cubby.interceptor;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.Populator;
import org.seasar.cubby.controller.RequestParser;

/**
 * コントローラの初期化や実行結果のrequest/sessionへの反映などを行うインターセプタです。
 * {@link Action#initialize()}、{@link Action#prerender()} の実行を行います。
 * 
 * @author agata
 * @author baba
 * @since 1.0
 */
public class InitializeInterceptor implements MethodInterceptor {

	private CubbyConfiguration cubbyConfiguration;

	private HttpServletRequest request;

	private ActionContext context;

	public void setCubbyConfiguration(final CubbyConfiguration cubbyConfiguration) {
		this.cubbyConfiguration = cubbyConfiguration;
	}

	public void setRequest(final HttpServletRequest request) {
		this.request = request;
	}

	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		setupParams(context, request);
		setupForm(context, request);

		final Action action = context.getAction();
		action.initialize();

		final ActionResult result = (ActionResult) invocation.proceed();
		if (result != null) {
			result.prerender(context);
		}

		return result;
	}

	void setupParams(final ActionContext context,
			final HttpServletRequest request) {
		final RequestParser requestParser = cubbyConfiguration
				.getRequestParser();
		final Map<String, Object> parameterMap = requestParser
				.getParameterMap(request);
		request.setAttribute(ATTR_PARAMS, parameterMap);
	}

	private void setupForm(final ActionContext context,
			final HttpServletRequest request) {
		final Object formBean = context.getFormBean();
		if (formBean != null) {
			final Populator populator = context.getPopulator();
			final Map<String, Object[]> params = getParams(request);
			populator.populate(params, formBean);
		}
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object[]> getParams(
			final HttpServletRequest request) {
		return (Map<String, Object[]>) request.getAttribute(ATTR_PARAMS);
	}

}
