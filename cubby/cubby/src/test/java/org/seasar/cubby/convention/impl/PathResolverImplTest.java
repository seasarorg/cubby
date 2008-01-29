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
package org.seasar.cubby.convention.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.seasar.cubby.convention.InternalForwardInfo;
import org.seasar.extension.unit.S2TestCase;

public class PathResolverImplTest extends S2TestCase {
	
	private PathResolverImpl resolver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}
	
	public void testRoot1() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/", "GET");
		assertNotNull(info);
		assertEquals("/mockRoot/index", info.getInternalForwardPath());
	}

	public void testRoot2() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/dummy1", "GET");
		assertNotNull(info);
		assertEquals("/mockRoot/dummy1", info.getInternalForwardPath());
	}

	public void testDefault1() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/mock/update", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mock/update", query.getPath());
		assertTrue(query.isEmptyParameter());
	}

	public void testDefault2() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/mock/create", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mock/insert", query.getPath());
		assertTrue(query.isEmptyParameter());
	}

	public void testDefault3() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/mock/delete/10", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mock/delete", query.getPath());
		assertEquals(1, query.getParameterSize());
		assertEquals(1, query.getParam("value").size());
		assertEquals("10", query.getParam("value").get(0));
	}

	public void testDefault4() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/mock/delete/a", "GET");
		assertNull(info);
	}

	public void testDefault5() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/mock/cubby", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mock/name", query.getPath());
		assertEquals(1, query.getParam("name").size());
		assertEquals("cubby", query.getParam("name").get(0));
	}

	public void testPath1() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/foo/4/update", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mockPath/update", query.getPath());
		assertEquals(1, query.getParameterSize());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
	}

	public void testPath2() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/foo/4/create", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mockPath/insert", query.getPath());
		assertEquals(1, query.getParameterSize());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
	}

	public void testPath3() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/foo/4/delete/10", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mockPath/delete", query.getPath());
		assertEquals(2, query.getParameterSize());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
		assertEquals(1, query.getParam("value").size());
		assertEquals("10", query.getParam("value").get(0));
	}

	public void testPath4() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/foo/4/delete/a", "GET");
		assertNull(info);
	}

	public void testPath5() {
		InternalForwardInfo info = resolver.getInternalForwardInfo("/foo/4/cubby", "GET");
		assertNotNull(info);
		Query query = new Query(info.getInternalForwardPath());
		assertEquals("/mockPath/name", query.getPath());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
		assertEquals(1, query.getParam("name").size());
		assertEquals("cubby", query.getParam("name").get(0));
	}


	class Query {
		private String path;
		private Map<String, List<String>> params;
		public Query(String path) {
			String[] tokens = path.split("\\?", 2);
			switch (tokens.length) {
			case 1:
				this.path = tokens[0];
				this.params = new HashMap<String, List<String>>();
				break;
			case 2:
				this.path = tokens[0];
				this.params = parseQueryString(tokens[1]);
				
				break;
			default:
				Assert.fail("illegal path " + path);
				break;
			}
		}

		private Map<String, List<String>> parseQueryString(String queryString) {
			Map<String, List<String>> params = new HashMap<String, List<String>>();
			String[] tokens = queryString.split("&");
			for (String token : tokens) {
				String[] param = parseQueryParameter(token);
				String name = param[0];
				String value = param[1];
				List<String> values;
				if (params.containsKey(name)) {
					values = params.get(name);
				} else {
					values = new ArrayList<String>();
					params.put(name, values);
				}
				values.add(value);
			}
			return params;
		}

		private String[] parseQueryParameter(String token) {
			return token.split("=");
		}

		public String getPath() {
			return path;
		}

		public List<String> getParam(String name) {
			return params.get(name);
		}

		public boolean isEmptyParameter() {
			return params.isEmpty();
		}

		public int getParameterSize() {
			return params.size();
		}
	}

}
