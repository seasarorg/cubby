package org.seasar.cubby.unit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.unit.mock.impl.MockHttpServletResponseImpl;
import org.seasar.cubby.unit.mock.impl.MockServletContextImpl;

public class CubbyMock {

	private static final String CUBBY_CONTEXT_PATH = "/cubby";

	private static final String CUBBY_PATH = "/dummy";

	private CubbyMock() {
	}

	public static HttpServletRequest createRequest() {
		return createRequest(CUBBY_CONTEXT_PATH, CUBBY_PATH);
	}

	public static HttpServletRequest createRequest(String path) {
		return createRequest(CUBBY_CONTEXT_PATH, path);
	}

	public static HttpServletRequest createRequest(String contextPath,
			String path) {
		MockServletContextImpl servletContext = new MockServletContextImpl(
				contextPath);
		return servletContext.createRequest(path);
	}

	public static HttpServletResponse createResponse(HttpServletRequest request) {
		return new MockHttpServletResponseImpl(request);
	}
}
