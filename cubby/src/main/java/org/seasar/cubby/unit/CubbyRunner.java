package org.seasar.cubby.unit;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.routing.impl.PathProcessorImpl;

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
			final HttpServletResponse response) throws Exception {

		MockPathProccessorImpl pathProccessor = new MockPathProccessorImpl(
				request, response, new ArrayList<Pattern>());
		final ActionResultWrapper actionResultWrapper = pathProccessor
				.doProcess();
		if (actionResultWrapper == null) {
			return null;
		}
		return actionResultWrapper.getActionResult();
	}

	protected static class MockPathProccessorImpl extends PathProcessorImpl {

		private HttpServletRequest request;

		private HttpServletResponse response;

		private ActionProcessor actionProcessor = new ActionProcessorImpl();

		public MockPathProccessorImpl(HttpServletRequest request,
				HttpServletResponse response, List<Pattern> ignorePathPatterns) {
			super(request, response, ignorePathPatterns);
			this.request = request;
			this.response = response;
		}

		/**
		 * PathProcessor の process 処理をエミュレートする
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
