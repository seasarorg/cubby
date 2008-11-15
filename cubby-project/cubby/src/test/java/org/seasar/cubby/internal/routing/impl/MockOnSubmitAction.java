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
package org.seasar.cubby.internal.routing.impl;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.OnSubmit;
import org.seasar.cubby.action.Path;

public class MockOnSubmitAction {

	public ActionResult update() {
		return null;
	}

	@Path("execute")
	public ActionResult method1() {
		return null;
	}

	@OnSubmit("button1")
	@Path("execute")
	public ActionResult method2() {
		return null;
	}

	@OnSubmit("button2")
	@Path("execute")
	public ActionResult method3() {
		return null;
	}

}
