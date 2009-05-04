package org.seasar.cubby.plugins.spring.spi;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.spi.PathResolverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cubby.xml",
		"SpringPathResolverProviderTest.xml" })
public class SpringPathResolverProviderTest {

	@Autowired
	private PathResolverProvider pathResolverProvider;

	@Test
	public void getPathResolver1() throws Exception {
		PathResolver actual = pathResolverProvider.getPathResolver();
		assertEquals(PathResolverImpl.class, actual.getClass());

		Collection<Routing> routings = actual.getRoutings();
		assertEquals(4, routings.size());
	}

}
