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

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.seasar.cubby.action.RequestMethod.GET;
import static org.seasar.cubby.action.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.impl.RoutingImpl;
import org.seasar.cubby.routing.impl.PathResolverImpl.RoutingKey;

public class RoutingKeyTest {

	private Routing routing1;
	private Routing routing1d;
	private Routing routing2;
	private Routing routing3;
	private Routing routing4;
	private Routing routing5;
	private Routing routing5d;
	private Routing routing6;
	private Routing routing6d;

	@Before
	public void setupRoutings() throws Exception {
		routing1 = new RoutingImpl(null, null, null, asList(new String[0]),
				Pattern.compile("/foo/bar"), GET, null, Integer.MAX_VALUE, true);
		routing1d = new RoutingImpl(null, null, null, asList(new String[0]),
				Pattern.compile("/foo/bar"), GET, null, Integer.MAX_VALUE, true);
		routing2 = new RoutingImpl(null, null, null,
				asList(new String[] { "p1" }), Pattern.compile("/foo/bar/a"),
				GET, null, Integer.MAX_VALUE, true);
		routing3 = new RoutingImpl(null, null, null, asList(new String[] {
				"p1", "p2" }), Pattern.compile("/foo/bar/bbb"), GET, null,
				Integer.MAX_VALUE, true);
		routing4 = new RoutingImpl(null, null, null, asList(new String[] {
				"p1", "p2" }), Pattern.compile("/foo/bar/cc"), GET, null,
				Integer.MAX_VALUE, true);
		routing5 = new RoutingImpl(null, null, null, asList(new String[] {
				"p1", "p2" }), Pattern.compile("/foo/bar/cc"), POST, null,
				Integer.MAX_VALUE, true);
		routing5d = new RoutingImpl(null, null, null, asList(new String[] {
				"p1", "p2" }), Pattern.compile("/foo/bar/cc"), POST, null,
				Integer.MAX_VALUE, true);
		routing6 = new RoutingImpl(null, null, null, asList(new String[] {
				"p1", "p2" }), Pattern.compile("/foo/bar/cc/dd"), GET, null, 1,
				false);
		routing6d = new RoutingImpl(null, null, null, asList(new String[] {
				"p1", "p2" }), Pattern.compile("/foo/bar/cc/dd"), GET, null, 0,
				false);
	}

	@Test
	public void duplicate() {
		assertEquals(0, new RoutingKey(routing1).compareTo(new RoutingKey(
				routing1d)));
		assertEquals(0, new RoutingKey(routing5).compareTo(new RoutingKey(
				routing5d)));
	}

	@Test
	public void sort() {
		Map<RoutingKey, Routing> map = new TreeMap<RoutingKey, Routing>();
		map.put(new RoutingKey(routing3), routing3);
		map.put(new RoutingKey(routing5), routing5);
		map.put(new RoutingKey(routing1), routing1);
		map.put(new RoutingKey(routing4), routing4);
		map.put(new RoutingKey(routing2), routing2);
		List<Routing> actualList = new ArrayList<Routing>(asList(new Routing[] {
				routing1, routing2, routing3, routing4, routing5 }));
		for (Routing routing : map.values()) {
			assertSame(actualList.remove(0), routing);
		}
	}

	@Test
	public void sort2() {
		Map<RoutingKey, Routing> map = new TreeMap<RoutingKey, Routing>();
		map.put(new RoutingKey(routing3), routing3);
		map.put(new RoutingKey(routing5), routing5);
		map.put(new RoutingKey(routing1), routing1);
		map.put(new RoutingKey(routing4), routing4);
		map.put(new RoutingKey(routing2), routing2);
		map.put(new RoutingKey(routing6), routing6);
		map.put(new RoutingKey(routing6d), routing6d);
		List<Routing> actualList = new ArrayList<Routing>(asList(new Routing[] {
				routing6d, routing6, routing1, routing2, routing3, routing4,
				routing5 }));
		for (Routing routing : map.values()) {
			assertSame(actualList.remove(0), routing);
		}
	}

}
