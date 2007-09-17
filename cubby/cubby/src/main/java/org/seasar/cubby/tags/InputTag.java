package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyHelperFunctions;

public class InputTag extends DynamicAttributesTagSupport {
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		final Map fieldErros = (Map) getJspContext().getAttribute("fieldErrors", PageContext.REQUEST_SCOPE);
		if (fieldErros.get(getDynamicAttribute().get("name")) != null) {
			CubbyHelperFunctions.addClassName(getDynamicAttribute(), "fieldError");
		}

		final JspWriter out = getJspContext().getOut();
		final Object form = getJspContext().getAttribute("__form", PageContext.REQUEST_SCOPE);
		if ("checkbox".equals(type)) {
			final String value = toString(getDynamicAttribute().get("value"));
			final Object checkedValue = CubbyHelperFunctions.formValue2(getDynamicAttribute(), form, getJspContext(), "checkedValue");
			getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
			getJspContext().setAttribute("checkedValue", checkedValue, PageContext.PAGE_SCOPE);
			out.write("<input type=\"");
			out.write(type);
			out.write("\" value=\"");
			out.write(value);// TODO
			out.write("\" ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write(" ");
			out.write(CubbyHelperFunctions.checked(value, checkedValue));
			out.write("/>\n");
		} else {
			final Object value = CubbyHelperFunctions.formValue2(getDynamicAttribute(), form, getJspContext(), "value");
			final Object checkedValue = getDynamicAttribute().get("checkedValue");
			getJspContext().setAttribute("value", value, PageContext.PAGE_SCOPE);
			getJspContext().setAttribute("checkedValue", checkedValue, PageContext.PAGE_SCOPE);
			out.write("<input type=\"");
			out.write(type);
			out.write("\" value=\"");
			out.write(CubbyHelperFunctions.convertFieldValue(value, form, getRequest(), toString(getDynamicAttribute().get("value"))));// TODO
			out.write("\" ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write("/>\n");
		}
	}
}
