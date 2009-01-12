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
package org.seasar.cubby.internal.controller.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.internal.controller.RequestProcessor;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.RequestParserProvider;

/**
 * パス処理の実装クラス
 * 
 * @author someda
 * @author baba
 */
public class RequestProcessorImpl implements RequestProcessor {

	/**
	 * {@inheritDoc}
	 */
	public <T> T process(final HttpServletRequest request,
			final HttpServletResponse response, final PathInfo pathInfo,
			final CommandFactory<T> commandFactory) throws Exception {
		final HttpServletRequest wrappedRequest = new CubbyHttpServletRequestWrapper(
				request, pathInfo.getURIParameters());
		final RequestParserProvider requestParserProvider = ProviderFactory
				.get(RequestParserProvider.class);
		final Map<String, Object[]> parameterMap = requestParserProvider
				.getParameterMap(wrappedRequest);
		request.setAttribute(ATTR_PARAMS, parameterMap);
		final Routing routing = pathInfo.dispatch(parameterMap);
		final Command<T> command = commandFactory.create(routing);
		return ThreadContext.runInContext(wrappedRequest, response, command);
	}

}
