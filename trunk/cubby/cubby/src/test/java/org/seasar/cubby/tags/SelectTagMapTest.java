package org.seasar.cubby.tags;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.jdom.Element;
import org.seasar.cubby.CubbyConstants;
import org.seasar.framework.util.StringUtil;

public class SelectTagMapTest extends JspTagTestCase {

	SelectTag tag;
	Map<Integer, String> items = new LinkedHashMap<Integer, String>();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new SelectTag();
		setupSimpleTag(tag);
		setupErrors(context);
		items.put(1, "name1");
		items.put(2, "name2");
		items.put(3, "name3");
	}

	public void testDoTag1() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
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
			if (StringUtil.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtil.isEmpty(child.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
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
//		assertEquals("基本",
//				"<select name=\"stringField\" id=\"stringField\" >\n" +
//				"<option value=\"\"></option>\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" >name3</option>\n" +
//				"</select>\n", context.getResult());
	}

	public void testDoTag2() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("integerArrayField", new String[] { "1", "3" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
		tag.setName("integerArrayField");
		tag.setDynamicAttribute(null, "size", "5");
		tag.setItems(items);
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "selectedの対象が2つ";
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "integerArrayField", element.getAttributeValue("name"));
		assertEquals(message, "5", element.getAttributeValue("size"));
		assertEquals(message, 4, element.getChildren().size());
		for (Object o : element.getChildren("option")) {
			Element child = (Element) o;
			String value = child.getValue();
			if (StringUtil.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtil.isEmpty(child.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
			} else if ("name2".equals(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertEquals(message, "2", child.getAttributeValue("value"));
			} else if ("name3".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "3", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
			} else {
				fail(message);
			}
		}
//		assertEquals("selectedの対象が2つ",
//				"<select size=\"5\" name=\"integerArrayField\" >\n" +
//				"<option value=\"\"></option>\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" selected=\"true\">name3</option>\n" +
//				"</select>\n", context.getResult());
	}

	public void testDoTag4() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
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
				assertEquals(message, "true", child.getAttributeValue("selected"));
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
//		assertEquals("emptyOption=false",
//				"<select name=\"stringField\" >\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" >name3</option>\n" +
//				"</select>\n", context.getResult());
	}

	public void testDoTag5() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
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
				assertTrue(message, StringUtil.isEmpty(child.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
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
//		assertEquals("emptyOption=true, emptyOptionLabel=empty label",
//				"<select name=\"stringField\" >\n" +
//				"<option value=\"\">empty label</option>\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" >name3</option>\n" +
//				"</select>\n", context.getResult());
	}

	public void testDoTag11() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
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
			if (StringUtil.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtil.isEmpty(child.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
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
//		assertEquals("labelPropertyを設定",
//				"<select name=\"stringField\" id=\"stringField\" >\n" +
//				"<option value=\"\"></option>\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" >name3</option>\n" +
//				"</select>\n", context.getResult());
	}

	public void testDoTag12() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
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
			if (StringUtil.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtil.isEmpty(child.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
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
//		assertEquals("valuePropertyを設定",
//				"<select name=\"stringField\" id=\"stringField\" >\n" +
//				"<option value=\"\"></option>\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" >name3</option>\n" +
//				"</select>\n", context.getResult());
	}

	public void testDoTag13() throws Exception {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
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
			if (StringUtil.isEmpty(value)) {
				assertEquals(message, 1, child.getAttributes().size());
				assertTrue(message, StringUtil.isEmpty(child.getAttributeValue("value")));
			} else if ("name1".equals(value)) {
				assertEquals(message, 2, child.getAttributes().size());
				assertEquals(message, "1", child.getAttributeValue("value"));
				assertEquals(message, "true", child.getAttributeValue("selected"));
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
//		assertEquals("valuePropertyとlabelPropertyを設定",
//				"<select name=\"stringField\" id=\"stringField\" >\n" +
//				"<option value=\"\"></option>\n" +
//				"<option value=\"1\" selected=\"true\">name1</option>\n" +
//				"<option value=\"2\" >name2</option>\n" +
//				"<option value=\"3\" >name3</option>\n" +
//				"</select>\n", context.getResult());
	}

}
