package org.seasar.cubby.examples.smartdeploy.dto;

import java.io.Serializable;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;

@Component(instance = InstanceType.SESSION)
public class TodoCondiitonDto implements Serializable {

	private static final long serialVersionUID = 1573725383333853270L;

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

	public String getWildcardKeyword() {
		return '%' + keyword + '%';
	}

	public boolean hasTypeId() {
		return typeId != null;
	}

	public boolean hasKeyword() {
		return keyword != null && !"".equals(keyword);
	}

}