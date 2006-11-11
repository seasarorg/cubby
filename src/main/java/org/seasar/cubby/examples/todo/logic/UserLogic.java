package org.seasar.cubby.examples.todo.logic;

import java.util.List;

import org.seasar.cubby.examples.todo.entity.User;

public interface UserLogic {

	List<User> findAll();

	User findById(Integer id);

}
