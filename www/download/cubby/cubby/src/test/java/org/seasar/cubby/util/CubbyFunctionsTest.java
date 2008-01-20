package org.seasar.cubby.util;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class CubbyFunctionsTest extends TestCase {

	public void testContains() {
		List<String> action = new ArrayList<String>();
		action.add("unvalidate");
		action.add("validateRecord");
		action.add("branch");
		assertFalse(CubbyFunctions.contains(action, "validate"));
		action.add("validate");
		assertTrue(CubbyFunctions.contains(action, "validate"));
	}

}
