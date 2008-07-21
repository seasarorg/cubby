package org.seasar.cubby.tags;

import java.util.Map;

public class MockFormTag extends FormTag {

	private static final long serialVersionUID = 1L;

	private final Map<String, String[]> outputValues;

	public MockFormTag(final Map<String, String[]> outputValues) {
		super();
		this.outputValues = outputValues;
	}

	@Override
	public String[] getValues(String name) {
		return outputValues.get(name);
	}

}
