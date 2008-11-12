package org.seasar.cubby.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.junit.Before;
import org.junit.Test;

public class ParamTagTest extends SimpleTagTestCase {

	private ParamTag tag;

	@Before
	public void setup() throws Exception {
		tag = new ParamTag();
		setupSimpleTag(tag);
		setupErrors(context);
	}

	@Test
	public void doTag1() throws JspException, IOException {
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

	@Test
	public void doTag2() throws JspException, IOException {
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

	@Test
	public void doTagHasIllegalParent() throws JspException, IOException {
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

	@Test
	public void doTagHasNoParent() throws JspException, IOException {
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

	private class MockParentTag extends SimpleTagSupport implements ParamParent {

		private final Map<String, String> parameters = new HashMap<String, String>();

		public void addParameter(final String name, final String value) {
			parameters.put(name, value);
		}

		public Map<String, String> getParameters() {
			return parameters;
		}

	}

}
