package org.seasar.cubby.guice_examples.service.impl;

import org.seasar.cubby.guice_examples.service.HelloService;

public class HelloServiceImpl implements HelloService {

	public String getMessage() {
		return "Hello!";
	}

}