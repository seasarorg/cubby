package org.seasar.cubby.examples.whiteboard;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;

public class WhiteboardAction extends Action {
	
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return this.id;
	}
	
	@Form
	@Url("{id,[0-9]+}/")
	public ActionResult index() {
		return new Forward("whiteboard.jsp");
	}	
}
