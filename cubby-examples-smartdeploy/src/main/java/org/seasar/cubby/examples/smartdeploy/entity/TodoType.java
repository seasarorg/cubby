package org.seasar.cubby.examples.smartdeploy.entity;

import java.io.Serializable;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;

@Bean(table = "todotype")
public class TodoType implements Serializable {

	private static final long serialVersionUID = 7643426554577224817L;

	private Integer id;

	private String name;

	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
