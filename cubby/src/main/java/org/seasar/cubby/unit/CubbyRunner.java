package org.seasar.cubby.unit;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.routing.impl.PathProcessorImpl;
import org.seasar.cubby.routing.Routing;

public class CubbyRunner {

	/**
	 * @param request
	 *            テスト用のリクエスト
	 * @param response
	 *            テスト用のレスポンス
	 * @return アクションメソッドの実行結果。アクションメソッドが見つからなかったり結果がない場合は <code>null</code>
	 * @throws Exception
	 *             アクションメソッドの実行時に例外が発生した場合
	 */
	public static ActionResult processAction(final HttpServletRequest request,
			final HttpServletResponse response, final Filter... filters)
			throws Exception {
		final ActionInvokeFilterChain chain = new ActionInvokeFilterChain(
				filters);
		chain.doFilter(request, response);
		return chain.getActionResult();
	}

	private static class ActionInvoker {
		public ActionResult invoke(final HttpServletRequest request,
				final HttpServletResponse response) throws Exception {
			final MockPathProccessorImpl pathProccessor = new MockPathProccessorImpl(
					request, response, new ArrayList<Pattern>());
			final ActionResultWrapper actionResultWrapper = pathProccessor
					.doProcess();
			if (actionResultWrapper == null) {
				return null;
			}
			return actionResultWrapper.getActionResult();
		}
	}

	private static class ActionInvokeFilterChain implements FilterChain {

		private final Iterator<Filter> iterator;

		private ActionResult actionResult = null;

		public ActionInvokeFilterChain(final Filter... filters) {
			this.iterator = Arrays.asList(filters).iterator();
		}

		public void doFilter(final ServletRequest request,
				final ServletResponse response) throws IOException,
				ServletException {
			if (iterator.hasNext()) {
				final Filter filter = iterator.next();
				filter.doFilter(request, response, this);
			} else {
				try {
					this.actionResult = new ActionInvoker().invoke(
							(HttpServletRequest) request,
							(HttpServletResponse) response);
				} catch (final Exception e) {
					throw new ServletException(e);
				}
			}
		}

		public ActionResult getActionResult() {
			return actionResult;
		}

	}

	protected static class MockPathProccessorImpl extends PathProcessorImpl {

		private final HttpServletRequest request;

		private final HttpServletResponse response;

		private final ActionProcessor actionProcessor = new ActionProcessorImpl();

		public MockPathProccessorImpl(final HttpServletRequest request,
				final HttpServletResponse response,
				final List<Pattern> ignorePathPatterns) {
			super(request, response, ignorePathPatterns);
			this.request = request;
			this.response = response;
		}

		/**
		 * PathProcessor の process 処理をエミュレートする
		 * 
		 * @return
		 */
		public ActionResultWrapper doProcess() throws Exception {
			if (!super.hasPathInfo()) {
				return null;
			}

			final HttpServletRequest wrappedRequest = super.wrapRequest();
			final Map<String, Object[]> parameterMap = super
					.parseRequest(wrappedRequest);
			request.setAttribute(ATTR_PARAMS, parameterMap);
			final Routing routing = super.dispatch(parameterMap);
			ThreadContext.newContext(wrappedRequest);
			try {
				return actionProcessor.process(wrappedRequest, response,
						routing);
			} finally {
				ThreadContext.restoreContext();
			}
		}
	}

}
