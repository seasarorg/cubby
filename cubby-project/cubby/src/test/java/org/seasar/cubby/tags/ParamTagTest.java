package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ParamTagTest extends SimpleTagTestCase {

	private ParamTag tag;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new ParamTag();
		setupSimpleTag(tag);
		setupErrors(context);
	}

	public void testDoTag1() throws JspException, IOException {
		final MockParentTag parent = new MockParentTag();
		tag.setParent(parent);
		tag.setName("paramname");
		tag.setValue("paramvalue");
		tag.doTag();
		final Map<String, String> parameters = parent.getParameters();
		assertEquals(1, parameters.size());
		final String value = parameters.get("paramname");
		assertNotNull(value);
		assertEquals("paramvalue", value);
	}

	public void testDoTag2() throws JspException, IOException {
		final MockParentTag parent = new MockParentTag();
		tag.setParent(parent);
		tag.setName("paramname");
		MockJspFragment body = new MockJspFragment();
		body.setBody("bodyvalue");
		tag.setJspBody(body);
		tag.doTag();
		final Map<String, String> parameters = parent.getParameters();
		assertEquals(1, parameters.size());
		final String value = parameters.get("paramname");
		assertNotNull(value);
		assertEquals("bodyvalue", value);
	}

	public void testDoTagHasIllegalParent() throws JspException, IOException {
		final InputTag parent = new InputTag();
		assertFalse(parent instanceof ParamParent);
		tag.setParent(parent);
		tag.setName("paramname");
		tag.setValue("paramvalue");
		try {
			tag.doTag();
			fail();
		} catch (final JspException e) {
			// ok
			e.printStackTrace();
		}
	}

	public void testDoTagHasNoParent() throws JspException, IOException {
		tag.setName("paramname");
		tag.setValue("paramvalue");
		try {
			tag.doTag();
			fail();
		} catch (final JspException e) {
			// ok
			e.printStackTrace();
		}
	}

	private class MockParentTag extends SimpleTagSupport implements
			ParamParent {

		private final Map<String, String> parameters = new HashMap<String, String>();

		public void addParameter(final String name, final String value) {
			parameters.put(name, value);
		}

		public Map<String, String> getParameters() {
			return parameters;
		}

	}

}
