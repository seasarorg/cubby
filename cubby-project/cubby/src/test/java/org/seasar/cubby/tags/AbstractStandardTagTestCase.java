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
import java.io.StringWriter;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import javax.servlet.jsp.tagext.Tag;

abstract class AbstractStandardTagTestCase extends AbstractTagTestCase {

	protected void setupBodyTag(BodyTag tag) {
		tag.setPageContext(context);
	}

	protected void doLifecycle(Tag tag) throws JspException, IOException {
		doLifecycle(tag, null);
	}

	protected void doLifecycle(Tag tag, ChildrenFactory childrenFactory)
			throws JspException, IOException {
		int reuslt = tag.doStartTag();
		if (tag instanceof BodyTag) {
			BodyTag bodyTag = (BodyTag) tag;
			if (reuslt == BodyTag.EVAL_BODY_BUFFERED) {
				BodyContent bodyContent = (MockBodyContent) context.pushBody();
				bodyTag.setBodyContent(bodyContent);
			}
			bodyTag.doInitBody();
			if (childrenFactory != null) {
				List<JspTag> children = childrenFactory.create();
				if (children != null) {
					for (JspTag child : children) {
						if (child instanceof SimpleTag) {
							SimpleTag simpleTag = (SimpleTag) child;
							simpleTag.setJspBody(jspBody);
							simpleTag.setJspContext(context);
							simpleTag.setParent(tag);
						} else {
							throw new UnsupportedOperationException();
						}
						jspBody.addChild(child);
					}
				}
			}
			jspBody.invoke(new StringWriter());
			bodyTag.doAfterBody();
			if (reuslt == BodyTag.EVAL_BODY_BUFFERED) {
				context.popBody();
			}
		}
		tag.doEndTag();
	}

	interface ChildrenFactory {
		List<JspTag> create();
	}

}
