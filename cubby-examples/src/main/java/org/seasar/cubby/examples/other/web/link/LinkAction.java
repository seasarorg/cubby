package org.seasar.cubby.examples.other.web.link;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

public class LinkAction extends Action {

	public Integer id;

	public String token;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	public ActionResult foo() {
		return new Forward("index.jsp");
	}

	@Path("bar/{id,[0-9]+}")
	public ActionResult bar() {
		return new Forward("index.jsp");
	}

}
