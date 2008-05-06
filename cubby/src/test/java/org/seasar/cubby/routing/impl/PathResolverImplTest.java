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
package org.seasar.cubby.routing.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.routing.InternalForwardInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ClassUtil;

public class PathResolverImplTest extends S2TestCase {

	private PathResolver pathResolver;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testGetRoutings() {
		List<Routing> routings = new ArrayList<Routing>(pathResolver
				.getRoutings());
		assertEquals(25, routings.size());
	}

	public void testAdd() {
		pathResolver.add("/wiki/edit/{name,.+}", MockAction.class, "update");
		pathResolver.add("/wiki/{name,.+}", MockAction.class, "update2",
				RequestMethod.POST);
		pathResolver.add("/wiki/{name,.+}", MockAction.class, "name",
				RequestMethod.PUT);

		List<Routing> routings = new ArrayList<Routing>(pathResolver
				.getRoutings());
		assertEquals(29, routings.size());

		Iterator<Routing> iterator = routings.iterator();

		Routing routing1 = iterator.next();
		assertEquals("^/wiki/edit/(.+)$", routing1.getPattern().pattern());
		assertEquals(RequestMethod.GET, routing1.getRequestMethod());
		assertEquals(0, routing1.getPriority());

		Routing routing2 = iterator.next();
		assertEquals("^/wiki/edit/(.+)$", routing2.getPattern().pattern());
		assertEquals(RequestMethod.POST, routing2.getRequestMethod());
		assertEquals(1, routing2.getPriority());

		Routing routing3 = iterator.next();
		assertEquals("^/wiki/(.+)$", routing3.getPattern().pattern());
		assertEquals(RequestMethod.POST, routing3.getRequestMethod());
		assertEquals(2, routing3.getPriority());

		Routing routing4 = iterator.next();
		assertEquals("^/wiki/(.+)$", routing4.getPattern().pattern());
		assertEquals(RequestMethod.PUT, routing4.getRequestMethod());
		assertEquals(3, routing4.getPriority());

		Routing routing5 = iterator.next();
		assertEquals("^/mockPriority/update$", routing5.getPattern().pattern());
		assertEquals(100, routing5.getPriority());
		assertEquals(RequestMethod.PUT, routing5.getRequestMethod());
	}

	public void testRoot1() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo("/",
				"GET");
		assertNotNull(info);
		assertEquals(CubbyConstants.INTERNAL_FORWARD_DIRECTORY, info
				.getInternalForwardPath());
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockRootAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockRootAction.class, "index",
				new Class[0]), routing.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());
	}

	public void testRoot2() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/dummy1", "GET");
		assertNotNull(info);
		assertEquals(CubbyConstants.INTERNAL_FORWARD_DIRECTORY, info
				.getInternalForwardPath());
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockRootAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockRootAction.class, "dummy1",
				new Class[0]), routing.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());
	}

	public void testDefault1() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/mock/update", "GET");
		assertNotNull(info);
		assertEquals(CubbyConstants.INTERNAL_FORWARD_DIRECTORY, info
				.getInternalForwardPath());
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockAction.class, "update",
				new Class[0]), routing.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());
	}

	public void testDefault2() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/mock/create", "GET");
		assertNotNull(info);
		assertEquals(CubbyConstants.INTERNAL_FORWARD_DIRECTORY, info
				.getInternalForwardPath());
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockAction.class, "insert",
				new Class[0]), routing.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());
	}

	public void testDefault3() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/mock/delete/10", "GET");
		assertNotNull(info);
		assertTrue(info.getInternalForwardPath().startsWith(
				CubbyConstants.INTERNAL_FORWARD_DIRECTORY));
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockAction.class, "delete",
				new Class[0]), routing.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());
		Query query = new Query(info.getInternalForwardPath());
		assertEquals(1, query.getParameterSize());
		assertEquals(1, query.getParam("value").size());
		assertEquals("10", query.getParam("value").get(0));
	}

	public void testDefault4() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/mock/delete/a", "GET");
		assertNull(info);
	}

	public void testDefault5() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/mock/cubby", "GET");
		assertNotNull(info);
		assertTrue(info.getInternalForwardPath().startsWith(
				CubbyConstants.INTERNAL_FORWARD_DIRECTORY));
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(ClassUtil
				.getMethod(MockAction.class, "name", new Class[0]), routing
				.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());
		Query query = new Query(info.getInternalForwardPath());
		assertEquals(1, query.getParam("name").size());
		assertEquals("cubby", query.getParam("name").get(0));
	}

	public void testPath1() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/foo/4/update", "GET");
		assertNotNull(info);
		assertTrue(info.getInternalForwardPath().startsWith(
				CubbyConstants.INTERNAL_FORWARD_DIRECTORY));
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockPathAction.class, "update",
				new Class[0]), routing.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());
		Query query = new Query(info.getInternalForwardPath());
		assertEquals(1, query.getParameterSize());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
	}

	public void testPath2() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/foo/4/create", "GET");
		assertNotNull(info);
		assertTrue(info.getInternalForwardPath().startsWith(
				CubbyConstants.INTERNAL_FORWARD_DIRECTORY));
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockPathAction.class, "insert",
				new Class[0]), routing.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());
		Query query = new Query(info.getInternalForwardPath());
		assertEquals(1, query.getParameterSize());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
	}

	public void testPath3() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/foo/4/delete/10", "GET");
		assertNotNull(info);
		assertTrue(info.getInternalForwardPath().startsWith(
				CubbyConstants.INTERNAL_FORWARD_DIRECTORY));
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockPathAction.class, "delete",
				new Class[0]), routing.getMethod());
		assertEquals(2, routing.getUriParameterNames().size());
		Query query = new Query(info.getInternalForwardPath());
		assertEquals(2, query.getParameterSize());
		assertEquals(1, query.getParam("id").size());
		assertEquals("4", query.getParam("id").get(0));
		assertEquals(1, query.getParam("value").size());
		assertEquals("10", query.getParam("value").get(0));
	}

	public void testPath4() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/foo/4/delete/a", "GET");
		assertNull(info);
	}

	public void testPath5() {
		InternalForwardInfo info = pathResolver.getInternalForwardInfo(
				"/foo/4/cubby", "GET");
		assertNotNull(info);
		assertTrue(info.getInternalForwardPath().startsWith(
				CubbyConstants.INTERNAL_FORWARD_DIRECTORY));
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(ClassUtil.getMethod(MockPathAction.class, "name",
				new Class[0]), routing.getMethod());
		assertEquals(2, routing.getUriParameterNames().size());
		Query query = new Query(info.getInternalForwardPath());
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
