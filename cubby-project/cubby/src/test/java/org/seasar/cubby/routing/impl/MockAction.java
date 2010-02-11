/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.routing.impl;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;

public class MockAction extends Action {

	@Path("/")
	public ActionResult index1() {
		return null;
	}

	public ActionResult update() {
		return null;
	}

	@Path("create")
	public ActionResult insert() {
		return null;
	}

	@Path("delete/{value,[0-9]+}")
	public ActionResult delete() {
		return null;
	}

	@Path("{name}")
	public ActionResult name() {
		return null;
	}

	@Path("update")
	@Accept(RequestMethod.PUT)
	public ActionResult update2() {
		// メソッドがPUTなのでエラーにならない
		return null;
	}

}
