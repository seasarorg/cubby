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
package org.seasar.cubby.showcase.other.web.dispatch;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.OnSubmit;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;

public class DispatchAction extends Action {

	@RequestParameter
	public String button1;

	@RequestParameter
	public String button2;

	@RequestParameter
	public String message;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Path("execute")
	@OnSubmit("button1")
	public ActionResult method1() {
		this.message = "Invoke method1, @OnSubmit(\"button1\")";
		return new Forward("index.jsp");
	}

	@Path("execute")
	@OnSubmit("button2")
	public ActionResult method2() {
		this.message = "Invoke method2, @OnSubmit(\"button2\")";
		return new Forward("index.jsp");
	}

	@Path("execute")
	public ActionResult method3() {
		this.message = "Invoke method3, No @OnSubmit";
		return new Forward("index.jsp");
	}

}
