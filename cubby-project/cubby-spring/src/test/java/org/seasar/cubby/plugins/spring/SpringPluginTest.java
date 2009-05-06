package org.seasar.cubby.plugins.spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugins.spring.spi.SpringContainerProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.Provider;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;

/**
 * {@link SpringPlugin} のテストです
 * 
 * @author someda
 */
public class SpringPluginTest {

	private Plugin plugin;

	@Before
	public void before() throws Exception {
		plugin = new SpringPlugin();
		MockServletContext context = new MockServletContext();
		context.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
				"/applicationContext.xml");
		ContextLoader contextLoader = new ContextLoader();
		contextLoader.initWebApplicationContext(context);
		plugin.initialize(context);
	}

	/**
	 * サポートしているプロバイダの場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void getProvider1() throws Exception {
		ContainerProvider actual = plugin.getProvider(ContainerProvider.class);
		assertEquals(SpringContainerProvider.class, actual.getClass());
	}

	/**
	 * サポートしていないプロバイダの場合
	 * 
	 * @throws Exception
	 */
	@Test
	public void getProvider2() throws Exception {
		MockProvider actual = plugin.getProvider(MockProvider.class);
		assertNull(actual);
	}

	private static interface MockProvider extends Provider {
	}

}
