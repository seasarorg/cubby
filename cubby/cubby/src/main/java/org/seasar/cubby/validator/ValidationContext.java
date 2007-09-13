package org.seasar.cubby.validator;

import java.util.Map;

import org.seasar.cubby.action.FormatPattern;

public class ValidationContext {

	private final String name;

	private final Object value;

	private final Map<String, Object> params;

	private final FormatPattern formatPattern;

	public ValidationContext(final String name, final Object value,
			final Map<String, Object> params, final FormatPattern formatPattern) {
		this.name = name;
		this.value = value;
		this.params = params;
		this.formatPattern = formatPattern;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public FormatPattern getFormatPattern() {
		return formatPattern;
	}

}
