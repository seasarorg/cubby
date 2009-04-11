package org.seasar.cubby.servlet;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.Test;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.Provider;

public class CubbyServletTest {

	private boolean initialized = false;

	private boolean ready = false;

	private boolean destroyed = false;

	@Test
	public void initialize() throws ServletException {
		ServletConfig servletConfig = createMock(ServletConfig.class);
		replay(servletConfig);

		Servlet servlet = new CubbyServlet() {

			private static final long serialVersionUID = 1L;

			@Override
			protected Collection<Plugin> loadPlugins() {
				return Arrays.asList(new Plugin[] { new AssertPlugin() });
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

		verify(servletConfig);
	}

	private class AssertProvider implements Provider {

	}

	private static final Set<Class<? extends Provider>> SUPPORTED_SERVICES;
	static {
		final Set<Class<? extends Provider>> services = new HashSet<Class<? extends Provider>>();
		services.add(AssertProvider.class);
		SUPPORTED_SERVICES = Collections.unmodifiableSet(services);
	}

	private class AssertPlugin implements Plugin {

		public void initialize(ServletConfig config) {
			initialized = true;
		}

		public void ready() {
			ready = true;
		}

		public void destroy() {
			destroyed = true;
		}

		public <S extends Provider> S getProvider(Class<S> service) {
			if (AssertProvider.class.equals(service)) {
				return service.cast(new AssertProvider());
			}
			throw new IllegalArgumentException();
		}

		public Set<Class<? extends Provider>> getSupportedServices() {
			return SUPPORTED_SERVICES;
		}

	}

}
