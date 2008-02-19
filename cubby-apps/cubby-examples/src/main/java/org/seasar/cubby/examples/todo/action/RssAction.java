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
package org.seasar.cubby.examples.todo.action;

import java.util.Date;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Feed;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.examples.todo.dao.TodoDao;
import org.seasar.cubby.examples.todo.dto.TodoConditionDto;
import org.seasar.cubby.examples.todo.entity.Todo;

/**
 * RSS配信
 * @author agata
 */
@Path("todo")
public class RssAction extends Action {

	// ----------------------------------------------[Validation]

	// ----------------------------------------------[DI Filed]

	public TodoDao todoDao;
	
	// ----------------------------------------------[Attribute]

	public Integer typeId;
	
	// ----------------------------------------------[Action Method]
	/**
	 * RSS配信(/todo/rss)
	 * TODO 作業途中：TODOに作成日付を用意する必要あり。
	 */
	public ActionResult rss() throws Exception {
		TodoConditionDto condition = new TodoConditionDto();
		List<Todo> todoList = todoDao.selectByCondition(condition);
		Feed feed = Abdera.getNewFactory().newFeed();
		feed.setTitle("Cubby Examples Todo App");
		feed.setUpdated(new Date());
		return null;
	}

	/**
	 * RSS配信(カテゴリ別)(/todo/rss/{typeId})
	 * TODO 作業途中：TODOに作成日付を用意する必要あり。
	 */
	@Path("/rss/{typeId,0-9}")
	public ActionResult categoryRss() throws Exception {
		TodoConditionDto condition = new TodoConditionDto();
		condition.setTypeId(typeId);
		List<Todo> todoList = todoDao.selectByCondition(condition);
		Feed feed = Abdera.getNewFactory().newFeed();
		feed.setTitle("Cubby Examples Todo App");
		feed.setUpdated(new Date());
		return null;
	}

	// ----------------------------------------------[Helper Method]
}