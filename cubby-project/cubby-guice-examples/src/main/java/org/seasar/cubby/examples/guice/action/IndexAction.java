package org.seasar.cubby.examples.guice.action;

import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
@ActionClass
@Path("/")
public class IndexAction {

	public ActionResult index() {
		return new Forward("index.jsp");
	}

}