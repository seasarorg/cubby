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

import org.seasar.cubby.internal.controller.FormWrapper;
import org.seasar.cubby.internal.controller.FormWrapperFactory;
import org.seasar.cubby.internal.controller.impl.FormWrapperFactoryImpl;
import org.seasar.cubby.util.LinkBuilder;

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
	private final LinkSupport linkSupport = new LinkSupport();

	/** リンクビルダ。 */
	private final LinkBuilder linkBuilder = new LinkBuilder();

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
		final FormWrapperFactory formWrapperFactory = new FormWrapperFactoryImpl();
		this.formWrapper = formWrapperFactory.create(this.value);
		return EVAL_BODY_BUFFERED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int doEndTag() throws JspException {
		final String contextPath = getContextPath(pageContext);
		if (linkSupport.isLinkable()) {
			final String characterEncoding = pageContext.getRequest()
					.getCharacterEncoding();
			final String url = contextPath
					+ linkSupport.getPath(characterEncoding);
			dynamicAttributes.put("action", url);
		}

		if (encodeURL && dynamicAttributes.containsKey("action")) {
			final HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			final HttpServletResponse response = (HttpServletResponse) pageContext
					.getResponse();
			final String actionPath = (String) dynamicAttributes.get("action");
			final String url;
			try {
				url = linkBuilder.file(actionPath).toLink(request);
			} catch (final MalformedURLException e) {
				throw new JspException(e);
			}
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
		linkBuilder.clear();
		dynamicAttributes.clear();
		value = null;
		formWrapper = null;
	}

	/**
	 * フォームオブジェクトのラッパーを取得します。
	 * 
	 * @return フォームオブジェクトのラッパー
	 */
	public FormWrapper getFormWrapper() {
		return formWrapper;
	}

}
