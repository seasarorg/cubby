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

import static org.seasar.cubby.internal.util.LogMessages.format;
import static org.seasar.cubby.tags.TagUtils.addClassName;
import static org.seasar.cubby.tags.TagUtils.contains;
import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.formValue;
import static org.seasar.cubby.tags.TagUtils.getOutputValues;
import static org.seasar.cubby.tags.TagUtils.multipleFormValues;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;

/**
 * inputを出力するタグ。
 * <p>
 * 入力検証にエラーがある場合、class属性に「fieldError」を追加します。 なおこのタグはtype属性により挙動が変わります。
 * </p>
 * <ul>
 * <li>type値がcheckbox/radio -
 * value値をvalue属性の値として出力します。フォームオブジェクトの値とvalueが一致した場合checked="checked"を出力します。</li>
 * <li>その他 - value値をvalue属性の値として出力します。</li>
 * </ul>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class InputTag extends DynamicAttributesTagSupport {

	private static final Set<String> MULTIPLE_VALUE_TYPES;
	static {
		Set<String> set = new HashSet<String>();
		set.add("checkbox");
		set.add("radio");
		MULTIPLE_VALUE_TYPES = Collections.unmodifiableSet(set);
	}

	private String type;

	private String name;

	private Object value;

	private String checkedValue;

	private Integer index;

	/**
	 * type属性を設定します。
	 * 
	 * @param type
	 *            type属性
	 */
	public void setType(final String type) {
		this.type = type;
	}

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
	 * checkedValue属性を設定します。
	 * 
	 * @param checkedValue
	 *            checkedValue属性
	 */
	public void setCheckedValue(final String checkedValue) {
		this.checkedValue = checkedValue;
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
			if (!errors.getIndexedFields().get(this.name).get(index).isEmpty()) {
				addClassName(dyn, "fieldError");
			}
		}

		if (MULTIPLE_VALUE_TYPES.contains(this.type)) {
			if (this.value == null) {
				throw new JspTagException(format("ECUB1003",
						MULTIPLE_VALUE_TYPES, "value"));
			}
			final Object[] values = multipleFormValues(context, outputValues,
					this.name, this.checkedValue);

			out.write("<input type=\"");
			out.write(type);
			out.write("\" name=\"");
			out.write(this.name);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(this.value));
			out.write("\" ");
			out.write(toAttr(dyn));
			out.write(" ");
			out.write(checked(toString(this.value), values));
			out.write("/>");
		} else {
			final Object value = formValue(context, outputValues, this.name,
					this.index, this.value);

			out.write("<input type=\"");
			out.write(type);
			out.write("\" name=\"");
			out.write(this.name);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(toString(value)));
			out.write("\" ");
			out.write(toAttr(dyn));
			out.write("/>");
		}
	}

	private static String checked(final String value, final Object values) {
		if (value == null || values == null) {
			return "";
		}
		if (contains(values, value)) {
			return "checked=\"checked\"";
		} else {
			return "";
		}
	}

}
