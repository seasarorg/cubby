/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.util;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("/")
public class MockAction extends Action {
	
	public ValidationRules validation = new DefaultValidationRules();
	
	public String attr1;
	public String attr2;
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
	
	@Validation(errorPage="error.jsp", rules="validation1")
	@Form
	public ActionResult dummy1() {
		return new Forward("dummy1.jsp");
	}

	public ActionResult dummy2() {
		return new Redirect("dummy2");
	}

	@Validation(errorPage="error.jsp", rules="validation2")
	@Form
	public ActionResult dummy3() {
		return new Forward("dummy1.jsp");
	}

	@Form
	public ActionResult index() {
		return new Forward("dummy1.jsp");
	}
	
	@Path("todo/lists")
	public ActionResult todolist() {
		return new Forward("dummy1.jsp");
	}

	@Path("/tasklists")
	public ActionResult tasklist() {
		return new Forward("dummy1.jsp");
	}
}
