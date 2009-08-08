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
package org.seasar.cubby.servlet;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Test;
import org.seasar.cubby.internal.plugin.PluginManager;
import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.Provider;

public class CubbyServletTest {

	private boolean initialized = false;

	private boolean ready = false;

	private boolean destroyed = false;

	@Test
	public void initialize() throws ServletException {
		ServletContext servletContext = createMock(ServletContext.class);
		ServletConfig servletConfig = createMock(ServletConfig.class);
		expect(servletConfig.getServletContext()).andReturn(servletContext);
		replay(servletContext, servletConfig);

		Servlet servlet = new CubbyServlet() {

			private static final long serialVersionUID = 1L;

			@Override
			protected PluginManager buildPluginManager() {
				PluginManager pluginManager = new PluginManager(PluginRegistry
						.getInstance()) {

					@Override
					protected Collection<Plugin> loadPlugins() {
						return Arrays
								.asList(new Plugin[] { new AssertPlugin() });
					}

				};
				return pluginManager;
			}

		};

		PluginRegistry pluginRegistry = PluginRegistry.getInstance();

		assertNull(pluginRegistry.getPlugin(AssertPlugin.class));
		try {
			pluginRegistry.getProvider(AssertProvider.class);
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}

		assertFalse(initialized);
		assertFalse(ready);
		servlet.init(servletConfig);
		assertTrue(initialized);
		assertTrue(ready);

		assertNotNull(pluginRegistry.getPlugin(AssertPlugin.class));
		assertNotNull(pluginRegistry.getProvider(AssertProvider.class));

		assertFalse(destroyed);
		servlet.destroy();
		assertTrue(destroyed);

		verify(servletContext, servletConfig);
	}

	private class AssertProvider implements Provider {

	}

	private class AssertPlugin extends AbstractPlugin {

		public AssertPlugin() {
			support(AssertProvider.class);
		}

		@Override
		public void initialize(ServletContext servletContext) throws Exception {
			super.initialize(servletContext);
			initialized = true;
		}

		@Override
		public void ready() throws Exception {
			super.ready();
			ready = true;
		}

		@Override
		public void destroy() {
			super.destroy();
			destroyed = true;
		}

		public <S extends Provider> S getProvider(Class<S> service) {
			if (AssertProvider.class.equals(service)) {
				return service.cast(new AssertProvider());
			}
			return null;
		}

	}

}
