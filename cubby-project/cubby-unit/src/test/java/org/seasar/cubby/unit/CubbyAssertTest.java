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

package org.seasar.cubby.unit;

import static org.junit.Assert.fail;
import junit.framework.AssertionFailedError;

import org.junit.Test;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;

public class CubbyAssertTest {

	@Test
	public void forward() {
		ActionResult actionResult = new Forward("aaa/bbb");

		CubbyAssert.assertPathEquals(Forward.class, "aaa/bbb", actionResult);

		// not null
		try {
			CubbyAssert.assertPathEquals(Forward.class, "aaa/bbb", null);
			fail();
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
		}

		// type
		try {
			CubbyAssert.assertPathEquals(Redirect.class, "aaa/bbb", actionResult);
			fail();
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
		}

		// path
		try {
			CubbyAssert
					.assertPathEquals(Forward.class, "aaa/ccc", actionResult);
			fail();
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void redirect() {
		ActionResult actionResult = new Redirect("aaa/bbb");

		CubbyAssert.assertPathEquals(Redirect.class, "aaa/bbb", actionResult);

		// not null
		try {
			CubbyAssert.assertPathEquals(Redirect.class, "aaa/bbb", null);
			fail();
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
		}

		// type
		try {
			CubbyAssert.assertPathEquals(Forward.class, "aaa/bbb", actionResult);
			fail();
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
		}

		// path
		try {
			CubbyAssert
					.assertPathEquals(Redirect.class, "aaa/ccc", actionResult);
			fail();
		} catch (AssertionFailedError e) {
			System.out.println(e.getMessage());
		}
	}

}
