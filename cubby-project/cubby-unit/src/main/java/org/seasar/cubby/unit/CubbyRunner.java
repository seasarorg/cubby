/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.RequestProcessor;
import org.seasar.cubby.internal.controller.RequestProcessor.CommandFactory;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.controller.impl.RequestProcessorImpl;
import org.seasar.cubby.internal.plugin.PluginManager;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.impl.RouterImpl;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;

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
		pluginManager.init(servletContext);
		final ActionInvokeFilterChain chain = new ActionInvokeFilterChain(
				filters);
		chain.doFilter(request, response);
		final ActionResult actionResult = chain.getActionResult();
		pluginManager.destroy();
		return actionResult;
	}

	static class ActionInvokeFilterChain implements FilterChain {

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
					this.actionResult = invoke((HttpServletRequest) request,
							(HttpServletResponse) response);
				} catch (final Exception e) {
					throw new ServletException(e);
				}
			}
		}

		private ActionResult invoke(final HttpServletRequest request,
				final HttpServletResponse response) throws Exception {
			final Router router = new RouterImpl();
			final PathInfo pathInfo = router.routing(request, response);
			if (pathInfo == null) {
				return null;
			}

			final RequestProcessor pathProcessor = new RequestProcessorImpl();
			final CommandFactory<ActionResultWrapper> commandFactory = new CubbyRunnerCommandFactory();
			final ActionResultWrapper actionResultWrapper = pathProcessor
					.process(request, response, pathInfo, commandFactory);
			if (actionResultWrapper == null) {
				return null;
			}
			return actionResultWrapper.getActionResult();
		}

		public ActionResult getActionResult() {
			return actionResult;
		}

	}

	static class CubbyRunnerCommandFactory implements
			CommandFactory<ActionResultWrapper> {

		final ActionProcessor actionProcessor = new ActionProcessorImpl();

		public Command<ActionResultWrapper> create(final Routing routing) {
			return new Command<ActionResultWrapper>() {

				public ActionResultWrapper execute(
						final HttpServletRequest request,
						final HttpServletResponse response) throws Exception {
					return actionProcessor.process(request, response, routing);
				}

			};
		}

	}

}
