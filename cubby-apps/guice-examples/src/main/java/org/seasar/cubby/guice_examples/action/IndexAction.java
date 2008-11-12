package org.seasar.cubby.guice_examples.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
@Path("/")
public class IndexAction extends Action {

	public ActionResult index() {
		return new Forward("index.jsp");
	}
}