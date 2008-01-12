package org.seasar.cubby.examples.whiteboard.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

public class WhiteboardAction extends Action {

	public Integer id;

	@Form
	@Path("{id,[0-9]+}/")
	public ActionResult index() {
		return new Forward("whiteboard.jsp");
	}	
}
