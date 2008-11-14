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

import static org.seasar.cubby.util.LogMessages.format;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * パラメータを指定するためのカスタムタグです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ParamTag extends SimpleTagSupport {

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
		final ParamParent parent = (ParamParent) findAncestorWithClass(this,
				ParamParent.class);
		if (parent == null) {
			throw new JspException(format("ECUB1004"));
		}
		final String value;
		if (this.value == null) {
			StringWriter writer = new StringWriter();
			getJspBody().invoke(writer);
			value = writer.toString().trim();
		} else {
			value = this.value;
		}
		parent.addParameter(name, value);
	}

}
