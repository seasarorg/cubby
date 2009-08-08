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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 動的な属性を持つタグのサポートクラスです。
 * 
 * @author agata
 */
abstract class DynamicAttributesSimpleTagSupport extends SimpleTagSupport
		implements DynamicAttributes {

	/** 動的な属性。 */
	private final Map<String, Object> dynamicAttributes = new HashMap<String, Object>();

	/**
	 * {@inheritDoc}
	 */
	public void setDynamicAttribute(final String uri, final String localName,
			final Object value) throws JspException {
		this.dynamicAttributes.put(localName, value);
	}

	/**
	 * 動的な属性を取得します。
	 * 
	 * @return 動的な属性
	 */
	protected Map<String, Object> getDynamicAttributes() {
		return this.dynamicAttributes;
	}

}
