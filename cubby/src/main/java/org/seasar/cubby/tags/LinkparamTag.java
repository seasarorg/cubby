package org.seasar.cubby.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LinkparamTag extends SimpleTagSupport {

	private String name;

	private String value;

	public void setName(final String name) {
		this.name = name;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		final LinkTag linkTag = (LinkTag) findAncestorWithClass(this,
				LinkTag.class);
		linkTag.addParameter(name, value);
	}

}
