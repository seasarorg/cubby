package org.seasar.cubby.servlet;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.junit.Test;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.spi.Provider;

public class CubbyServletContextListenerTest {

	private boolean initialized = false;

	private boolean destroyed = false;

	@Test
	public void initialize() {
		ServletContext servletContext = createMock(ServletContext.class);
		replay(servletContext);

		ServletContextListener servletContextListener = new CubbyServletContextListener() {

			@Override
			protected Collection<Plugin> loadPlugins() {
				return Arrays.asList(new Plugin[] { new AssertPlugin() });
			}

		};

		PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		ServletContextEvent event = new ServletContextEvent(servletContext);

		assertNull(pluginRegistry.getPlugin(AssertPlugin.class));
		try {
			pluginRegistry.getProvider(AssertProvider.class);
			fail();
		} catch (IllegalArgumentException e) {
			// ok
		}

		assertFalse(initialized);
		servletContextListener.contextInitialized(event);
		assertTrue(initialized);

		assertNotNull(pluginRegistry.getPlugin(AssertPlugin.class));
		assertNotNull(pluginRegistry.getProvider(AssertProvider.class));

		assertFalse(destroyed);
		servletContextListener.contextDestroyed(event);
		assertTrue(destroyed);

		verify(servletContext);
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

		public void contextInitialized(ServletContextEvent event) {
			initialized = true;
		}

		public void contextDestroyed(ServletContextEvent event) {
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
