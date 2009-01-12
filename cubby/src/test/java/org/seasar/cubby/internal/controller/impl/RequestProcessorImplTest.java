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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParserImpl;
import org.seasar.cubby.internal.controller.RequestProcessor;
import org.seasar.cubby.internal.controller.RequestProcessor.CommandFactory;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;

public class RequestProcessorImplTest {

	private RequestProcessor requestProcessor = new RequestProcessorImpl();

	@Before
	public void setupProvider() {
		final List<RequestParser> requestParsers = new ArrayList<RequestParser>();
		requestParsers.add(new DefaultRequestParserImpl());

		ProviderFactory.bind(RequestParserProvider.class).toInstance(
				new AbstractRequestParserProvider() {

					@Override
					protected Collection<RequestParser> getRequestParsers() {
						return requestParsers;
					}

				});
	}

	@After
	public void teardownProvider() {
		ProviderFactory.clear();
	}

	@Test
	public void process() throws Exception {
		Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		Routing routing = createMock(Routing.class);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getParameterMap()).andReturn(parameterMap);
		request.setAttribute(CubbyConstants.ATTR_PARAMS, parameterMap);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		PathInfo pathInfo = createMock(PathInfo.class);
		expect(pathInfo.dispatch(parameterMap)).andReturn(routing);
		expect(pathInfo.getURIParameters()).andReturn(
				new HashMap<String, String[]>());
		CommandFactory<Void> commandFactory = createMock(CommandFactory.class);
		expect(commandFactory.create(routing)).andReturn(new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				assertTrue(request instanceof CubbyHttpServletRequestWrapper);
				return null;
			}

		});
		replay(routing, request, response, pathInfo, commandFactory);

		requestProcessor.process(request, response, pathInfo, commandFactory);

		verify(routing, request, response, pathInfo, commandFactory);
	}

}
