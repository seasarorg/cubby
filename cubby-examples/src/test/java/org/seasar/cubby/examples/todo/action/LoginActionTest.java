package org.seasar.cubby.examples.todo.action;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.unit.CubbyTestCase;

public class LoginActionTest extends CubbyTestCase {

	private LoginAction action;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
	
	public void testIndex() throws Exception {
		assertPathEquals(Forward.class, "/todo/login.jsp", processAction("/todo/login/"));
	}

	public void testLogin() throws Exception {
		getRequest().addParameter("userId", "test");
		getRequest().addParameter("password", "test");
		assertPathEquals(Redirect.class, "/todo/", processAction("/todo/login/process"));
		assertNotNull(action.sessionScope.get("user"));
	}

	public void testLogin_authError() throws Exception {
		getRequest().addParameter("userId", "test");
		getRequest().addParameter("password", "dummy");
		assertPathEquals(Forward.class, "/todo/login.jsp", processAction("/todo/login/process"));
		assertEquals(0, action.getErrors().getOthers().size());
		assertEquals(2, action.getErrors().getFields().size());
		assertEquals(1, action.getErrors().getFields().get("userId").size());
		assertEquals(1, action.getErrors().getFields().get("password").size());
		assertEquals("ユーザIDかパスワードが違います。", action.getErrors().getFields().get("userId").get(0));
		assertEquals("ユーザIDかパスワードが違います。", action.getErrors().getFields().get("password").get(0));
		assertNull(action.sessionScope.get("user"));
	}

	public void testLogin_validationError() throws Exception {
		assertPathEquals(Forward.class, "/todo/login.jsp", processAction("/todo/login/process"));
		assertEquals(0, action.getErrors().getOthers().size());
		assertEquals(2, action.getErrors().getFields().size());
		assertEquals(1, action.getErrors().getFields().get("userId").size());
		assertEquals(1, action.getErrors().getFields().get("password").size());
		assertEquals("ユーザ名は必須です。", action.getErrors().getFields().get("userId").get(0));
		assertEquals("パスワードは必須です。", action.getErrors().getFields().get("password").get(0));
		assertNull(action.sessionScope.get("user"));
	}
}
