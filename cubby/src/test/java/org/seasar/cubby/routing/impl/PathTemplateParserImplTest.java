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

import org.seasar.cubby.exception.PathTemplateParseException;
import org.seasar.cubby.routing.PathTemplateParser;

import junit.framework.TestCase;

public class PathTemplateParserImplTest extends TestCase {

	private PathTemplateParser parser = new PathTemplateParserImpl();

	public void testParseFail() {
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
		} catch (PathTemplateParseException e) {
			// ok
			e.printStackTrace();
		}
	}

	public void testParse1() {
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

	public void testParse2() {
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

	public void testParse3() {
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

	public void testParse4() {
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

	public void testParse5() {
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

	public void testParse6() {
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
