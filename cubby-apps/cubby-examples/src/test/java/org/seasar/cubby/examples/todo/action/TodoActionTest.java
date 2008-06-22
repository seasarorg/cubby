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

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.examples.H2ScriptRunner;
import org.seasar.cubby.examples.ScriptRunner;
import org.seasar.cubby.examples.todo.entity.User;
import org.seasar.cubby.unit.CubbyTestCase;

public class TodoActionTest extends CubbyTestCase {

	private TodoAction action;

	protected void setUp() throws Exception {
		include("app.dicon");
		ScriptRunner scriptRunner = new H2ScriptRunner();
		scriptRunner.execute("ddl.sql");
	}

	public void setUpShow() throws Exception {
		emulateLogin();
	}

	private void emulateLogin() throws Exception {
		User user = new User();
		user.setId("mock");
		user.setName("mock");
		user.setPassword("mock");
		getRequest().getSession().setAttribute("user", user);
	}

	public void testShow() throws Exception {
		this.readXlsAllReplaceDb("TodoActionTest_PREPARE.xls");
		// CoolURIの場合のテスト
		ActionResult result = processAction("/todo/1");
		assertPathEquals(Forward.class, "show.jsp", result);
		assertEquals(new Integer(1), action.id);
		assertEquals("todo1", action.text);
		assertEquals("todo1 memo", action.memo);
		assertEquals(new Integer(1), action.todoType.getId());
		assertEquals("type1", action.todoType.getName());
		assertEquals("2008-01-01", action.limitDate);
	}
}
