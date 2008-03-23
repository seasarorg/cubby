package org.seasar.cubby.examples.other.web;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

/**
 * Indexのサンプル
 * @author agata
 */
@Path("/")
public class IndexAction extends Action {

	public ActionResult index() {
		return new Forward("index.jsp");
	}
}
