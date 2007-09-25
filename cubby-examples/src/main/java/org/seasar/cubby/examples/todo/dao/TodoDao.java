package org.seasar.cubby.examples.todo.dao;

import java.util.List;

import org.seasar.cubby.examples.todo.dto.TodoConditionDto;
import org.seasar.cubby.examples.todo.entity.Todo;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Query;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = Todo.class)
public interface TodoDao {

	int insert(Todo todo);

	int update(Todo todo);

	int delete(Todo todo);

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
			"/*END*/" +
			"/*IF condition.hasLimitDate()*/" +
			"AND limitdate <= /*condition.limitDate*/'2007-08-05'" +
			"/*END*/")
	List<Todo> selectByCondition(TodoConditionDto TodoConditionDto);

}
