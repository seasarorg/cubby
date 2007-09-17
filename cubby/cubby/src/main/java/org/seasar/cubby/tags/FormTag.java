package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.seasar.cubby.util.CubbyHelperFunctions;

public class FormTag extends BodyTagSupport implements DynamicAttributes {
	
	private static final long serialVersionUID = 3997441280451382093L;

	private Map<String, Object>  dyn = new HashMap<String, Object>();

	private Object value;
	
	public void setDynamicAttribute(final String uri, final String localName, final Object value)
	throws JspException {
		this.dyn.put(localName, value);
	}
	
	protected Map<String, Object> getDynamicAttribute() {
		return this.dyn;
	}
	
	public void setValue(final Object value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
		pageContext.setAttribute("__form", value, PageContext.REQUEST_SCOPE);
		JspWriter out = pageContext.getOut();
		try {
			out.write("<form ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write(">\n");
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</form>\n");
		} catch (IOException e) {
			throw new JspException(e);
		}
		pageContext.removeAttribute("__form", PageContext.REQUEST_SCOPE);
		return EVAL_PAGE;
	}
}
