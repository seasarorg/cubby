package org.seasar.cubby.util;

import junit.framework.TestCase;

public class UriTest extends TestCase {

	public void testGetQueryString() throws Exception {
		
		Uri uri = new Uri();
		uri.setPath("path1/path2");
		uri.setParam("p1", "v1");
		uri.setParam("p2", null);
		uri.setParam("p3", new String[] {"v2", "v3"});
		assertEquals("p1=v1&p2=&p3=v2&p3=v3", uri.getQueryString());
		
	}
	
	
}
