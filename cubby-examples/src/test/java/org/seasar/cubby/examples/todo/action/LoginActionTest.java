package org.seasar.cubby.examples.todo.action;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.impl.ActionDefImpl;
import org.seasar.cubby.unit.CubbyTestCase;

public class LoginActionTest extends CubbyTestCase {

	private LoginAction action;
	private ActionContext actionContext;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
	
	public void testIndex() throws Exception {
		actionContext.initialize(new ActionDefImpl(getComponentDef(LoginAction.class), LoginAction.class.getMethod("index", new Class[0])));
		assertPathEquals(Forward.class, "/todo/login.jsp", action.index());
	}

	public void testLogin() throws Exception {
		actionContext.initialize(new ActionDefImpl(getComponentDef(LoginAction.class), LoginAction.class.getMethod("process", new Class[0])));
		assertPathEquals(Forward.class, "/todo/login.jsp", action.process());
		assertEquals(2, action.getErrors().getFields().size());
		assertTrue(action.getErrors().getFields().containsKey("userId"));
		assertTrue(action.getErrors().getFields().containsKey("password"));
	}
}
