package org.seasar.cubby.seasar;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;

@Url("sample1")
public class Sample1Controller extends Action {
	
	public ActionResult detail() {
		return new Forward("detail.jsp");
	}

	
	@Url("todo/{id}") // -> /cubby/org.seasar.cubby.seasar.Sample1Controller/todo?id=1
	public ActionResult show() {
		return new Forward("show.jsp");
	}

	@Url("{name}/list") // -> /cubby/org.seasar.cubby.seasar.Sample1Controller/list?name=agata
	public ActionResult list() {
		return new Forward("show.jsp");
	}

}
