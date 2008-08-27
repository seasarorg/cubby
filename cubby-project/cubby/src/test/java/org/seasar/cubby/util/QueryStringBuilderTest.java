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
		
	public void testToStringWithBaseUrl() throws Exception {		
		QueryStringBuilder query = new QueryStringBuilder("basePath");
		query.addParam("p1", "v1");
		query.addParam("p2", null);
		query.addParam("p3", new String[] {"v2", "v3"});
		assertEquals("basePath?p1=v1&p2=&p3=v2&p3=v3", query.toString());
	}

	public void testToStringWithBaseUrlAndParams() throws Exception {		
		QueryStringBuilder query = new QueryStringBuilder("basePath?key1=1");
		query.addParam("p1", "v1");
		query.addParam("p2", null);
		query.addParam("p3", new String[] {"v2", "v3"});
		assertEquals("basePath?key1=1&p1=v1&p2=&p3=v2&p3=v3", query.toString());
	}
}
