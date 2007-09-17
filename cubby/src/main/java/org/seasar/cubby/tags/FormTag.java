package org.seasar.cubby.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyHelperFunctions;

public class FormTag extends DynamicAttributesTagSupport {
	
	private Object value;
	
	public void setValue(final Object value) {
		this.value = value;
	}

	public void doTag() throws JspException, IOException {
		getJspContext().setAttribute("__form", value, PageContext.REQUEST_SCOPE);
		JspWriter out = getJspContext().getOut();
		out.write("<form ");
		out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
		out.write(">\n");
		getJspBody().invoke(out);
		out.write("</form>\n");
		getJspContext().removeAttribute("__form", PageContext.REQUEST_SCOPE);
	}
}
