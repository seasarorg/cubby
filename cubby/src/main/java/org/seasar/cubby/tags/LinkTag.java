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
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.framework.container.SingletonS2Container;

/**
 * 指定されたアクションクラス、アクションメソッドへリンクする URL を特定の属性にもつタグを出力するカスタムタグです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class LinkTag extends DynamicAttributesTagSupport {

	/** リクエストパラメータの {@link Map} */
	private final Map<String, List<String>> parameters = new HashMap<String, List<String>>();

	/** 出力するタグ。 */
	private String tag;

	/** リンクする URL を出力する属性。 */
	private String attribute;

	/** アクションクラス。 */
	private String actionclass;

	/** アクションメソッド。 */
	private String actionmethod;

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
	 * @param attribute
	 *            リンクする URL を出力する属性
	 */
	public void setAttribute(final String attribute) {
		this.attribute = attribute;
	}

	/**
	 * アクションクラスを設定します。
	 * 
	 * @param actionclass
	 *            アクションクラス
	 */
	public void setActionclass(final String actionclass) {
		this.actionclass = actionclass;
	}

	/**
	 * アクションメソッドを設定します。
	 * 
	 * @param actionmethod
	 *            アクションメソッド
	 */
	public void setActionmethod(final String actionmethod) {
		this.actionmethod = actionmethod;
	}

	/**
	 * リクエストパラメータを追加します。
	 * 
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            値
	 */
	void addParameter(final String name, final String value) {
		if (!parameters.containsKey(name)) {
			parameters.put(name, new ArrayList<String>());
		}
		final List<String> values = parameters.get(name);
		values.add(value);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doTag() throws JspException, IOException {
		final StringWriter writer = new StringWriter();
		this.getJspBody().invoke(writer);

		final Class<? extends Action> actionClass;
		try {
			actionClass = forName(actionclass);
		} catch (final ClassNotFoundException e) {
			throw new JspTagException(e);
		}

		final Map<String, String[]> parameters = new HashMap<String, String[]>();
		for (final Entry<String, List<String>> entry : this.parameters
				.entrySet()) {
			parameters.put(entry.getKey(), entry.getValue().toArray(
					new String[0]));
		}

		final PathResolver pathResolver = SingletonS2Container
				.getComponent(PathResolver.class);
		final String contextPath = (String) getJspContext().getAttribute(
				ATTR_CONTEXT_PATH, PageContext.REQUEST_SCOPE);
		final String redirectPath = contextPath
				+ pathResolver.toRedirectPath(actionClass, actionmethod,
						parameters);
		getDynamicAttribute().put(attribute, redirectPath);

		final JspWriter out = getJspContext().getOut();
		try {
			out.write("<");
			out.write(tag);
			out.write(" ");
			out.write(toAttr(getDynamicAttribute()));
			out.write(">");
			out.write(writer.toString());
			out.write("</");
			out.write(tag);
			out.write(">");
		} catch (final IOException e) {
			throw new JspTagException(e);
		}
	}

	/**
	 * 特定の型のクラスオブジェクトを返します。
	 * 
	 * @param <T>
	 *            型
	 * @param className
	 *            クラス名
	 * @return クラスオブジェクト
	 * @throws ClassNotFoundException
	 *             指定されたクラスが見つからない場合
	 */
	@SuppressWarnings("unchecked")
	private static <T> Class<T> forName(final String className)
			throws ClassNotFoundException {
		return (Class<T>) Class.forName(className);
	}

}
