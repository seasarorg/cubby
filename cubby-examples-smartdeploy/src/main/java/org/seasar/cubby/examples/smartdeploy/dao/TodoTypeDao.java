package org.seasar.cubby.examples.smartdeploy.dao;

import java.util.List;

import org.seasar.cubby.examples.smartdeploy.entity.TodoType;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TodoType.class)
public interface TodoTypeDao {

	List<TodoType> seletAll();

	@Arguments("id")
	TodoType selectById(Integer id);

}
