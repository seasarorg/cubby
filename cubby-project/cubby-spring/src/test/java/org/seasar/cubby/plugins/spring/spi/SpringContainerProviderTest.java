package org.seasar.cubby.plugins.spring.spi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/cubby.xml" })
public class SpringContainerProviderTest {

	@Autowired
	private ContainerProvider containerProvider;

	@Test
	public void getContainer1() throws Exception {
		Container container = containerProvider.getContainer();
		assertNotNull(container);
		BeanDescProvider actual = container.lookup(BeanDescProvider.class);
		assertEquals(DefaultBeanDescProvider.class, actual.getClass());
	}

}
