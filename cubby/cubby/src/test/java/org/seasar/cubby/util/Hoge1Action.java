package org.seasar.cubby.util;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

@Path("hoge")
public class Hoge1Action extends Action {
	public ActionResult m1() {
		return null;
	}
	@Path("m/m2")
	public ActionResult m2() {
		return null;
	}
	public ActionResult index() {
		return null;
	}
	public ActionResult index2() {
		return null;
	}

}
