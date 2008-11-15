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
package org.seasar.cubby.internal.action.impl;

import static org.seasar.cubby.action.RequestParameterBindingType.NONE;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.internal.beans.BeanDesc;
import org.seasar.cubby.internal.beans.BeanDescFactory;
import org.seasar.cubby.internal.beans.PropertyDesc;

/**
 * アクションのコンテキストの実装です。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ActionContextImpl implements ActionContext {

	/** アクション。 */
	private final Action action;

	/** アクションクラス。 */
	private final Class<? extends Action> actionClass;

	/** アクションメソッド。 */
	private final Method actionMethod;

	/** アクションエラー。 */
	private final ActionErrors actionErrors;

	/** 揮発性メッセージ。 */
	private final Map<String, Object> flashMap;

	/**
	 * インスタンス化します。
	 * 
	 * @param action
	 *            アクション
	 * @param actionClass
	 *            アクションクラス
	 * @param actionMethod
	 *            アクションメソッド
	 * @param actionErrors
	 *            アクションエラー
	 * @param flashMap
	 *            揮発性メッセージ
	 */
	public ActionContextImpl(final Action action,
			final Class<? extends Action> actionClass,
			final Method actionMethod, final ActionErrors actionErrors,
			final Map<String, Object> flashMap) {
		this.action = action;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
		this.actionErrors = actionErrors;
		this.flashMap = flashMap;
	}

	/**
	 * {@inheritDoc}
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<? extends Action> getActionClass() {
		return actionClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getActionMethod() {
		return actionMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getFormBean() {
		final Form form = getForm();
		if (form == null) {
			return action;
		}
		if (form.bindingType() == NONE) {
			return null;
		}
		if (Form.THIS.equals(form.value())) {
			return action;
		}

		final String propertyName = form.value();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(actionClass);
		final PropertyDesc propertyDesc = beanDesc
				.getPropertyDesc(propertyName);
		final Object formBean = propertyDesc.getValue(action);
		if (formBean == null) {
			throw new ActionException(format("ECUB0102", propertyName));
		}
		return formBean;
	}

	/**
	 * 指定されたアクションメソッドを修飾する {@link Form} を取得します。
	 * 
	 * @return {@link Form}、修飾されていない場合はメソッドが定義されたクラスを修飾する {@link Form}
	 *         、クラスも修飾されていない場合は <code>null</code>
	 */
	private Form getForm() {
		final Form form;
		if (actionMethod.isAnnotationPresent(Form.class)) {
			form = actionMethod.getAnnotation(Form.class);
		} else {
			form = actionClass.getAnnotation(Form.class);
		}
		return form;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isBindRequestParameterToAllProperties() {
		final Form form = this.getForm();
		if (form == null) {
			return false;
		}

		final RequestParameterBindingType type = form.bindingType();
		switch (type) {
		case ALL_PROPERTIES:
			return true;
		case ONLY_SPECIFIED_PROPERTIES:
			return false;
		default:
			throw new IllegalStateException(type.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokeInitializeMethod() {
		action.invokeInitializeMethod(actionMethod);
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokePreRenderMethod() {
		action.invokePreRenderMethod(actionMethod);
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokePostRenderMethod() {
		action.invokePostRenderMethod(actionMethod);
	}

	/**
	 * 指定されたメソッドを実行します。
	 * 
	 * @param method
	 *            メソッド
	 */
	protected void invoke(final Method method) {
		try {
			method.invoke(action);
		} catch (final IllegalAccessException e) {
			throw new ActionException(e);
		} catch (final InvocationTargetException e) {
			throw new ActionException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionErrors getActionErrors() {
		return actionErrors;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getFlashMap() {
		return flashMap;
	}

	/**
	 * {@inheritDoc}
	 */
	public void clearFlash() {
		flashMap.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("ActionContext[");
		builder.append("action=").append(action);
		builder.append(",actionClass=").append(actionClass);
		builder.append(",actionMethod=").append(actionMethod);
		builder.append("]");
		return builder.toString();
	}

}
