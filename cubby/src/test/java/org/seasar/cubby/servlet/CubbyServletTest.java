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

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.Test;
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

	private class AssertPlugin extends AbstractPlugin {

		public AssertPlugin() {
			support(AssertProvider.class);
		}

		@Override
		public void initialize(ServletConfig config) {
			initialized = true;
		}

		@Override
		public void ready() {
			ready = true;
		}

		@Override
		public void destroy() {
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
