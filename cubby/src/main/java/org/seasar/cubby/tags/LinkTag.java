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

/**
 * 指定されたアクションクラス、アクションメソッドへリンクする URL を特定の属性にもつタグを出力するカスタムタグです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class LinkTag extends BodyTagSupport implements DynamicAttributes,
		ParamParent {

	/** シリアルバージョン UID */
	private static final long serialVersionUID = 1L;

	/** DynamicAttributes */
	private final Map<String, Object> attrs = new HashMap<String, Object>();

	/** リンクの補助クラス。 */
	private final LinkSupport linkSupport = new LinkSupport();

	/** 出力するタグ。 */
	private String tag;

	/** リンクする URL を出力する属性。 */
	private String attr;

	/** 出力する URL を {@link HttpServletResponse#encodeURL(String)} でエンコードするか。 */
	private boolean encodeURL = true;

	/**
	 * {@inheritDoc} DynamicAttributeをセットします。
	 */
	public void setDynamicAttribute(final String uri, final String localName,
			final Object value) throws JspException {
		this.attrs.put(localName, value);
	}

	/**
	 * 出力するタグを設定します。
	 * 
	 * @param tag
	 *            出力するタグ
	 */
	public void setTag(final String tag) {
		this.tag = tag;
	}

	/**
	 * リンクする URL を出力する属性を設定します。
	 * 
	 * @param attr
	 *            リンクする URL を出力する属性
	 */
	public void setAttr(final String attr) {
		this.attr = attr;
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
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doEndTag() throws JspException {
		final String contextPath = (String) pageContext.getAttribute(
				ATTR_CONTEXT_PATH, PageContext.REQUEST_SCOPE);
		final String url;
		if (encodeURL) {
			final HttpServletResponse response = (HttpServletResponse) pageContext
					.getResponse();
			url = response.encodeURL(contextPath + linkSupport.getPath());
		} else {
			url = contextPath + linkSupport.getPath();
		}

		try {
			final JspWriter out = pageContext.getOut();
			if (tag == null) {
				out.write(url);
				final BodyContent bodyContent = getBodyContent();
				if (bodyContent != null) {
					bodyContent.writeOut(out);
				}
			} else {
				attrs.put(attr, url);
				out.write("<");
				out.write(tag);
				out.write(" ");
				out.write(toAttr(attrs));
				out.write(">");
				final BodyContent bodyContent = getBodyContent();
				if (bodyContent != null) {
					bodyContent.writeOut(out);
				}
				out.write("</");
				out.write(tag);
				out.write(">");
			}
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
		attrs.clear();
		tag = null;
		attr = null;
		encodeURL = true;
	}

}
