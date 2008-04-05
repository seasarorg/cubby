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

import static org.seasar.cubby.CubbyConstants.ATTR_OUTPUT_VALUES;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.seasar.cubby.dxo.FormDxo;
import org.seasar.framework.container.SingletonS2Container;

/**
 * フォームを出力するタグライブラリ。
 * <p>
 * {@link InputTag}, {@link SelectTag}, {@link TextareaTag}を保持することができます。
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class FormTag extends BodyTagSupport implements DynamicAttributes {

	private static final long serialVersionUID = 3997441280451382093L;

	/**
	 * DynamicAttributes
	 */
	private final Map<String, Object> attrs = new HashMap<String, Object>();

	/**
	 * フォームのバインディング対象のBean。
	 */
	private Object value;

	/**
	 * {@inheritDoc} DynamicAttributeをセットします。
	 */
	public void setDynamicAttribute(final String uri, final String localName,
			final Object value) throws JspException {
		this.attrs.put(localName, value);
	}

	/**
	 * DynamicAttributeを取得します。
	 * 
	 * @return DynamicAttribute
	 */
	protected Map<String, Object> getDynamicAttribute() {
		return this.attrs;
	}

	/**
	 * フォームのバインディング対象のBeanをセットします。
	 * 
	 * @param value
	 *            フォームのバインディング対象のBean
	 */
	public void setValue(final Object value) {
		this.value = value;
	}

	/**
	 * 開始タグ
	 */
	@Override
	public int doStartTag() throws JspException {
		final Map<String, String[]> outputValues = bindFormToOutputValues(this.value);
		pageContext.setAttribute(ATTR_OUTPUT_VALUES, outputValues,
				PageContext.PAGE_SCOPE);

		final JspWriter out = pageContext.getOut();
		try {
			out.write("<form ");
			out.write(toAttr(getDynamicAttribute()));
			out.write(">\n");
		} catch (final IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	private Map<String, String[]> bindFormToOutputValues(final Object value) {
		final Map<String, String[]> outputValues;
		if (value == null) {
			outputValues = Collections.emptyMap();
		} else {
			final FormDxo formDxo = SingletonS2Container
					.getComponent(FormDxo.class);
			final Map<String, String[]> map = new HashMap<String, String[]>();
			formDxo.convert(value, map);
			outputValues = map;
		}
		return outputValues;
	}

	/**
	 * 終了タグ
	 */
	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write("</form>\n");
		} catch (final IOException e) {
			throw new JspException(e);
		}
		pageContext.removeAttribute(ATTR_OUTPUT_VALUES, PageContext.PAGE_SCOPE);
		return EVAL_PAGE;
	}
}
