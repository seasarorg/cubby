/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
			"AND (text like /*condition.wildcardKeyword*/'%type%' " +
			"OR memo like /*condition.wildcardKeyword*/'%type%')" +
			"/*END*/" +
			"/*IF condition.hasLimitDate()*/" +
			"AND limitdate <= /*condition.limitDate*/'2007-08-05'" +
			"/*END*/")
	List<Todo> selectByCondition(TodoConditionDto TodoConditionDto);

}
