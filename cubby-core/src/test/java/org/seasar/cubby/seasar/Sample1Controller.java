package org.seasar.cubby.seasar;

import org.seasar.cubby.annotation.Url;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;

@Url("sample1")
public class Sample1Controller extends Controller {
	
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
