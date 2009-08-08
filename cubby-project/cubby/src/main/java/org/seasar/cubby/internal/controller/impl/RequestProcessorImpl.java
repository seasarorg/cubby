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
package org.seasar.cubby.internal.controller.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.RequestProcessor;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugin.RequestProcessingInvocation;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.RequestParserProvider;

/**
 * 要求処理の実装クラス。
 * 
 * @author someda
 * @author baba
 */
public class RequestProcessorImpl implements RequestProcessor {

	/**
	 * {@inheritDoc}
	 */
	public void process(final HttpServletRequest request,
			final HttpServletResponse response, final PathInfo pathInfo)
			throws Exception {
		final HttpServletRequest wrappedRequest = new CubbyHttpServletRequestWrapper(
				request, pathInfo.getURIParameters());
		final RequestProcessingInvocation invocation = new RequestProcessingInvocationImpl(
				wrappedRequest, response, pathInfo);
		invocation.proceed();
	}

	/**
	 * 要求処理の実行情報の実装です。
	 * 
	 * @author baba
	 */
	static class RequestProcessingInvocationImpl implements
			RequestProcessingInvocation {

		/** アクションを処理します。 */
		private final ActionProcessor actionProcessor = new ActionProcessorImpl();

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** パスから取得した情報。 */
		private final PathInfo pathInfo;

		/** プラグインのイテレータ。 */
		private final Iterator<Plugin> pluginsIterator;

		/**
		 * インスタンス化します。
		 * 
		 * @param request
		 *            要求
		 * @param response
		 *            応答
		 * @param pathInfo
		 *            パスから取得した情報
		 */
		public RequestProcessingInvocationImpl(
				final HttpServletRequest request,
				final HttpServletResponse response, final PathInfo pathInfo) {
			this.request = request;
			this.response = response;
			this.pathInfo = pathInfo;

			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public Void proceed() throws Exception {
			if (pluginsIterator.hasNext()) {
				final Plugin plugin = pluginsIterator.next();
				plugin.invokeRequestProcessing(this);
			} else {
				final HttpServletRequest request = getRequest();
				final RequestParserProvider requestParserProvider = ProviderFactory
						.get(RequestParserProvider.class);
				final Map<String, Object[]> parameterMap = requestParserProvider
						.getParameterMap(request);
				request.setAttribute(ATTR_PARAMS, parameterMap);
				final Routing routing = pathInfo.dispatch(parameterMap);
				final Command command = new Command() {

					public void execute(final HttpServletRequest request,
							final HttpServletResponse response)
							throws Exception {
						final ActionResultWrapper actionResultWrapper = actionProcessor
								.process(request, response, routing);
						actionResultWrapper.execute(request, response);
					}

				};
				ThreadContext.runInContext(request, response, command);
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletRequest getRequest() {
			return request;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletResponse getResponse() {
			return response;
		}

		/**
		 * {@inheritDoc}
		 */
		public PathInfo getPathInfo() {
			return pathInfo;
		}

	}

}
