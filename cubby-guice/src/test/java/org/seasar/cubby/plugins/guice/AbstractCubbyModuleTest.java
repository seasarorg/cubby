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
package org.seasar.cubby.plugins.guice;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;

import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.IAnswer;
import org.junit.Test;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.ProviderFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class AbstractCubbyModuleTest {

	@Test
	public void configure() throws Exception {
		HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		ServletContext servletContext = createNiceMock(ServletContext.class);
		expect(servletContext.getInitParameter("cubby.guice.module"))
				.andStubReturn(TestModule.class.getName());
		final Hashtable<String, Object> attributes = new Hashtable<String, Object>();
		expect(servletContext.getAttribute((String) anyObject()))
				.andStubAnswer(new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		servletContext.setAttribute((String) anyObject(), anyObject());
		expectLastCall().andAnswer(new IAnswer<Void>() {

			public Void answer() throws Throwable {
				attributes.put((String) getCurrentArguments()[0],
						getCurrentArguments()[1]);
				return null;
			}

		});
		replay(servletContext);

		ServletContextEvent event = new ServletContextEvent(servletContext);
		PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		GuicePlugin guicePlugin = new GuicePlugin();
		guicePlugin.contextInitialized(event);
		pluginRegistry.register(guicePlugin);

		Injector injector = Guice.createInjector(new TestModule());
		System.out.println(injector);
		Foo foo = injector.getInstance(Foo.class);
		System.out.println(foo.pathResolver);
		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				ConverterProvider converterProvider = ProviderFactory
						.get(ConverterProvider.class);
				System.out.println(converterProvider);
				return null;
			}

		});
	}

	public static class TestModule extends AbstractCubbyModule {

		@Override
		protected PathResolver getPathResolver() {
			return new PathResolverImpl(new PathTemplateParserImpl());
		}

	}

	public static class Foo {
		@Inject
		public PathResolver pathResolver;
	}

}
