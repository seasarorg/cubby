package org.seasar.cubby.examples.other.web.hello;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.unit.CubbyTestCase;

public class HelloActionTest extends CubbyTestCase {
	private HelloAction action;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
    
	public void testIndex() throws Exception {
		ActionResult result = processAction("/hello/");
		assertPathEquals(Forward.class, "input.jsp", result);
	}
	
	public void testMessage() throws Exception {
		getRequest().addParameter("name", "name1");
		ActionResult result = processAction("/hello/message");
		assertPathEquals(Forward.class, "result.jsp", result);
		assertEquals("name1", action.name);
	}	
}
