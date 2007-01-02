package org.seasar.cubby.controller;

import org.seasar.cubby.annotation.Session;

public class MockController extends Controller {
	
	public String attr1;
	public String attr2;
	@Session
	public String attr3;
	public boolean executedInitalizeMethod = false;
	public boolean executedPrerenderMethod = false;
	
	@Override
	public void initalize() {
		super.initalize();
		executedInitalizeMethod = true;
	}
	
	@Override
	public void prerender() {
		super.prerender();
		executedPrerenderMethod =  true;
	}
	
	public ActionResult dummy1() {
		return null;
	}
}
