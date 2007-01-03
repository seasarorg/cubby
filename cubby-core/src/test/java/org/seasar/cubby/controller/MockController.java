package org.seasar.cubby.controller;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Session;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.controller.results.Redirect;

public class MockController extends Controller {
	
	public String attr1;
	public String attr2;
	@Session
	public String attr3;
	public boolean executedInitalizeMethod = false;
	public boolean executedPrerenderMethod = false;
	
	@Override
	public void initialize() {
		super.initialize();
		executedInitalizeMethod = true;
	}
	
	@Override
	public void prerender() {
		super.prerender();
		executedPrerenderMethod =  true;
	}
	
	@Validation(errorPage="error.jsp")
	@Form("this")
	public ActionResult dummy1() {
		return new Forward("dummy1.jsp");
	}

	public ActionResult dummy2() {
		return new Redirect("dummy2");
	}
}
