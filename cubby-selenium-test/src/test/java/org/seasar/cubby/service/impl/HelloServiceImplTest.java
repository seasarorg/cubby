package org.seasar.cubby.service.impl;

import junit.framework.TestCase;

import org.seasar.cubby.service.impl.HelloServiceImpl;

public class HelloServiceImplTest extends TestCase {
	public void testSayHello() {
		HelloServiceImpl hello = new HelloServiceImpl();
		assertEquals("Hello!", hello.getMessage());
	}
}