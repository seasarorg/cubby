/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import static org.seasar.cubby.tags.TagUtils.getContextPath;
import static org.seasar.cubby.tags.TagUtils.toAttr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;

import org.seasar.cubby.util.LinkBuilder;

/**
 * 指定されたアクションクラス、アクションメソッドへリンクする URL を特定の属性にもつタグを出力するカスタムタグです。
 * 
 * @author baba
 */
public class LinkTag extends BodyTagSupport implements DynamicAttributes,
		ParamParent {

	/** シリアルバージョン UID */
	private static final long serialVersionUID = 1L;

	/** DynamicAttributes */
	private final Map<String, Object> dynamicAttributes = new HashMap<String, Object>();

	/** リンクの補助クラス。 */
	private final LinkSupport linkSupport = new LinkSupport();

	/** リンクビルダ。 */
	private final LinkBuilder linkBuilder = new LinkBuilder();

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
		this.dynamicAttributes.put(localName, value);
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
	 * 出力する URL のプロトコルを設定します。
	 * 
	 * @param protocol
	 *            出力する URL のプロトコル
	 */
	public void setProtocol(final String protocol) {
		linkBuilder.setProtocol(protocol);
	}

	/**
	 * 出力する URL のポートを設定します。
	 * 
	 * @param port
	 *            出力する URL のポート
	 */
	public void setPort(final int port) {
		linkBuilder.setPort(port);
	}

	/**
	 * 要求パラメータを追加します。
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
		final String contextPath = getContextPath(pageContext);
		final String actionPath;
		final HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		final String characterEncoding = request.getCharacterEncoding();
		if (encodeURL) {
			final HttpServletResponse response = (HttpServletResponse) pageContext
					.getResponse();
			actionPath = response.encodeURL(contextPath
					+ linkSupport.getPath(characterEncoding));
		} else {
			actionPath = contextPath + linkSupport.getPath(characterEncoding);
		}

		final String url;
		try {
			url = linkBuilder.file(actionPath).toLink(request);
		} catch (final MalformedURLException e) {
			throw new JspException(e);
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
				dynamicAttributes.put(attr, url);
				out.write("<");
				out.write(tag);
				out.write(" ");
				out.write(toAttr(dynamicAttributes));
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
		linkBuilder.clear();
		dynamicAttributes.clear();
		tag = null;
		attr = null;
		encodeURL = true;
	}

}
