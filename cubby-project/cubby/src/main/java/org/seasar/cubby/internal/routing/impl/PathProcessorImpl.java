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
package org.seasar.cubby.internal.routing.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.routing.PathProcessor;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.RequestParserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * パス処理の実装クラス
 * 
 * @author someda
 */
public class PathProcessorImpl implements PathProcessor {

	// TODO
	private final ActionProcessor actionProcessor = new ActionProcessorImpl();

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(PathProcessor.class);

	public void process(final HttpServletRequest request,
			final HttpServletResponse response, final PathInfo pathInfo)
			throws IOException, ServletException {
		final HttpServletRequest wrappedRequest = wrapRequest(request, pathInfo);
		final Map<String, Object[]> parameterMap = parseRequest(wrappedRequest);
		request.setAttribute(ATTR_PARAMS, parameterMap);

		final Routing routing = pathInfo.dispatch(parameterMap);
		final ActionProcessorInvokeCommand actionProcessorInvokeCommand = new ActionProcessorInvokeCommand(
				routing);
		try {
			ThreadContext.runInContext(wrappedRequest, response,
					actionProcessorInvokeCommand);
		} catch (final Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else if (e instanceof ServletException) {
				throw (ServletException) e;
			} else {
				throw new ServletException(e);
			}
		}
	}

	private class ActionProcessorInvokeCommand implements Command<Void> {

		private final Routing routing;

		public ActionProcessorInvokeCommand(final Routing routing) {
			this.routing = routing;
		}

		public Void execute(final HttpServletRequest request,
				final HttpServletResponse response) throws Exception {
			final ActionResultWrapper actionResultWrapper = actionProcessor
					.process(request, response, routing);
			actionResultWrapper.execute(request, response);
			return null;
		}

	}

	/**
	 * PathInfo を利用し、オリジナルのリクエストをラップする
	 * 
	 * @return
	 */
	protected final HttpServletRequest wrapRequest(
			final HttpServletRequest request, final PathInfo pathInfo) {
		final Map<String, String[]> parameters = pathInfo.getURIParameters();
		return new CubbyHttpServletRequestWrapper(request, parameters);
	}

	/**
	 * リクエストをパースしてパラメータを取り出し、{@link Map}に変換して返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return リクエストパラメータの{@link Map}
	 */
	protected final Map<String, Object[]> parseRequest(
			final HttpServletRequest request) {
		final RequestParserProvider requestParserProvider = ProviderFactory
				.get(RequestParserProvider.class);
		final RequestParser requestParser = requestParserProvider
				.getRequestParser(request);
		if (requestParser == null) {
			throw new NullPointerException("requestParser");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0016", requestParser));
		}
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		return parameterMap;
	}

}
