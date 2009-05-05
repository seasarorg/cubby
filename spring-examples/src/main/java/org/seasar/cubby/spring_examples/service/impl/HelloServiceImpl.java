package org.seasar.cubby.spring_examples.service.impl;

import org.seasar.cubby.spring_examples.service.HelloService;

public class HelloServiceImpl implements HelloService {

	public String getMessage() {
		return "Hello!";
	}

}