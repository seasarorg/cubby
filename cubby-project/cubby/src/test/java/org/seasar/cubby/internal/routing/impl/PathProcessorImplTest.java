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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.routing.Routing;

public class PathProcessorImplTest {

	private PathProcessorImpl pathProcessor;

	private HttpServletRequest request;

	private HttpServletResponse response;

	/**
	 * Routing 情報がある場合
	 */
	@Test
	public void testHasPathInfo1() {
		Routing routing = createMock(Routing.class);		
		request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ROUTING)).andReturn(routing);
		request.removeAttribute(CubbyConstants.ATTR_ROUTING);
		replay(request,routing);
		
		pathProcessor = new PathProcessorImpl(request, response,
				new ArrayList<Pattern>());		
		assertTrue(pathProcessor.hasPathInfo());
	}
	
}
