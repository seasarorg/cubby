package org.seasar.cubby.examples.spring.service.impl;

import org.seasar.cubby.examples.spring.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HelloServiceImpl implements HelloService {

	public String getMessage() {
		return "Hello!";
	}

}