package org.seasar.cubby.tags;


public class NullTagTest extends JspTagTestCase {

	NullTag tag;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new NullTag();
		setupSimpleTag(tag);
		jspBody.setBody("Dummy Body Text");
	}

	public void testDoTag() throws Exception {
		tag.doTag();
		tag.setDynamicAttribute(null, "id", "token");

		assertEquals("", context.getResult());
	}
}
