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

import static org.seasar.cubby.util.ActionUtils.errors;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.examples.H2ScriptRunner;
import org.seasar.cubby.examples.ScriptRunner;
import org.seasar.cubby.plugins.s2.unit.CubbyTestCase;

public class LoginActionTest extends CubbyTestCase {

	private LoginAction action;

	protected void setUp() throws Exception {
		super.setUp();
		include("app.dicon");
		ScriptRunner scriptRunner = new H2ScriptRunner();
		scriptRunner.execute("ddl.sql");
	}

	public void testIndex() throws Exception {
		assertPathEquals(Forward.class, "/todo/login.jsp",
				processAction("/todo/login/"));
	}

	public void setUpLogin() {
		getRequest().addParameter("userId", "test");
		getRequest().addParameter("password", "test");
	}

	public void testLogin() throws Exception {
		assertPathEquals(Redirect.class, "/todo/",
				processAction("/todo/login/process"));
		assertNotNull(action.sessionScope.get("user"));
	}

	public void setUpLogin_authError() {
		getRequest().addParameter("userId", "test");
		getRequest().addParameter("password", "dummy");
	}

	public void testLogin_authError() throws Exception {
		assertPathEquals(Forward.class, "/todo/login.jsp",
				processAction("/todo/login/process"));
		ActionErrors errors = errors(getRequest());
		assertEquals(0, errors.getOthers().size());
		assertEquals(2, errors.getFields().size());
		assertEquals(1, errors.getFields().get("userId").size());
		assertEquals(1, errors.getFields().get("password").size());
		assertEquals("ユーザIDかパスワードが違います。", errors.getFields().get("userId").get(
				0));
		assertEquals("ユーザIDかパスワードが違います。", errors.getFields().get("password")
				.get(0));
		assertNull(action.sessionScope.get("user"));
	}

	public void testLogin_validationError() throws Exception {
		assertPathEquals(Forward.class, "/todo/login.jsp",
				processAction("/todo/login/process"));
		ActionErrors errors = errors(getRequest());
		assertEquals(0, errors.getOthers().size());
		assertEquals(2, errors.getFields().size());
		assertEquals(1, errors.getFields().get("userId").size());
		assertEquals(1, errors.getFields().get("password").size());
		assertEquals("ユーザ名は必須です。", errors.getFields().get("userId").get(0));
		assertEquals("パスワードは必須です。", errors.getFields().get("password").get(0));
		assertNull(action.sessionScope.get("user"));
	}
}
