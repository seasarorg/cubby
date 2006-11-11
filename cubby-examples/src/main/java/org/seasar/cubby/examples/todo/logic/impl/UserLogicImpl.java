package org.seasar.cubby.examples.todo.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.examples.todo.logic.UserLogic;


public class UserLogicImpl implements UserLogic {

	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < 10; i++) {
			users.add(new User(i, "User" + i));
		}
		return users;
	}

	public User findById(Integer id) {
		User user = new User(id, "User" + id);
		return user;
	}
}
