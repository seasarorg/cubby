package org.seasar.cubby.action;

import junit.framework.TestCase;

public class DirectTest extends TestCase {

	public void testExecute() throws Exception {
		Direct direct = new Direct();
		direct.prerender(null);
		direct.execute(null, null, null);
	}

}
