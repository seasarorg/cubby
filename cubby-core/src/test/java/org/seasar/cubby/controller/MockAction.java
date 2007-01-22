package org.seasar.cubby.controller;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Session;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.Validators;

public class MockAction extends Action {
	
	public static final Validators VALIDATORS = new Validators();
	
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
	@Form
	public ActionResult dummy1() {
		return new Forward("dummy1.jsp");
	}

	public ActionResult dummy2() {
		return new Redirect("dummy2");
	}

	@Validation(errorPage="error.jsp", validator="VALIDATOR2")
	@Form
	public ActionResult dummy3() {
		return new Forward("dummy1.jsp");
	}
}
