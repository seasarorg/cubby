/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;

public class MockJspFragment extends JspFragment {

	private JspContext context;
	private String body = "";
	private List<JspTag> children = new ArrayList<JspTag>();

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
		for (JspTag child : children) {
			if (child instanceof SimpleTag) {
				SimpleTag simpleTag = (SimpleTag) child;
				simpleTag.setJspContext(getJspContext());
				simpleTag.doTag();
			} else {
				throw new UnsupportedOperationException();
			}
		}
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addChild(JspTag child) {
		children.add(child);
	}
}
