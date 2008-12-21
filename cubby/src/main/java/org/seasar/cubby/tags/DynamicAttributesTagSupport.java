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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * DynamicAttributesをフィールドに持つタグの基底クラスです。
 * 
 * @author agata
 * @since 1.0.0
 */
abstract class DynamicAttributesTagSupport extends SimpleTagSupport implements
		DynamicAttributes {

	/**
	 * DynamicAttributes
	 */
	private final Map<String, Object> attrs = new HashMap<String, Object>();

	/**
	 * {@inheritDoc} DynamicAttributesをセットします。 FIXME
	 * 現在はuriを無視しているので、必要であれば対応したほうがよいかも
	 */
	public void setDynamicAttribute(final String uri, final String localName,
			final Object value) throws JspException {
		this.attrs.put(localName, value);
	}

	/**
	 * DynamicAttributesを取得します。
	 * 
	 * @return DynamicAttributes
	 */
	protected Map<String, Object> getDynamicAttribute() {
		return this.attrs;
	}

	/**
	 * PageContextを取得します。
	 * 
	 * @return PageContext
	 */
	protected PageContext getPageContext() {
		return (PageContext) getJspContext();
	}

	/**
	 * HttpServletRequestを取得します。
	 * 
	 * @return HttpServletRequest
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) getPageContext().getRequest();
	}

	/**
	 * オブジェクトを文字列に変換します。 オブジェクトが<code>null</code>の場合、空文字を返します。
	 * 
	 * @param object
	 *            対象のオブジェクト
	 * @return オブジェクトのtoString結果。
	 */
	protected static String toString(final Object object) {
		return object == null ? "" : object.toString();
	}

}
