package org.seasar.cubby.examples.todo.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 4405198629442011216L;

	private Integer id;
	private String name;

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
