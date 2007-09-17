package org.seasar.cubby.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.PageContext;

public class SelectTagTest extends JspTagTestCase {

	SelectTag tag;
	List<ItemBean> items = new ArrayList<ItemBean>();
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new SelectTag();
		setupSimpleTag(tag);
		context.setAttribute("fieldErrors", new HashMap<String, String>(), PageContext.REQUEST_SCOPE);
		items.add(new ItemBean(1, "name1"));
		items.add(new ItemBean(2, "name2"));
		items.add(new ItemBean(3, "name3"));
	}

	public void testDoTag1() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "id", "stringField");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.setLabelProperty("name");
		tag.doTag();
		assertEquals("基本",
				"<select name=\"stringField\" id=\"stringField\" >\n<option value=\"\"></option>\n<option value=\"1\" selected=\"true\">name1</option>\n<option value=\"2\" >name2</option>\n<option value=\"3\" >name3</option>\n</select>\n", context.getResult());
	}

	public void testDoTag2() throws Exception {
		FormDto form = new FormDto();
		form.setIntegerArrayField(new Integer[] {1, 3});
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "integerArrayField");
		tag.setDynamicAttribute(null, "size", "5");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.setLabelProperty("name");
		tag.doTag();
		assertEquals("selectedの対象が2つ",
				"<select size=\"5\" name=\"integerArrayField\" >\n<option value=\"\"></option>\n<option value=\"1\" selected=\"true\">name1</option>\n<option value=\"2\" >name2</option>\n<option value=\"3\" selected=\"true\">name3</option>\n</select>\n", context.getResult());
	}

	public void testDoTag3() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "id", "stringField");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.doTag();
		assertEquals("labelPropertyを省略",
				"<select name=\"stringField\" id=\"stringField\" >\n<option value=\"\"></option>\n<option value=\"1\" selected=\"true\">1</option>\n<option value=\"2\" >2</option>\n<option value=\"3\" >3</option>\n</select>\n", context.getResult());
	}

	public void testDoTag4() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.setLabelProperty("name");
		tag.setEmptyOption(false);
		tag.doTag();
		assertEquals("emptyOption=false",
				"<select name=\"stringField\" >\n<option value=\"1\" selected=\"true\">name1</option>\n<option value=\"2\" >name2</option>\n<option value=\"3\" >name3</option>\n</select>\n", context.getResult());
	}

	public void testDoTag5() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setItems(items);
		tag.setValueProperty("id");
		tag.setLabelProperty("name");
		tag.setEmptyOptionLabel("empty label");
		tag.doTag();
		assertEquals("emptyOption=true, emptyOptionLabel=empty label",
				"<select name=\"stringField\" >\n<option value=\"\">empty label</option>\n<option value=\"1\" selected=\"true\">name1</option>\n<option value=\"2\" >name2</option>\n<option value=\"3\" >name3</option>\n</select>\n", context.getResult());
	}

	public static class ItemBean {
		private Integer id;
		private String name;
		public ItemBean(int id, String name) {
			this.id = id;
			this.name = name;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
