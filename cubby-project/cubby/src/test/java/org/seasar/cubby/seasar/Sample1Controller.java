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
package org.seasar.cubby.seasar;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;

@Path("sample1")
public class Sample1Controller extends Action {
	
	public ActionResult detail() {
		return new Forward("detail.jsp");
	}

	
	@Path("todo/{id}") // -> /cubby/org.seasar.cubby.seasar.Sample1Controller/todo?id=1
	public ActionResult show() {
		return new Forward("show.jsp");
	}

	@Path("{name}/list") // -> /cubby/org.seasar.cubby.seasar.Sample1Controller/list?name=agata
	public ActionResult list() {
		return new Forward("show.jsp");
	}

}
