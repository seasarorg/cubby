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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.RoutingException;
import org.seasar.cubby.routing.impl.PathResolverImpl;

public class PathResolverImplTest {

	private PathResolverImpl pathResolver;

	@Before
	public void setupPathResolver() {
		pathResolver = new PathResolverImpl();
		List<Class<?>> actionClasses = new ArrayList<Class<?>>();
		actionClasses.add(MockAction.class);
		actionClasses.add(MockRootAction.class);
		actionClasses.add(MockPathAction.class);
		pathResolver.addAll(actionClasses);
	}

	@Test
	public void testGetRoutings() {
		List<Routing> routings = new ArrayList<Routing>(pathResolver
				.getRoutings());
		assertEquals(24, routings.size());
	}

	@Test
	public void testAdd() {
		pathResolver.add("/wiki/edit/{name,.+}", MockAction.class, "update");
		pathResolver.add("/wiki/{name,.+}", MockAction.class, "update2",
				RequestMethod.POST);
		pathResolver.add("/wiki/{name,.+}", MockAction.class, "name",
				RequestMethod.PUT);

		List<Routing> routings = new ArrayList<Routing>(pathResolver
				.getRoutings());
		assertEquals(28, routings.size());

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

		// Routing routing5 = iterator.next();
		// assertEquals("^/mockPriority/update$",
		// routing5.getPattern().pattern());
		// assertEquals(100, routing5.getPriority());
		// assertEquals(RequestMethod.PUT, routing5.getRequestMethod());
	}

	@Test
	public void testRoot1() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/", "GET", "UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockRootAction.class, routing.getActionClass());
		assertEquals(MockRootAction.class.getMethod("index"), routing
				.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(0, uriParameters.size());
	}

	@Test
	public void testRoot2() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/dummy1", "GET", "UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockRootAction.class, routing.getActionClass());
		assertEquals(MockRootAction.class.getMethod("dummy1"), routing
				.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(0, uriParameters.size());
	}

	@Test
	public void testDefault1() throws Exception {
		PathInfo info = pathResolver
				.getPathInfo("/mock/update", "GET", "UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(MockAction.class.getMethod("update"), routing.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(0, uriParameters.size());
	}

	@Test
	public void testDefault2() throws Exception {
		PathInfo info = pathResolver
				.getPathInfo("/mock/create", "GET", "UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(MockAction.class.getMethod("insert"), routing.getMethod());
		assertEquals(0, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(0, uriParameters.size());
	}

	@Test
	public void testDefault3() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/mock/delete/10", "GET",
				"UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(MockAction.class.getMethod("delete"), routing.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(1, uriParameters.size());
		String[] value = uriParameters.get("value");
		assertEquals(1, value.length);
		assertEquals("10", value[0]);
	}

	@Test
	public void testDefault4() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/mock/delete/a", "GET",
				"UTF-8");
		assertNull(info);
	}

	@Test
	public void testDefault5() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/mock/cubby", "GET", "UTf-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockAction.class, routing.getActionClass());
		assertEquals(MockAction.class.getMethod("name"), routing.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(1, uriParameters.size());
		String[] value = uriParameters.get("name");
		assertEquals(1, value.length);
		assertEquals("cubby", value[0]);
	}

	@Test
	public void testPath1() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/foo/4/update", "GET",
				"UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(MockPathAction.class.getMethod("update"), routing
				.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(1, uriParameters.size());
		String[] value = uriParameters.get("id");
		assertEquals(1, value.length);
		assertEquals("4", value[0]);
	}

	@Test
	public void testPath2() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/foo/4/create", "GET",
				"UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(MockPathAction.class.getMethod("insert"), routing
				.getMethod());
		assertEquals(1, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(1, uriParameters.size());
		String[] value = uriParameters.get("id");
		assertEquals(1, value.length);
		assertEquals("4", value[0]);
	}

	@Test
	public void testPath3() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/foo/4/delete/10", "GET",
				"UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(MockPathAction.class.getMethod("delete"), routing
				.getMethod());
		assertEquals(2, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(2, uriParameters.size());
		String[] id = uriParameters.get("id");
		assertEquals(1, id.length);
		assertEquals("4", id[0]);
		String[] value = uriParameters.get("value");
		assertEquals(1, value.length);
		assertEquals("10", value[0]);
	}

	@Test
	public void testPath4() throws Exception {
		PathInfo info = pathResolver.getPathInfo("/foo/4/delete/a", "GET",
				"UTF-8");
		assertNull(info);
	}

	@Test
	public void testPath5() throws Exception {
		PathInfo info = pathResolver
				.getPathInfo("/foo/4/cubby", "GET", "UTF-8");
		assertNotNull(info);
		Map<String, Routing> routings = info.getOnSubmitRoutings();
		assertNotNull(routings);
		assertEquals(1, routings.size());
		Routing routing = routings.get(null);
		assertEquals(MockPathAction.class, routing.getActionClass());
		assertEquals(MockPathAction.class.getMethod("name"), routing
				.getMethod());
		assertEquals(2, routing.getUriParameterNames().size());

		Map<String, String[]> uriParameters = info.getURIParameters();
		assertEquals(2, uriParameters.size());
		String[] id = uriParameters.get("id");
		assertEquals(1, id.length);
		assertEquals("4", id[0]);
		String[] value = uriParameters.get("name");
		assertEquals(1, value.length);
		assertEquals("cubby", value[0]);
	}

	@Test
	public void testAddAbstractClass() throws Exception {
		try {
			pathResolver.add("/parent/m1", ParentAction.class, "m1");
			fail();
		} catch (RoutingException e) {
			// assertEquals("アクションクラスではないクラスを登録するとエラー(抽象クラス)", "ECUB0002",
			// e.getMessageCode());
		}
		try {
			pathResolver.add("/child/m3", ChildAction.class, "m3");
			fail();
		} catch (RoutingException e) {
			// assertEquals("アクションメソッドではないメソッドを登録するとエラー(戻り値がObject)",
			// "ECUB0003", e.getMessageCode());
		}
		pathResolver.add("/child/m1", ChildAction.class, "m1");
		pathResolver.add("/child/m2", ChildAction.class, "m2");
		Collection<Routing> routings = pathResolver.getRoutings();
		assertEquals("正常に登録できたルーティング情報の数", 28, routings.size());
		Iterator<Routing> it = routings.iterator();
		assertRouting(it.next(), "/child/m1", RequestMethod.GET,
				ChildAction.class, "m1");
		assertRouting(it.next(), "/child/m1", RequestMethod.POST,
				ChildAction.class, "m1");
		assertRouting(it.next(), "/child/m2", RequestMethod.GET,
				ChildAction.class, "m2");
		assertRouting(it.next(), "/child/m2", RequestMethod.POST,
				ChildAction.class, "m2");
	}

	private void assertRouting(Routing routing, String path,
			RequestMethod requestMethod, Class<? extends Action> actionClass,
			String actionMethod) {
		assertEquals(path, routing.getActionPath());
		assertEquals(requestMethod, routing.getRequestMethod());
		assertEquals(actionClass, routing.getActionClass());
		assertEquals(actionMethod, routing.getMethod().getName());
	}

	public abstract class ParentAction extends Action {
		public ActionResult m1() {
			return null;
		}
	}

	public class ChildAction extends ParentAction {
		public ActionResult m2() {
			return null;
		}

		public Object m3() {
			return null;
		}
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
