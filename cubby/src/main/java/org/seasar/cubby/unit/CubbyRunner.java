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
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.routing.impl.PathProcessorImpl;
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
		 * @return アクションの実行結果
		 * @throws Exception
		 *             アクションの実行中に例外が発生した場合
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
			final ActionResultWrapper actionResultWrapper = ThreadContext
					.runInContext(wrappedRequest, response,
							new Command<ActionResultWrapper>() {

								public ActionResultWrapper execute()
										throws Exception {
									return actionProcessor.process(
											wrappedRequest, response, routing);
								}

							});
			return actionResultWrapper;
		}
	}

}
