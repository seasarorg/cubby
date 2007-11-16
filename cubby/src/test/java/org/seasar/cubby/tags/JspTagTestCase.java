package org.seasar.cubby.tags;

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.SimpleTag;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.extension.unit.S2TestCase;

abstract public class JspTagTestCase extends S2TestCase {
	protected MockJspFragment jspBody;
	protected MockJspContext context;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
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

	public void setupErrors(JspContext context) {
		ActionErrorsImpl errors = new ActionErrorsImpl();
		context.setAttribute("errors", errors, PageContext.REQUEST_SCOPE);
	}

}
