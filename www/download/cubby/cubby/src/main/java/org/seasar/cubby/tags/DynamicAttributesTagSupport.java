package org.seasar.cubby.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

abstract public class DynamicAttributesTagSupport extends SimpleTagSupport implements DynamicAttributes {
	
	@SuppressWarnings("unchecked")
	private Map<String, Object>  dyn = new HashMap<String, Object>();
	
	@SuppressWarnings("unchecked")
	public void setDynamicAttribute(final String uri, final String localName, final Object value)
	throws JspException {
		this.dyn.put(localName, value);
	}
	
	protected Map<String, Object> getDynamicAttribute() {
		return this.dyn;
	}
	
	protected String toString(Object object) {
		return object == null ? "" : object.toString();
	}
	
	protected PageContext getPageContext() {
		return (PageContext) getJspContext();
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getPageContext().getRequest();
	}

}
