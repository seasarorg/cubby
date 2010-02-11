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

import static org.seasar.cubby.action.RequestMethod.DELETE;
import static org.seasar.cubby.action.RequestMethod.PUT;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

@Path("/")
public class MockRootAction extends Action {

	public ActionResult index() {
		return null;
	}

	public ActionResult dummy1() {
		return null;
	}

	@Path("dummy1")
	@Accept({ PUT, DELETE })
	public ActionResult dummy2() {
		return null;
	}
}
