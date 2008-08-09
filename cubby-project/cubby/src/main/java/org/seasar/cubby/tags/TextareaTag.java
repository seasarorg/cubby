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

import static org.seasar.cubby.tags.TagUtils.addClassName;
import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.formValue;
import static org.seasar.cubby.tags.TagUtils.getOutputValues;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;

/**
 * textareaを出力するタグ
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class TextareaTag extends DynamicAttributesTagSupport {

	private String name;

	private Object value;

	private Integer index;

	/**
	 * name属性を設定します。
	 * 
	 * @param name
	 *            name属性
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * value属性を設定します。
	 * 
	 * @param value
	 *            value属性
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * index属性を設定します。
	 * 
	 * @param index
	 *            index属性
	 */
	public void setIndex(final Integer index) {
		this.index = index;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTag() throws JspException, IOException {
		final JspContext context = this.getJspContext();
		final JspWriter out = context.getOut();
		final ActionErrors errors = errors(context);
		final Map<String, Object> dyn = this.getDynamicAttribute();
		final String[] outputValues = getOutputValues(this, this.name);

		if (this.index == null) {
			if (!errors.getFields().get(this.name).isEmpty()) {
				addClassName(dyn, "fieldError");
			}
		} else {
			if (!errors.getIndexedFields().get(this.name).get(this.index)
					.isEmpty()) {
				addClassName(dyn, "fieldError");
			}
		}
		final Object value = formValue(context, outputValues, this.name,
				this.index, this.value);

		out.write("<textarea name=\"");
		out.write(this.name);
		out.write("\" ");
		out.write(toAttr(dyn));
		out.write(">");
		out.write(CubbyFunctions.out(value));
		out.write("</textarea>");
	}
}
