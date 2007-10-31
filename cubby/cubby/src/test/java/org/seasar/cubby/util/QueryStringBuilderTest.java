package org.seasar.cubby.util;

import junit.framework.TestCase;

public class QueryStringBuilderTest extends TestCase {

	public void testToString() throws Exception {		
		QueryStringBuilder query = new QueryStringBuilder();
		query.addParam("p1", "v1");
		query.addParam("p2", null);
		query.addParam("p3", new String[] {"v2", "v3"});
		assertEquals("p1=v1&p2=&p3=v2&p3=v3", query.toString());
	}
		
}
