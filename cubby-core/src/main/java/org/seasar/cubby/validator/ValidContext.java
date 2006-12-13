package org.seasar.cubby.validator;

import java.util.Map;

public class ValidContext {
	private String name;
	private Map params;
	
	public ValidContext(String name, Map params) {
		this.name = name;
		this.params = params;
	}
	
	public String getName() {
		return name;
	}

	public Map getParams() {
		return params;
	}
}
