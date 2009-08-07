package org.seasar.cubby.showcase.todo.dao;

import org.seasar.cubby.showcase.todo.entity.User;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = User.class)
public interface UserDao {

	@Arguments( { "id", "password" })
	User findByIdAndPassword(String id, String password);

}
