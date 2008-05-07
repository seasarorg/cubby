package ${package}.service.impl;

import junit.framework.TestCase;

import ${package}.service.impl.HelloServiceImpl;

public class HelloServiceImplTest extends TestCase {
	public void testSayHello() {
		HelloServiceImpl hello = new HelloServiceImpl();
		assertEquals("Hello!", hello.getMessage());
	}
}