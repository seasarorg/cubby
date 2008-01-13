package org.seasar.cubby.examples.other.web.importTag;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;

public class ImportAction extends Action {

	public ActionResult index() {
		return new Forward("import.jsp");
	}
	
}
