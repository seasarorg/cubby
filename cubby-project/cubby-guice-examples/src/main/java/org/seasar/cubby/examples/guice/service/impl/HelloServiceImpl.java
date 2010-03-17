package org.seasar.cubby.examples.guice.service.impl;

import org.seasar.cubby.examples.guice.service.HelloService;

public class HelloServiceImpl implements HelloService {

	public String getMessage() {
		return "Hello!";
	}

}