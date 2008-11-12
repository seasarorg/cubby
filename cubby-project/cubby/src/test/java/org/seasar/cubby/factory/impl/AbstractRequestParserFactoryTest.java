package org.seasar.cubby.factory.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParserImpl;
import org.seasar.cubby.factory.RequestParserFactory;

public class AbstractRequestParserFactoryTest {

	private RequestParserFactory factory = new AbstractRequestParserFactory() {

		private List<RequestParser> parsers = Arrays
				.asList(new RequestParser[] { new DefaultRequestParserImpl(),
						new MyRequestParserImpl() });

		@Override
		protected Collection<RequestParser> getRequestParsers() {
			return parsers;
		}

	};

	@Test
	public void testSelect1() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn(null).anyTimes();
		replay(request);
		RequestParser requestParser = factory.getRequestParser(request);
		assertTrue(requestParser instanceof DefaultRequestParserImpl);
	}

	@Test
	public void testSelect2() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn(
				"application/x-www-form-urlencoded").anyTimes();
		replay(request);
		RequestParser requestParser = factory.getRequestParser(request);
		assertTrue(requestParser instanceof DefaultRequestParserImpl);
	}

	// public void testSelect3() {
	// MockHttpServletRequest request = getRequest();
	// request.setContentType("multipart/form-data");
	// RequestParser requestParser = requestParserSelector.select(request);
	// assertTrue(requestParser instanceof MultipartRequestParserImpl);
	// }

	@Test
	public void testSelect4() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn("application/atom+xml")
				.anyTimes();
		replay(request);
		RequestParser requestParser = factory.getRequestParser(request);
		assertTrue(requestParser instanceof DefaultRequestParserImpl);
	}

	@Test
	public void testSelect5() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getContentType()).andReturn("foo/bar").anyTimes();
		replay(request);
		RequestParser requestParser = factory.getRequestParser(request);
		assertTrue(requestParser instanceof MyRequestParserImpl);
	}

	private static class MyRequestParserImpl implements RequestParser {

		public Map<String, Object[]> getParameterMap(HttpServletRequest request) {
			return null;
		}

		public int getPriority() {
			return 1;
		}

		public boolean isParsable(HttpServletRequest request) {
			String contentType = request.getContentType();
			return contentType != null && contentType.startsWith("foo/");
		}

	}
}
