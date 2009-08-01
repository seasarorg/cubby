package org.seasar.cubby.spring_examples.service.impl;

import org.seasar.cubby.spring_examples.service.HelloService;
import org.springframework.stereotype.Component;

@Component
public class HelloServiceImpl implements HelloService {

	public String getMessage() {
		return "Hello!";
	}

}