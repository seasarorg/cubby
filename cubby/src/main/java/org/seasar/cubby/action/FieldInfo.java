package org.seasar.cubby.action;

public class FieldInfo {

	private final String name;

	private final Integer index;

	public FieldInfo(final String name) {
		this(name, null);
	}

	public FieldInfo(final String name, final Integer index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public Integer getIndex() {
		return index;
	}

}
