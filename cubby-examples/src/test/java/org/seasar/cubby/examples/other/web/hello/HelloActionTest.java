package org.seasar.cubby.examples.other.web.hello;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.impl.ActionDefImpl;
import org.seasar.cubby.unit.CubbyTestCase;

public class HelloActionTest extends CubbyTestCase {
	
	private HelloAction action;
	private ActionContext actionContext;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
    
	public void testIndex() throws Exception {
		actionContext.initialize(new ActionDefImpl(getComponentDef(HelloAction.class), HelloAction.class.getMethod("index", new Class[0])));
		Forward result = (Forward) action.index();
		assertEquals("input.jsp", result.getPath());
	}
	
	public void testMessage() throws Exception {
		actionContext.initialize(new ActionDefImpl(getComponentDef(HelloAction.class), HelloAction.class.getMethod("message", new Class[0])));
		action.name = "name1";
		Forward result = (Forward) action.message();
		assertEquals("result.jsp", result.getPath());
		assertEquals("name1", action.name);
	}
	
}
