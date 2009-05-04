package mock.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;

public class MockAction1 extends Action {

	public ActionResult index() throws Exception {
		return new Forward("index.jsp");
	}

}
