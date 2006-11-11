package org.seasar.cubby.examples.todo.dto;


public class FindTodoDto {
	public String keyword;
	public Integer typeId;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
}