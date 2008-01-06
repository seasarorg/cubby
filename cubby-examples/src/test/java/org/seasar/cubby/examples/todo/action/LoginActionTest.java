package org.seasar.cubby.examples.todo.action;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.unit.CubbyTestCase;

public class LoginActionTest extends CubbyTestCase {

	private LoginAction action;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
	
	public void testIndex() throws Exception {
		assertPathEquals(Forward.class, "/todo/login.jsp", action.index());
	}
}
