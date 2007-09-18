package org.seasar.cubby.examples.todo.dto;

import java.io.Serializable;
import java.util.Date;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class TodoConditionDto implements Serializable {

	private static final long serialVersionUID = 1573725383333853270L;

	public String keyword;

	public Integer typeId;

	public Date limitDate;

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

	public Date getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(Date limitDate) {
		this.limitDate = limitDate;
	}

	public String getWildcardKeyword() {
		return '%' + keyword + '%';
	}

	public boolean hasTypeId() {
		return typeId != null;
	}

	public boolean hasKeyword() {
		return keyword != null && !"".equals(keyword);
	}

	public boolean hasLimitDate() {
		return limitDate != null;
	}
}