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
import static org.junit.Assert.fail;

import org.junit.Test;
import org.seasar.cubby.routing.PathTemplateException;
import org.seasar.cubby.routing.PathTemplateParser;

public class PathTemplateParserImplTest {

	private PathTemplateParser parser = new PathTemplateParserImpl();

	@Test
	public void parseFail() {
		assertIllegalTemplate("/foo/bar/{}");
		assertIllegalTemplate("/foo/bar/{");
		assertIllegalTemplate("/foo/bar/{a");
		assertIllegalTemplate("/foo/bar/{a,");
		assertIllegalTemplate("/foo/bar/{a,b");
		assertIllegalTemplate("/foo/bar/{a,}");
	}

	private void assertIllegalTemplate(String template) {
		try {
			parser.parse(template, new PathTemplateParser.Handler() {

				public String handle(String name, String regex) {
					fail();
					return regex;
				}

			});
			fail();
		} catch (PathTemplateException e) {
			// ok
			e.printStackTrace();
		}
	}

	@Test
	public void parse1() {
		String regex = parser.parse("/foo/{abc}",
				new PathTemplateParser.Handler() {

					private int count = 0;

					public String handle(String name, String regex) {
						switch (count) {
						case 0:
							assertEquals("abc", name);
							assertEquals("[a-zA-Z0-9]+", regex);
							break;
						default:
							fail();
						}
						count++;
						return "(" + regex + ")";
					}

				});
		assertEquals("/foo/([a-zA-Z0-9]+)", regex);
	}

	@Test
	public void parse2() {
		String regex = parser.parse("/foo/{abc}/bar/{def}",
				new PathTemplateParser.Handler() {

					private int count = 0;

					public String handle(String name, String regex) {
						switch (count) {
						case 0:
							assertEquals("abc", name);
							assertEquals("[a-zA-Z0-9]+", regex);
							break;
						case 1:
							assertEquals("def", name);
							assertEquals("[a-zA-Z0-9]+", regex);
							break;
						default:
							fail();
						}
						count++;
						return "(" + regex + ")";
					}

				});
		assertEquals("/foo/([a-zA-Z0-9]+)/bar/([a-zA-Z0-9]+)", regex);
	}

	@Test
	public void parse3() {
		String regex = parser.parse("/foo/bar/{abc,{3}a}",
				new PathTemplateParser.Handler() {

					private int count = 0;

					public String handle(String name, String regex) {
						switch (count) {
						case 0:
							assertEquals("abc", name);
							assertEquals("{3}a", regex);
							break;
						default:
							fail();
						}
						count++;
						return "(" + regex + ")";
					}

				});
		assertEquals("/foo/bar/({3}a)", regex);
	}

	@Test
	public void parse4() {
		String regex = parser.parse("/foo/bar/{abc,\\{aa}",
				new PathTemplateParser.Handler() {

					private int count = 0;

					public String handle(String name, String regex) {
						switch (count) {
						case 0:
							assertEquals("abc", name);
							assertEquals("\\{aa", regex);
							break;
						default:
							fail();
						}
						count++;
						return "(" + regex + ")";
					}

				});
		assertEquals("/foo/bar/(\\{aa)", regex);
	}

	@Test
	public void parse5() {
		String regex = parser.parse("/foo/bar/{abc,\\{aa}/{def,a\\}a}",
				new PathTemplateParser.Handler() {

					private int count = 0;

					public String handle(String name, String regex) {
						switch (count) {
						case 0:
							assertEquals("abc", name);
							assertEquals("\\{aa", regex);
							break;
						case 1:
							assertEquals("def", name);
							assertEquals("a\\}a", regex);
							break;
						default:
							fail();
						}
						count++;
						return "(" + regex + ")";
					}

				});
		assertEquals("/foo/bar/(\\{aa)/(a\\}a)", regex);
	}

	@Test
	public void parse6() {
		String regex = parser.parse("/foo/bar/{abc,aa\\\\}",
				new PathTemplateParser.Handler() {

					private int count = 0;

					public String handle(String name, String regex) {
						switch (count) {
						case 0:
							assertEquals("abc", name);
							assertEquals("aa\\\\", regex);
							break;
						default:
							fail();
						}
						count++;
						return "(" + regex + ")";
					}

				});
		assertEquals("/foo/bar/(aa\\\\)", regex);
	}

}
