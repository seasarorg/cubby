package org.seasar.cubby.controller.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.RequestParserSelector;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;

public class RequestParserSelectorImplTest extends S2TestCase {

	public RequestParserSelector requestParserSelector;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testSelect1() {
		MockHttpServletRequest request = getRequest();
		RequestParser requestParser = requestParserSelector.select(request);
		assertTrue(requestParser instanceof DefaultRequestParserImpl);
	}

	public void testSelect2() {
		MockHttpServletRequest request = getRequest();
		request.setContentType("application/x-www-form-urlencoded");
		RequestParser requestParser = requestParserSelector.select(request);
		assertTrue(requestParser instanceof DefaultRequestParserImpl);
	}

	public void testSelect3() {
		MockHttpServletRequest request = getRequest();
		request.setContentType("multipart/form-data");
		RequestParser requestParser = requestParserSelector.select(request);
		assertTrue(requestParser instanceof MultipartRequestParserImpl);
	}

	public void testSelect4() {
		MockHttpServletRequest request = getRequest();
		request.setContentType("application/atom+xml");
		RequestParser requestParser = requestParserSelector.select(request);
		assertTrue(requestParser instanceof DefaultRequestParserImpl);
	}

	public void testSelect5() {
		MockHttpServletRequest request = getRequest();
		request.setContentType("foo/bar");
		RequestParser requestParser = requestParserSelector.select(request);
		assertTrue(requestParser instanceof MyRequestParserImpl);
	}

	public static class MyRequestParserImpl implements RequestParser {

		private int priority;

		public Map<String, Object[]> getParameterMap(HttpServletRequest request) {
			return null;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
		}

		public boolean isParsable(HttpServletRequest request) {
			return request.getContentType() != null
					&& request.getContentType().startsWith("foo/");
		}

	}
}
