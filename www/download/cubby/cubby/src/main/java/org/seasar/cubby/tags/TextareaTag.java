package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.cubby.util.CubbyHelperFunctions;

public class TextareaTag extends DynamicAttributesTagSupport {
	
	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		final Object form = getJspContext().getAttribute("__form", PageContext.REQUEST_SCOPE);
		final Object value = CubbyHelperFunctions.formValue2(getDynamicAttribute(), form, getJspContext(), "value");
		getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
		final Map fieldErros = (Map) getJspContext().getAttribute("fieldErrors", PageContext.REQUEST_SCOPE);
		if (fieldErros.get(getDynamicAttribute().get("name")) != null) {
			CubbyHelperFunctions.addClassName(getDynamicAttribute(), "fieldError");
		}
		JspWriter out = getJspContext().getOut();
		out.write("<textarea ");
		out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
		out.write(">");
		out.write(CubbyFunctions.out(value));
		out.write("</textarea>\n");
	}
}
