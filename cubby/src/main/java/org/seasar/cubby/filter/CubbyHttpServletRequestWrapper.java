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
package org.seasar.cubby.filter;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
import static org.seasar.cubby.CubbyConstants.ATTR_MESSAGES;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

/**
 * 特別な属性を取得するためのリクエストのラッパです。
 * <p>
 * 以下のような属性を使用することができます。 <table><thead>
 * <tr>
 * <th>属性名</th>
 * <th>値</th>
 * <th>型</th>
 * </tr>
 * </thead><tbody>
 * <tr>
 * <td>{@link CubbyConstants#ATTR_CONTEXT_PATH}</td>
 * <td>コンテキストパス</td>
 * <td>{@link String}</td>
 * </tr>
 * <tr>
 * <td>{@link CubbyConstants#ATTR_ACTION}</td>
 * <td>アクション</td>
 * <td>{@link org.seasar.cubby.action.Action}</td>
 * </tr>
 * <tr>
 * <td>{@link CubbyConstants#ATTR_MESSAGES}</td>
 * <td>メッセージリソース</td>
 * <td>{@link java.util.Map}</td>
 * </tr>
 * <tr>
 * <td>アクションのプロパティ名</td>
 * <td>アクションのプロパティ値</td>
 * <td>任意</td>
 * </tr>
 * </table> これらの属性は通常の属性よりも優先されるのでご注意ください。
 * </p>
 * 
 * @author baba
 */
public class CubbyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/** アクションのコンテキスト。 */
	private final ActionContext context;

	/**
	 * インスタンス化します。
	 * 
	 * @param request
	 *            ラップするリクエスト
	 * @param context
	 *            アクションのコンテキスト
	 */
	public CubbyHttpServletRequestWrapper(final HttpServletRequest request,
			final ActionContext context) {
		super(request);

		this.context = context;
	}

	/**
	 * リクエストの属性を取得します。
	 * 
	 * @param name
	 *            属性名
	 */
	@Override
	public Object getAttribute(final String name) {
		final Object attribute;
		if (ATTR_CONTEXT_PATH.equals(name)) {
			attribute = this.getContextPath();
		} else if (ATTR_ACTION.equals(name)) {
			attribute = context.getAction();
		} else if (ATTR_MESSAGES.equals(name)) {
			attribute = ThreadContext.getMessagesMap();
		} else {
			if (context.isInitialized()) {
				final ComponentDef componentDef = context.getComponentDef();
				final Class<?> concreteClass = componentDef.getConcreteClass();
				final BeanDesc beanDesc = BeanDescFactory
						.getBeanDesc(concreteClass);
				if (beanDesc.hasPropertyDesc(name)) {
					final PropertyDesc propertyDesc = beanDesc
							.getPropertyDesc(name);
					if (propertyDesc.isReadable()) {
						attribute = propertyDesc.getValue(context.getAction());
					} else {
						attribute = super.getAttribute(name);
					}
				} else {
					attribute = super.getAttribute(name);
				}
			} else {
				attribute = super.getAttribute(name);
			}
		}
		return attribute;
	}

	/**
	 * 属性名の列挙を返します。
	 * 
	 * @return 属性名の列挙
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getAttributeNames() {
		final List attributeNames = new ArrayList();

		attributeNames.add(ATTR_ACTION);

		final Class<?> concreteClass = context.getComponentDef()
				.getConcreteClass();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(concreteClass);
		for (int i = 0; i < beanDesc.getPropertyDescSize(); i++) {
			final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(i);
			if (propertyDesc.isReadable()) {
				attributeNames.add(propertyDesc.getPropertyName());
			}
		}

		final Enumeration defaultAttributeNames = super.getAttributeNames();
		while (defaultAttributeNames.hasMoreElements()) {
			attributeNames.add(defaultAttributeNames.nextElement());
		}
		return new IteratorEnumeration(attributeNames.iterator());
	}

	private static class IteratorEnumeration<T> implements Enumeration<T> {

		private final Iterator<T> iterator;

		private IteratorEnumeration(final Iterator<T> iterator) {
			this.iterator = iterator;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		/**
		 * {@inheritDoc}
		 */
		public T nextElement() {
			return iterator.next();
		}

	}

}
