package org.seasar.cubby.tags;

public class FormDto {
	private String stringField;
	private Integer integerField;
	private Integer[] integerArrayField;
	private Boolean booleanField;
	public String getStringField() {
		return stringField;
	}
	public void setStringField(String stringField) {
		this.stringField = stringField;
	}
	public Integer getIntegerField() {
		return integerField;
	}
	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}
	public Boolean getBooleanField() {
		return booleanField;
	}
	public void setBooleanField(Boolean booleanField) {
		this.booleanField = booleanField;
	}
	public Integer[] getIntegerArrayField() {
		return integerArrayField.clone();
	}
	public void setIntegerArrayField(Integer[] integerArrayField) {
		this.integerArrayField = integerArrayField.clone();
	}
}
