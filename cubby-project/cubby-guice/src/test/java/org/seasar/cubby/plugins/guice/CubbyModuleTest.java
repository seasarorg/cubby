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
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.easymock.IAnswer;
import org.junit.Test;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.converter.impl.BigDecimalConverter;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;

import com.google.inject.AbstractModule;
import com.google.inject.ConfigurationException;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.inject.util.Modules;

public class CubbyModuleTest {

	@Test
	public void configure() throws Exception {
		final HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		final HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		final ServletContext servletContext = createNiceMock(ServletContext.class);
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

		final CubbyGuiceServletContextListener cubbyGuiceServletContextListener = new CubbyGuiceServletContextListener();
		cubbyGuiceServletContextListener.contextInitialized(new ServletContextEvent(servletContext));

		final GuiceFilter guiceFilter = new GuiceFilter();
		guiceFilter.init(new FilterConfig() {

			public String getFilterName() {
				return "guice";
			}

			public String getInitParameter(final String name) {
				return null;
			}

			@SuppressWarnings("unchecked")
			public Enumeration getInitParameterNames() {
				return new Vector<String>().elements();
			}

			public ServletContext getServletContext() {
				return servletContext;
			}

		});

		final FilterChain chain = new FilterChain() {

			public void doFilter(final ServletRequest request,
					final ServletResponse response) throws IOException,
					ServletException {
				final PluginRegistry pluginRegistry = PluginRegistry
						.getInstance();
				final GuicePlugin guicePlugin = new GuicePlugin();
				try {
					guicePlugin.initialize(servletContext);
					guicePlugin.ready();
					pluginRegistry.register(guicePlugin);
				} catch (final Exception e) {
					throw new ServletException(e);
				}

				final Injector injector = guicePlugin.getInjector();
				System.out.println(injector);

				final PathResolverProvider pathResolverProvider = ProviderFactory
						.get(PathResolverProvider.class);
				final PathResolver pathResolver = pathResolverProvider
						.getPathResolver();
				System.out.println(pathResolver);

				final PathTemplateParser pathTemplateParser = injector
						.getInstance(PathTemplateParser.class);
				System.out.println(pathTemplateParser);
				assertTrue(pathTemplateParser instanceof MyPathTemplateParser);

				final ContainerProvider containerProvider = ProviderFactory
						.get(ContainerProvider.class);
				final Container container = containerProvider.getContainer();
				final Foo foo = container.lookup(Foo.class);
				System.out.println(foo);
				System.out.println(foo.pathResolver);

				assertSame(pathResolver, foo.pathResolver);

				try {
					final Baz baz = injector.getInstance(Baz.class);
					System.out.println(baz);
					fail();
				} catch (final ConfigurationException e) {
					// ok
				}
				try {
					ThreadContext.runInContext((HttpServletRequest) request,
							(HttpServletResponse) response, new Command() {

								public void execute(
										final HttpServletRequest request,
										final HttpServletResponse response)
										throws Exception {
									final ConverterProvider converterProvider = ProviderFactory
											.get(ConverterProvider.class);
									System.out.println(converterProvider);
									System.out
											.println(converterProvider
													.getConverter(BigDecimalConverter.class));

									final FileUpload fileUpload1 = injector
											.getInstance(FileUpload.class);
									System.out.println(fileUpload1);
									System.out.println(fileUpload1
											.getFileItemFactory());

									final FileUpload fileUpload2 = injector
											.getInstance(FileUpload.class);
									System.out.println(fileUpload2);
									System.out.println(fileUpload2
											.getFileItemFactory());

									assertNotSame(fileUpload1, fileUpload2);
									assertNotSame(fileUpload1
											.getFileItemFactory(), fileUpload2
											.getFileItemFactory());
								}

							});
				} catch (final Exception e) {
					throw new ServletException(e);
				}
			}

		};
		guiceFilter.doFilter(request, response, chain);
		cubbyGuiceServletContextListener.contextDestroyed(new ServletContextEvent(servletContext));
	}

	public static class TestModule extends AbstractModule {

		@Override
		protected void configure() {
			install(new ServletModule());
			// install(new AbstractCubbyModule());
			install(Modules.override(new CubbyModule(), new FileUploadModule())
					.with(new AbstractModule() {

						@Override
						protected void configure() {
							bind(PathTemplateParser.class).to(
									MyPathTemplateParser.class).in(
									Singleton.class);
						}

					}));
			bind(BarAction.class);

			// {
			// @Override
			// protected PathResolver getPathResolver() {
			// return new PathResolverImpl(new PathTemplateParserImpl());
			// }
			// });
		}

	}

	public static class MyPathTemplateParser implements PathTemplateParser {

		public String parse(final String template, final Handler handler) {
			return "mytemplateparser";
		}

	}

	public static class Foo {
		@Inject
		public PathResolver pathResolver;
	}

	@ActionClass
	@RequestScoped
	public static class BarAction {
		public ActionResult index() {
			return null;
		}
	}

	public interface Baz {

	}
}
