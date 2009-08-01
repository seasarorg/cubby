package org.seasar.cubby.spring_examples.action;

import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@ActionClass
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Path("/")
public class IndexAction {

	public ActionResult index() {
		return new Forward("index.jsp");
	}
}
