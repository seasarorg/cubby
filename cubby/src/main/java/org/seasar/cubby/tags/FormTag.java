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

import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.seasar.cubby.internal.controller.FormWrapper;
import org.seasar.cubby.internal.controller.FormWrapperFactory;
import org.seasar.cubby.internal.controller.impl.FormWrapperFactoryImpl;

/**
 * <code>&lt;form&gt;</code> タグを出力します。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class FormTag extends BodyTagSupport implements DynamicAttributes,
		ParamParent {

	/** シリアルバージョン UID */
	private static final long serialVersionUID = 1L;

	/** DynamicAttributes */
	private final Map<String, Object> dynamicAttributes = new HashMap<String, Object>();

	/** フォームのバインディング対象のBean。 */
	private Object value;

	/** 出力する URL を {@link HttpServletResponse#encodeURL(String)} でエンコードするか。 */
	private boolean encodeURL = true;

	/** リンク用の補助クラス。 */
	private final transient LinkSupport linkSupport = new LinkSupport();

	/** フォームオブジェクトのラッパーファクトリ。 */
	private final transient FormWrapperFactory formWrapperFactory = new FormWrapperFactoryImpl();

	/** フォームオブジェクトのラッパー。 */
	private transient FormWrapper formWrapper;

	/**
	 * {@inheritDoc} DynamicAttributeをセットします。
	 */
	public void setDynamicAttribute(final String uri, final String localName,
			final Object value) throws JspException {
		this.dynamicAttributes.put(localName, value);
	}

	/**
	 * DynamicAttributeを取得します。
	 * 
	 * @return DynamicAttribute
	 */
	protected Map<String, Object> getDynamicAttribute() {
		return this.dynamicAttributes;
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
	 * アクションクラスを設定します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 */
	public void setActionClass(final String actionClass) {
		linkSupport.setActionClassName(actionClass);
	}

	/**
	 * アクションメソッドを設定します。
	 * 
	 * @param actionMethod
	 *            アクションメソッド
	 */
	public void setActionMethod(final String actionMethod) {
		linkSupport.setActionMethodName(actionMethod);
	}

	/**
	 * 出力する URL を {@link HttpServletResponse#encodeURL(String)} でエンコードするかを設定します。
	 * 
	 * @param encodeURL
	 *            出力する URL を {@link HttpServletResponse#encodeURL(String)}
	 *            でエンコードする場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	public void setEncodeURL(final boolean encodeURL) {
		this.encodeURL = encodeURL;
	}

	/**
	 * リクエストパラメータを追加します。
	 * 
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            値
	 */
	public void addParameter(final String name, final String value) {
		linkSupport.addParameter(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doStartTag() throws JspException {
		this.formWrapper = formWrapperFactory.create(this.value);
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doEndTag() throws JspException {
		final String contextPath = (String) pageContext.getAttribute(
				ATTR_CONTEXT_PATH, PageContext.REQUEST_SCOPE);
		if (linkSupport.isLinkable()) {
			final String characterEncoding = pageContext.getRequest()
					.getCharacterEncoding();
			final String url = contextPath
					+ linkSupport.getPath(characterEncoding);
			dynamicAttributes.put("action", url);
		}

		if (encodeURL && dynamicAttributes.containsKey("action")) {
			final String url = (String) dynamicAttributes.get("action");
			final HttpServletResponse response = (HttpServletResponse) pageContext
					.getResponse();
			final String encodedUrl = response.encodeURL(url);
			dynamicAttributes.put("action", encodedUrl);
		}

		final JspWriter out = pageContext.getOut();
		try {
			out.write("<form ");
			out.write(toAttr(getDynamicAttribute()));
			out.write(">");
			final BodyContent bodyContent = getBodyContent();
			if (bodyContent != null) {
				bodyContent.writeOut(out);
			}
			out.write("</form>");
		} catch (final IOException e) {
			throw new JspException(e);
		}
		reset();
		return EVAL_PAGE;
	}

	/**
	 * このタグをリセットします。
	 */
	private void reset() {
		linkSupport.clear();
		dynamicAttributes.clear();
		value = null;
		formWrapper = null;
	}

	/**
	 * フォームへ出力する値を取得します。
	 * 
	 * @param name
	 *            フィールド名
	 * @return フォームへ出力する値
	 * @since 1.1.0
	 */
	public String[] getValues(final String name) {
		return formWrapper.getValues(name);
	}

}
