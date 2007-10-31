package org.seasar.cubby.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;

public class NullTag extends DynamicAttributesTagSupport {
	@Override
	public void doTag() throws JspException, IOException {}
}
