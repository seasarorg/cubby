package org.seasar.cubby.tags;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.SimpleTag;

import junit.framework.TestCase;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

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

	protected void setupBodyTag(BodyTag tag) {
		tag.setPageContext(context);
	}

	protected Element getResultAsElementFromContext() throws JDOMException,
			IOException {
		String result = context.getResult();
		Document document = new SAXBuilder().build(new StringReader(result));
		Element element = document.getRootElement();
		return element;
	}

}
