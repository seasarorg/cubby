package org.seasar.cubby.examples.guice.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.seasar.cubby.guice_examples.service.impl.HelloServiceImpl;

public class HelloServiceImplTest {

	@Test
	public void sayHello() {
		HelloServiceImpl hello = new HelloServiceImpl();
		assertEquals("Hello!", hello.getMessage());
	}

}
