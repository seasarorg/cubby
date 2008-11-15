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
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.routing.impl.RoutingImpl;
import org.seasar.cubby.internal.routing.impl.PathResolverImpl.RoutingComparator;

public class RoutingComparatorTest {

	private RoutingComparator comparator = new RoutingComparator();
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
		assertEquals(0, comparator.compare(routing1, routing1d));
		assertEquals(0, comparator.compare(routing5, routing5d));
	}

	@Test
	public void sort() {
		List<Routing> routings = new ArrayList<Routing>(asList(new Routing[] {
				routing3, routing5, routing1, routing4, routing2 }));
		Collections.sort(routings, comparator);
		System.out.println(routings);
		assertSame("1", routing1, routings.get(0));
		assertSame("2", routing2, routings.get(1));
		assertSame("3", routing3, routings.get(2));
		assertSame("4", routing4, routings.get(3));
		assertSame("5", routing5, routings.get(4));
	}

	@Test
	public void sort2() {
		List<Routing> routings = new ArrayList<Routing>(asList(new Routing[] {
				routing3, routing5, routing1, routing4, routing2, routing6,
				routing6d }));
		Collections.sort(routings, comparator);
		System.out.println(routings);
		assertSame("0", routing6d, routings.get(0));
		assertSame("1", routing6, routings.get(1));
		assertSame("2", routing1, routings.get(2));
		assertSame("3", routing2, routings.get(3));
		assertSame("4", routing3, routings.get(4));
		assertSame("5", routing4, routings.get(5));
		assertSame("6", routing5, routings.get(6));
	}

}
