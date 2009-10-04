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
