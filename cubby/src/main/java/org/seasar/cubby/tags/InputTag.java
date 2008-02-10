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
import static org.seasar.cubby.tags.TagUtils.contains;
import static org.seasar.cubby.tags.TagUtils.multipleFormValues;
import static org.seasar.cubby.tags.TagUtils.outputValues;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.framework.message.MessageFormatter;
import org.seasar.framework.util.ArrayUtil;

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
 */
public class InputTag extends DynamicAttributesTagSupport {

	private static final String[] MULTIPLE_VALUE_TYPES = new String[] {
			"checkbox", "radio" };

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
		final Map<String, String[]> outputValues = outputValues(context);

		if (this.index == null) {
			if (!errors.getFields().get(this.name).isEmpty()) {
				addClassName(dyn, "fieldError");
			}
		} else {
			if (!errors.getIndexedFields().get(this.name).get(index).isEmpty()) {
				addClassName(dyn, "fieldError");
			}
		}

		if (ArrayUtil.contains(MULTIPLE_VALUE_TYPES, this.type)) {
			if (this.value == null) {
				throw new JspTagException(MessageFormatter.getMessage(
						"ECUB1003", new Object[] {
								Arrays.deepToString(MULTIPLE_VALUE_TYPES),
								"value" }));
			}
			final Object[] values = multipleFormValues(context, outputValues,
					this.name, this.checkedValue);

			out.write("<input type=\"");
			out.write(type);
			out.write("\" name=\"");
			out.write(this.name);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(this.value));// TODO
			out.write("\" ");
			out.write(toAttr(dyn));
			out.write(" ");
			out.write(checked(toString(this.value), values));
			out.write("/>\n");
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
			out.write("/>\n");
		}
	}

	private static String checked(final String value, final Object values) {
		if (value == null || values == null) {
			return "";
		}
		if (contains(values, value)) {
			return "checked=\"true\"";
		} else {
			return "";
		}
	}

}
