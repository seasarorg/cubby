package org.seasar.cubby.examples.todo.entity;

import java.io.Serializable;
import java.util.Date;

import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;
import org.seasar.dao.annotation.tiger.Relation;

public class Todo implements Serializable {

	private static final long serialVersionUID = 1976887708993721192L;

	private Integer id;

	private String text;

	private String memo;

	private Integer typeId;

	private Date limitDate;

	private TodoType todoType;

	@Id(IdType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	@Relation(relationNo = 0, relationKey = "typeid:id")
	public TodoType getTodoType() {
		return todoType;
	}

	public void setTodoType(TodoType type) {
		this.todoType = type;
	}

}