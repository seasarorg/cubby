package org.seasar.cubby.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.util.CubbyHelperFunctions;
import org.seasar.framework.util.StringUtil;

/**
 * 
 * @author agata
 * @deprecated
 */
public class ErrorsTag extends DynamicAttributesTagSupport {

	@SuppressWarnings("unchecked")
	private Collection<String> items;

	public void setCollection(final Collection<String> items) {
		this.items = items;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doTag() throws JspException, IOException {
		Collection<String> items = this.items;
		if (items == null) {
			items = (Collection<String>) getJspContext().findAttribute("allErrors");
			getJspContext().setAttribute("items", items, PageContext.PAGE_SCOPE);
		}
		if (items.size() > 0) {
			JspWriter out = getPageContext().getOut();
			out.write("<div class=\"errors\" ");
			out.write(CubbyHelperFunctions.toAttr(getDynamicAttribute()));
			out.write(">\n");
			out.write("<ul>\n");
			for (String error : items) {
				out.write("<li>");
				out.write(StringUtil.replace(error, "\n", "<br/>"));
				out.write("</li>\n");
			}
			out.write("</ul>\n");
			out.write("</div>\n");
		}
	}
}
