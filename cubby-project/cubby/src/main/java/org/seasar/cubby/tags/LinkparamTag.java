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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * リクエストパラメータを指定するためのカスタムタグです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class LinkparamTag extends SimpleTagSupport {

	/** パラメータ名。 */
	private String name;

	/** 値。 */
	private String value;

	/**
	 * パラメータ名を設定します。
	 * 
	 * @param name
	 *            パラメータ名
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * 値を設定します。
	 * 
	 * @param value
	 *            値
	 */
	public void setValue(final String value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTag() throws JspException, IOException {
		final LinkTag linkTag = (LinkTag) findAncestorWithClass(this,
				LinkTag.class);
		linkTag.addParameter(name, value);
	}

}
