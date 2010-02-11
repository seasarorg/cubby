/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.filter.CubbyFilter;
import org.seasar.cubby.internal.plugin.PluginManager;
import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.plugin.ActionResultInvocation;
import org.seasar.cubby.plugin.PluginRegistry;

/**
 * アクションを実行するためのクラスです。
 * 
 * @author someda
 * @author baba
 */
public class CubbyRunner {

	/** プラグインマネージャ。 */
	private static PluginManager pluginManager = new PluginManager(
			PluginRegistry.getInstance());

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
		final ServletContext servletContext = new MockServletContext();
		return processAction(servletContext, request, response, filters);
	}

	/**
	 * リクエストに応じたアクションを実行します。
	 * <p>
	 * <code>filters</code> が指定された場合はアクションを実行する前後に
	 * {@link Filter#doFilter(ServletRequest, ServletResponse, FilterChain)}
	 * を実行します。
	 * </p>
	 * 
	 * @param servletContext
	 *            サーブレットコンテキスト
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
	public static ActionResult processAction(
			final ServletContext servletContext,
			final HttpServletRequest request,
			final HttpServletResponse response, final Filter... filters)
			throws Exception {
		return doProcess(false, servletContext, request, response, filters);
	}

	/**
	 * リクエストに応じたアクションを実行し、その結果の {@link ActionResult} も実行します。
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
	 * @since 2.0.2
	 */
	public static ActionResult processActionAndExecuteActionResult(
			final HttpServletRequest request,
			final HttpServletResponse response, final Filter... filters)
			throws Exception {
		final ServletContext servletContext = new MockServletContext();
		return processActionAndExecuteActionResult(servletContext, request,
				response, filters);
	}

	/**
	 * リクエストに応じたアクションを実行し、その結果の {@link ActionResult} も実行します。
	 * <p>
	 * <code>filters</code> が指定された場合はアクションを実行する前後に
	 * {@link Filter#doFilter(ServletRequest, ServletResponse, FilterChain)}
	 * を実行します。
	 * </p>
	 * 
	 * @param servletContext
	 *            サーブレットコンテキスト
	 * @param request
	 *            テスト用の要求
	 * @param response
	 *            テスト用の応答
	 * @param filters
	 *            実行するサーブレットフィルタ
	 * @return アクションメソッドの実行結果。アクションメソッドが見つからなかったり結果がない場合は <code>null</code>
	 * @throws Exception
	 *             アクションメソッドの実行時に例外が発生した場合
	 * @since 2.0.2
	 */
	public static ActionResult processActionAndExecuteActionResult(
			final ServletContext servletContext,
			final HttpServletRequest request,
			final HttpServletResponse response, final Filter... filters)
			throws Exception {
		return doProcess(true, servletContext, request, response, filters);
	}

	/**
	 * リクエストに応じたアクションを実行します。
	 * 
	 * @param executeActionResult
	 *            {@link ActionResult} を実行する場合は <code>true</code>、そうでない場合は
	 *            <code>false</code>
	 * @param servletContext
	 *            サーブレットコンテキスト
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
	private static ActionResult doProcess(final boolean executeActionResult,
			final ServletContext servletContext,
			final HttpServletRequest request,
			final HttpServletResponse response, final Filter... filters)
			throws Exception {
		final FilterConfig filterConfig = new MockFilterConfig(servletContext);
		for (final Filter filter : filters) {
			filter.init(filterConfig);
		}

		try {
			final CubbyRunnerPlugin cubbyRunnerPlugin = new CubbyRunnerPlugin();
			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			pluginRegistry.register(cubbyRunnerPlugin);

			pluginManager.init(servletContext);
			CubbyRunnerHttpServletRequestWrapper requestWrapper = new CubbyRunnerHttpServletRequestWrapper(
					request);
			final ActionInvokeFilterChain chain = new ActionInvokeFilterChain(
					filters);
			chain.doFilter(requestWrapper, response);

			final ActionResult actionResult = cubbyRunnerPlugin
					.getActionResult();
			if (executeActionResult) {
				final ActionContext actionContext = requestWrapper
						.getActionContext();
				actionResult.execute(actionContext, requestWrapper, response);
			}
			return actionResult;
		} finally {
			pluginManager.destroy();
			for (final Filter filter : filters) {
				filter.destroy();
			}
		}
	}

	static class ActionInvokeFilterChain implements FilterChain {

		private final Iterator<Filter> iterator;

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
					invoke((HttpServletRequest) request,
							(HttpServletResponse) response);
				} catch (final Exception e) {
					throw new ServletException(e);
				}
			}
		}

		private void invoke(final HttpServletRequest request,
				final HttpServletResponse response) throws Exception {
			final CubbyFilter cubbyFilter = new CubbyFilter();
			cubbyFilter.doFilter(request, response, this);
		}

	}

	static class CubbyRunnerPlugin extends AbstractPlugin {

		private ActionResult actionResult;

		@Override
		public void invokeActionResult(ActionResultInvocation invocation)
				throws Exception {
			this.actionResult = invocation.getActionResult();
		}

		public ActionResult getActionResult() {
			return actionResult;
		}

	}

	static class CubbyRunnerHttpServletRequestWrapper extends
			HttpServletRequestWrapper {

		private ActionContext actionContext;

		public CubbyRunnerHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		@Override
		public void setAttribute(String name, Object o) {
			if (CubbyConstants.ATTR_ACTION_CONTEXT.equals(name)) {
				this.actionContext = (ActionContext) o;
			}
			super.setAttribute(name, o);
		}

		public ActionContext getActionContext() {
			return actionContext;
		}

	}

}
