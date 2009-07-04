/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import static org.seasar.cubby.tags.TagUtils.addCSSClassName;
import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.formValue;
import static org.seasar.cubby.tags.TagUtils.getFormWrapper;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.controller.FormWrapper;

/**
 * <code>&lt;textarea&gt;</code> タグを出力します。
 * 
 * @author agata
 * @author baba
 */
public class TextareaTag extends DynamicAttributesSimpleTagSupport {

	/** <code>name</code> 属性。 */
	private String name;

	/** <code>value</code> 属性。 */
	private Object value;

	/** <code>index</code> 属性。 */
	private Integer index;

	/**
	 * <code>name</code> 属性を設定します。
	 * 
	 * @param name
	 *            <code>name</code> 属性
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * <code>value</code> 属性を設定します。
	 * 
	 * @param value
	 *            <code>value</code> 属性
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * <code>index</code> 属性を設定します。
	 * 
	 * @param index
	 *            <code>index</code> 属性
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
		final Map<String, Object> dyn = this.getDynamicAttributes();
		final FormWrapper formWrapper = getFormWrapper(this);

		if (this.index == null) {
			if (!errors.getFields().get(this.name).isEmpty()) {
				addCSSClassName(dyn, "fieldError");
			}
		} else {
			if (!errors.getIndexedFields().get(this.name).get(this.index)
					.isEmpty()) {
				addCSSClassName(dyn, "fieldError");
			}
		}
		final Object value = formValue(context, formWrapper, this.name,
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
