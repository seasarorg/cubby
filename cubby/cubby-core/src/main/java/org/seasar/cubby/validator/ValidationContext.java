package org.seasar.cubby.validator;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ValidationContext {
	private final String name;
	private final Object value;
	private final Map params;
	
	public ValidationContext(final String name, final Object value) {
		this(name, value, Collections.EMPTY_MAP);
	}

	public ValidationContext(final String name, final Object value, final Map params) {
		this.name = name;
		this.value = value;
		this.params = params;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public Map getParams() {
		return params;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
