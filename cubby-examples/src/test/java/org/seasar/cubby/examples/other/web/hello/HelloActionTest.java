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
package org.seasar.cubby.examples.other.web.hello;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.unit.CubbyTestCase;

public class HelloActionTest extends CubbyTestCase {
	private HelloAction action;
	
    protected void setUp() throws Exception {
        include("app.dicon");
    }
    
	public void testIndex() throws Exception {
		ActionResult result = processAction("/hello/");
		assertPathEquals(Forward.class, "input.jsp", result);
	}
	
	public void testMessage() throws Exception {
		getRequest().addParameter("name", "name1");
		ActionResult result = processAction("/hello/message");
		assertPathEquals(Forward.class, "result.jsp", result);
		assertEquals("name1", action.name);
	}	
}
