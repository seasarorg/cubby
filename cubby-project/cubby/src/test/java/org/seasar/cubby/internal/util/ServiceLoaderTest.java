package org.seasar.cubby.internal.util;

import java.util.Iterator;

import org.junit.Test;
import org.seasar.cubby.internal.util.ServiceLoader;
import org.seasar.cubby.internal.util.ServiceLoadingException;

import static org.junit.Assert.*;

public class ServiceLoaderTest {

	@Test
	public void load() {
		ServiceLoader<FooProvider> serviceLoader = ServiceLoader
				.load(FooProvider.class);
		Iterator<FooProvider> iterator = serviceLoader.iterator();

		assertTrue(iterator.hasNext());
		FooProvider service1 = iterator.next();
		assertEquals(FooProviderImpl1.class, service1.getClass());

		assertTrue(iterator.hasNext());
		FooProvider service2 = iterator.next();
		assertEquals(FooProviderImpl2.class, service2.getClass());

		assertFalse(iterator.hasNext());
	}

	@Test
	public void load1() {
		ServiceLoader<FooProvider> serviceLoader = ServiceLoader
				.load(FooProvider.class);
		try {
			FooProvider provider = serviceLoader.getProvider();
			System.out.println(provider);
			fail();
		} catch (ServiceLoadingException e) {
			e.printStackTrace();
			assertTrue(true);
		}
	}
}
