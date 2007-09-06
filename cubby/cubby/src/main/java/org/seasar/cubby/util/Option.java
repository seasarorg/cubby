package org.seasar.cubby.util;

public class Option {
	private Object value;
	private Object label;
	public Option(){}
	public Option(Object value) {
		this(value, value);
	}
	public Option(Object value, Object label) {
		this.value = value;
		this.label = label;
	}
	public Object getLabel() {
		return label;
	}
	public void setLabel(Object label) {
		this.label = label;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
