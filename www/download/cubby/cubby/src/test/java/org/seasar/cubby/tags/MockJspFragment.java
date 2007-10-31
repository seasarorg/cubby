package org.seasar.cubby.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTag;

public class MockJspFragment extends JspFragment {

	private JspContext context;
	private String  body = "";
	private List<SimpleTag> childTags = new ArrayList<SimpleTag>();
	
	public void setJspContext(JspContext context) {
		this.context = context;
	}
	
	@Override
	public JspContext getJspContext() {
		return context;
	}

	@Override
	public void invoke(Writer out) throws JspException, IOException {
		out.write(body);
		for (SimpleTag tag : childTags) {
			tag.setJspContext(getJspContext());
			tag.doTag();
		}
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addChildTag(SimpleTag childTag) {
		childTags.add(childTag);
	}
}
