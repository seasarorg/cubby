package org.seasar.cubby.examples.other.web.hello;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.unit.CubbyTestCase;

public class HelloActionTest extends CubbyTestCase {
	
	private HelloAction action;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
    
	public void testIndex() throws Exception {
		Forward result = (Forward) action.index();
		assertEquals("input.jsp", result.getPath());
	}
	
	public void testMessage() throws Exception {
		action.name = "name1";
		Forward result = (Forward) action.message();
		assertEquals("result.jsp", result.getPath());
		assertEquals("name1", action.name);
	}
	
}
