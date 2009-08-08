package org.seasar.cubby.exmaple.gae.action;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
@ActionClass
@Path("/")
public class IndexAction {

	@Accept(RequestMethod.GET)
	public ActionResult index() throws Exception {
		return new Forward("index.jsp");
	}

}
