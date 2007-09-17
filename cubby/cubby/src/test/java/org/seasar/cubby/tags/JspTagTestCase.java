package org.seasar.cubby.tags;

import javax.servlet.jsp.tagext.SimpleTag;

import junit.framework.TestCase;

abstract public class JspTagTestCase extends TestCase {
	protected MockJspFragment jspBody;
	protected MockJspContext context;

	@Override
	protected void setUp() throws Exception {
		jspBody = new MockJspFragment();
		context = new MockJspContext();
		jspBody.setJspContext(context);
	}

	
	protected void setupSimpleTag(SimpleTag tag) {
		tag.setJspBody(jspBody);
		tag.setJspContext(context);
	}

}
