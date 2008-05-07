package ${package}.action;

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
		assertPathEquals(Forward.class, "index.jsp", result);
	}
	
	public void testMessage() throws Exception {
		getRequest().addParameter("name", "name1");
		ActionResult result = processAction("/hello/message");
		assertPathEquals(Forward.class, "hello.jsp", result);
		assertEquals("name1", action.name);
		assertEquals("name1 Hello!", action.message);
	}
	
	public void testMessage_validationError() throws Exception {
		ActionResult result = processAction("/hello/message");
		assertPathEquals(Forward.class, "input.jsp", result);
		assertEquals("name1", action.name);
		assertEquals(0, action.getErrors().getOthers().size());
		assertEquals(1, action.getErrors().getFields().size());
		assertEquals(1, action.getErrors().getFields().get("name").size());
		assertEquals("あなたの名前は必須です。", action.getErrors().getFields().get("name").get(0));
		assertNull(action.name);
		assertNull(action.message);
	}
}
