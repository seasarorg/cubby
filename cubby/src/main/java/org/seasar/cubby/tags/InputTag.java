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

import static org.seasar.cubby.internal.util.LogMessages.format;
import static org.seasar.cubby.tags.TagUtils.addCSSClassName;
import static org.seasar.cubby.tags.TagUtils.contains;
import static org.seasar.cubby.tags.TagUtils.errors;
import static org.seasar.cubby.tags.TagUtils.formValue;
import static org.seasar.cubby.tags.TagUtils.getFormWrapper;
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
import org.seasar.cubby.controller.FormWrapper;

/**
 * <code>&lt;input&gt;</code> タグを出力します。
 * <p>
 * このタグは type 属性により挙動が変わります。
 * <table>
 * <tbody>
 * <tr>
 * <td><code>type</code> 属性が <code>checkbox</code> または </code> radio の場合</td>
 * <td>
 * <code>value</code> 属性をそのまま <code>value</code> 属性として出力します。 フォームオブジェクトのプロパティ値と
 * <code>value</code> 属性が一致した場合 <code>checked=&quot;checked&quot;</code> を出力します。
 * </td>
 * </tr>
 * <tr>
 * <td>上記以外の場合</td>
 * <td>
 * <code>value</code> 属性が指定されている場合はそのまま <code>value</code> 属性として出力します。
 * <code>value</code> 属性が指定されていない場合はフォームオブジェクトのプロパティを <code>value</code>
 * 属性として出力します。</td>
 * </tr>
 * </tbody>
 * </table>
 * バリデーションエラーが発生している場合は、入力された値を復元します。
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class InputTag extends DynamicAttributesSimpleTagSupport {

	private static final Set<String> MULTIPLE_VALUE_TYPES;
	static {
		final Set<String> set = new HashSet<String>();
		set.add("checkbox");
		set.add("radio");
		MULTIPLE_VALUE_TYPES = Collections.unmodifiableSet(set);
	}

	/** <code>type</code> 属性。 */
	private String type;

	/** <code>name</code> 属性。 */
	private String name;

	/** <code>value</code> 属性。 */
	private Object value;

	/** <code>checkedValue</code> 属性。 */
	private String checkedValue;

	/** <code>index</code> 属性。 */
	private Integer index;

	/**
	 * <code>type</code> 属性を設定します。
	 * 
	 * @param type
	 *            <code>type</code> 属性
	 */
	public void setType(final String type) {
		this.type = type;
	}

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
	 * <code>checkedValue</code> 属性を設定します。
	 * 
	 * @param checkedValue
	 *            <code>checkedValue</code> 属性
	 */
	public void setCheckedValue(final String checkedValue) {
		this.checkedValue = checkedValue;
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
		// final String[] outputValues = getOutputValues(this, this.name);

		if (this.index == null) {
			if (!errors.getFields().get(this.name).isEmpty()) {
				addCSSClassName(dyn, "fieldError");
			}
		} else {
			if (!errors.getIndexedFields().get(this.name).get(index).isEmpty()) {
				addCSSClassName(dyn, "fieldError");
			}
		}

		if (MULTIPLE_VALUE_TYPES.contains(this.type)) {
			if (this.value == null) {
				throw new JspTagException(format("ECUB1003",
						MULTIPLE_VALUE_TYPES, "value"));
			}
			final Object[] values = multipleFormValues(context, formWrapper,
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
			out.write(checked(TagUtils.toString(this.value), values));
			out.write("/>");
		} else {
			final Object value = formValue(context, formWrapper, this.name,
					this.index, this.value);

			out.write("<input type=\"");
			out.write(type);
			out.write("\" name=\"");
			out.write(this.name);
			out.write("\" value=\"");
			out.write(CubbyFunctions.out(TagUtils.toString(value)));
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
