package org.seasar.cubby.unit;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.mock.MockConverterProvider;
import org.seasar.cubby.mock.MockPathResolverProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;

public class CubbyRunnerFilterTest {

	private PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private final Hashtable<String, Object> attributes = new Hashtable<String, Object>();

	private final Map<String, String[]> parameterMap = new HashMap<String, String[]>();

	private MockAction mockAction = new MockAction();

	private static ActionResult expectActionResult = new ActionResult() {

		public void execute(ActionContext actionContext,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		}

	};

	@Before
	public void before() {
		PathResolver pathResolver = new PathResolverImpl(
				new PathTemplateParserImpl());
		pathResolver.add(MockAction.class);

		BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(RequestParserProvider.class).toInstance(
				new AbstractRequestParserProvider() {

					@Override
					protected Collection<RequestParser> getRequestParsers() {
						return Arrays
								.asList(new RequestParser[] { new DefaultRequestParser() });
					}

				});
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) throws LookupException {
						if (MockAction.class.equals(type)) {
							return type.cast(mockAction);
						}
						throw new LookupException("type:" + type);
					}

				}));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		binderPlugin.bind(ConverterProvider.class).toInstance(
				new MockConverterProvider());
		binderPlugin.bind(PathResolverProvider.class).toInstance(
				new MockPathResolverProvider(pathResolver));
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void after() {
		pluginRegistry.clear();
	}

	@Test
	public void processActionWithFilter() throws Exception {
		parameterMap.put("name", new String[] { "abcdefg" });

		HttpServletRequest request = createMock(HttpServletRequest.class);
		request.setAttribute(isA(String.class), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttribute(isA(String.class))).andStubAnswer(
				new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.removeAttribute(isA(String.class));
		expectLastCall().andStubAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.remove(getCurrentArguments()[0]);
				return null;
			}

		});
		expect(request.getServletPath()).andReturn("/mock/execute");
		expect(request.getPathInfo()).andReturn(null);
		expect(request.getParameterMap()).andReturn(parameterMap);
		expect(request.getRequestURI()).andReturn("/context/mock/execute");
		expect(request.getMethod()).andReturn("GET");
		expect(request.getCharacterEncoding()).andReturn("UTF-8");
		expect(request.getSession(false)).andReturn(null);
		HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		MockFilter filter = new MockFilter();
		ActionResult result = CubbyRunner.processAction(request, response,
				filter);
		assertSame(expectActionResult, result);
		assertEquals("abcdefg", mockAction.getName());

		verify(request, response);
	}

	public static class MockAction {
		private String name;

		@RequestParameter
		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public ActionResult execute() {
			HttpServletRequest request = ThreadContext.getRequest();
			assertTrue((Boolean) request.getAttribute("filtered"));
			return expectActionResult;
		}
	}

	public static class MockFilter implements Filter {

		public void init(FilterConfig filterConfig) throws ServletException {
		}

		public void destroy() {
		}

		public void doFilter(ServletRequest request, ServletResponse response,
				FilterChain chain) throws IOException, ServletException {
			request.setAttribute("filtered", true);
			chain.doFilter(request, response);
		}

	}

}
