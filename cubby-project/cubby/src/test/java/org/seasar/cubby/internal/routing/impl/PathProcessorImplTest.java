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
import org.seasar.cubby.internal.routing.Routing;

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
		replay(request,routing);
		
		pathProcessor = new PathProcessorImpl(request, response,
				new ArrayList<Pattern>());		
		assertTrue(pathProcessor.hasPathInfo());
	}
	
}
