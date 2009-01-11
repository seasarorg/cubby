/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.unit;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

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
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.impl.PathProcessorImpl;
import org.seasar.cubby.internal.routing.impl.RouterImpl;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;

/**
 * アクションを実行するためのクラスです。
 * 
 * @author someda
 * @author baba
 * @since 2.0.0
 */
public class CubbyRunner {

	/**
	 * リクエストに応じたアクションを実行します。
	 * <p>
	 * <code>filters</code> が指定された場合はアクションを実行する前後に
	 * {@link Filter#doFilter(ServletRequest, ServletResponse, FilterChain)}
	 * を実行します。
	 * </p>
	 * 
	 * @param request
	 *            テスト用の要求
	 * @param response
	 *            テスト用の応答
	 * @param filters
	 *            実行するサーブレットフィルタ
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
			final Router router = new RouterImpl();
			final PathInfo pathInfo = router.routing(request, response);
			if (pathInfo != null) {
				final MockPathProccessorImpl pathProccessor = new MockPathProccessorImpl();
				final ActionResultWrapper actionResultWrapper = pathProccessor
						.doProcess(request, response, pathInfo);
				if (actionResultWrapper == null) {
					return null;
				}
				return actionResultWrapper.getActionResult();
			} else {
				return null;
			}
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

		private final ActionProcessor actionProcessor = new ActionProcessorImpl();

		/**
		 * PathProcessor の process 処理をエミュレートする
		 * 
		 * @return アクションの実行結果
		 * @throws Exception
		 *             アクションの実行中に例外が発生した場合
		 */
		public ActionResultWrapper doProcess(HttpServletRequest request,
				HttpServletResponse response, PathInfo pathInfo)
				throws Exception {
			if (pathInfo == null) {
				return null;
			}

			final HttpServletRequest wrappedRequest = super.wrapRequest(
					request, pathInfo);
			final Map<String, Object[]> parameterMap = super
					.parseRequest(wrappedRequest);
			request.setAttribute(ATTR_PARAMS, parameterMap);
			final Routing routing = pathInfo.dispatch(parameterMap);
			final ActionResultWrapper actionResultWrapper = ThreadContext
					.runInContext(wrappedRequest, response,
							new Command<ActionResultWrapper>() {

								public ActionResultWrapper execute(
										final HttpServletRequest request,
										final HttpServletResponse response)
										throws Exception {
									return actionProcessor.process(request,
											response, routing);
								}

							});
			return actionResultWrapper;
		}
	}

}
