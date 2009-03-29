package org.seasar.cubby.guice_examples.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HelloServiceImplTest {

	@Test
	public void sayHello() {
		HelloServiceImpl hello = new HelloServiceImpl();
		assertEquals("Hello!", hello.getMessage());
	}

}
