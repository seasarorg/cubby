package org.seasar.cubby.examples.smartdeploy.dao;

import java.util.List;

import org.seasar.cubby.examples.smartdeploy.dto.TodoConditionDto;
import org.seasar.cubby.examples.smartdeploy.entity.Todo;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = Todo.class)
public interface TodoDao {

	int insert(Todo todo);

	int update(Todo todo);

	int delete(Todo todo);

	List<Todo> selectAll();

	@Arguments("id")
	Todo selectById(Integer id);

	@Arguments("condition")
	@Query(
			"1 = 1 " +
			"/*IF condition.hasTypeId()*/" +
			"AND typeid = /*condition.typeId*/1 " +
			"/*END*/ " +
			"/*IF condition.hasKeyword()*/" +
			"AND text like /*condition.wildcardKeyword*/'%type%' " +
			"AND memo like /*condition.wildcardKeyword*/'%type%'" +
			"/*END*/")
	List<Todo> selectByCondition(TodoConditionDto TodoConditionDto);

}
