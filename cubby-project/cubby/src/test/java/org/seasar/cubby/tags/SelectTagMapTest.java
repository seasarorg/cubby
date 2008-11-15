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
package org.seasar.cubby.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jdom.Element;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.internal.util.StringUtils;

public class SelectTagMapTest extends SimpleTagTestCase {

	private SelectTag tag;

	private Map<Integer, String> items = new LinkedHashMap<Integer, String>();

	@Before
	public void setUp() throws Exception {
		tag = new SelectTag();
		setupSimpleTag(tag);
		setupErrors(context);
		items.put(1, "name1");
		items.put(2, "name2");
		items.put(3, "name3");
	}

	@Test
	public void doTag1() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		tag.setParent(new MockFormTag(map));
		tag.setName("stringField");
		tag.setDynamicAttribute(null, "id", "stringField");
		tag.setItems(items);
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "基本";
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "stringField", element.getAttributeValue("id"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if (StringUtils.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtils.isEmpty(child
						.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
			} else {
				fail(message);
			}
		}
		// assertEquals("基本",
		// "<select name=\"stringField\" id=\"stringField\" >\n" +
		// "<option value=\"\"></option>\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" >name3</option>\n" +
		// "</select>\n", context.getResult());
	}

	@Test
	public void doTag2() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("integerArrayField", new String[] { "1", "3" });
		tag.setParent(new MockFormTag(map));
		tag.setName("integerArrayField");
		tag.setDynamicAttribute(null, "size", "5");
		tag.setItems(items);
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "selectedの対象が2つ";
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "integerArrayField", element
				.getAttributeValue("name"));
		assertEquals(message, "5", element.getAttributeValue("size"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if (StringUtils.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtils.isEmpty(child
						.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else {
				fail(message);
			}
		}
		// assertEquals("selectedの対象が2つ",
		// "<select size=\"5\" name=\"integerArrayField\" >\n" +
		// "<option value=\"\"></option>\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" selected=\"true\">name3</option>\n" +
		// "</select>\n", context.getResult());
	}

	@Test
	public void doTag4() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		tag.setParent(new MockFormTag(map));
		tag.setName("stringField");
		tag.setItems(items);
		tag.setEmptyOption(false);
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "emptyOption=false";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, 3, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
			} else {
				fail(message);
			}
		}
		// assertEquals("emptyOption=false",
		// "<select name=\"stringField\" >\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" >name3</option>\n" +
		// "</select>\n", context.getResult());
	}

	@Test
	public void doTag5() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		tag.setParent(new MockFormTag(map));
		tag.setName("stringField");
		tag.setItems(items);
		tag.setEmptyOptionLabel("empty label");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "emptyOption=true, emptyOptionLabel=empty label";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if ("empty label".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtils.isEmpty(child
						.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
			} else {
				fail(message);
			}
		}
		// assertEquals("emptyOption=true, emptyOptionLabel=empty label",
		// "<select name=\"stringField\" >\n" +
		// "<option value=\"\">empty label</option>\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" >name3</option>\n" +
		// "</select>\n", context.getResult());
	}

	@Test
	public void doTag11() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		tag.setParent(new MockFormTag(map));
		tag.setName("stringField");
		tag.setDynamicAttribute(null, "id", "stringField");
		tag.setItems(items);
		tag.setLabelProperty("name");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "labelProperty設定";
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "stringField", element.getAttributeValue("id"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if (StringUtils.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtils.isEmpty(child
						.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
			} else {
				fail(message);
			}
		}
		// assertEquals("labelPropertyを設定",
		// "<select name=\"stringField\" id=\"stringField\" >\n" +
		// "<option value=\"\"></option>\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" >name3</option>\n" +
		// "</select>\n", context.getResult());
	}

	@Test
	public void doTag12() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		tag.setParent(new MockFormTag(map));
		tag.setName("stringField");
		tag.setDynamicAttribute(null, "id", "stringField");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valuePropertyを設定";
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "stringField", element.getAttributeValue("id"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if (StringUtils.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtils.isEmpty(child
						.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
			} else {
				fail(message);
			}
		}
		// assertEquals("valuePropertyを設定",
		// "<select name=\"stringField\" id=\"stringField\" >\n" +
		// "<option value=\"\"></option>\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" >name3</option>\n" +
		// "</select>\n", context.getResult());
	}

	@Test
	public void doTag13() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		tag.setParent(new MockFormTag(map));
		tag.setName("stringField");
		tag.setDynamicAttribute(null, "id", "stringField");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.setLabelProperty("name");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valuePropertyとlabelPropertyを設定";
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "stringField", element.getAttributeValue("id"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if (StringUtils.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtils.isEmpty(child
						.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child
						.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
			} else {
				fail(message);
			}
		}
		// assertEquals("valuePropertyとlabelPropertyを設定",
		// "<select name=\"stringField\" id=\"stringField\" >\n" +
		// "<option value=\"\"></option>\n" +
		// "<option value=\"1\" selected=\"true\">name1</option>\n" +
		// "<option value=\"2\" >name2</option>\n" +
		// "<option value=\"3\" >name3</option>\n" +
		// "</select>\n", context.getResult());
	}

}
