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
import org.seasar.cubby.action.InitializeMethod;
import org.seasar.cubby.action.PostRenderMethod;
import org.seasar.cubby.action.PreRenderMethod;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;

/**
 * アクションのコンテキストの実装です。
 * 
 * @author baba
 */
public class ActionContextImpl implements ActionContext {

	/** アクション。 */
	private final Object action;

	/** アクションクラス。 */
	private final Class<?> actionClass;

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
	public ActionContextImpl(final Object action, final Class<?> actionClass,
			final Method actionMethod, final ActionErrors actionErrors,
			final Map<String, Object> flashMap) {
		this.action = action;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
		this.actionErrors = actionErrors;
		this.flashMap = flashMap;
		if (action instanceof Action) {
			initialize((Action) action, actionErrors, flashMap);
		}
	}

	private void initialize(final Action action,
			final ActionErrors actionErrors, final Map<String, Object> flashMap) {
		action.setErrors(actionErrors);
		action.setFlash(flashMap);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getAction() {
		return action;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getActionClass() {
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

		final String attributeName = form.value();
		final Object formBean;
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(actionClass);
		if (beanDesc.hasPropertyAttribute(attributeName)) {
			final Attribute attribute = beanDesc
					.getPropertyAttribute(attributeName);
			formBean = attribute.getValue(action);
			if (formBean == null) {
				throw new ActionException(format("ECUB0102", actionClass,
						attributeName));
			}
		} else if (beanDesc.hasFieldAttribute(attributeName)) {
			final Attribute attribute = beanDesc
					.getFieldAttribute(attributeName);
			formBean = attribute.getValue(action);
			if (formBean == null) {
				throw new ActionException(format("ECUB0111", actionClass,
						attributeName));
			}
		} else {
			throw new ActionException(format("ECUB0112", actionClass,
					attributeName));
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
		if (action instanceof Action) {
			((Action) action).invokeInitializeMethod(actionMethod);
		} else if (actionMethod.isAnnotationPresent(InitializeMethod.class)) {
			final InitializeMethod initializeMethod = actionMethod
					.getAnnotation(InitializeMethod.class);
			final String methodName = initializeMethod.value();
			this.invoke(action, methodName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokePreRenderMethod() {
		if (action instanceof Action) {
			((Action) action).invokePreRenderMethod(actionMethod);
		} else if (actionMethod.isAnnotationPresent(PreRenderMethod.class)) {
			final PreRenderMethod preRenderMethod = actionMethod
					.getAnnotation(PreRenderMethod.class);
			final String methodName = preRenderMethod.value();
			this.invoke(action, methodName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokePostRenderMethod() {
		if (action instanceof Action) {
			((Action) action).invokePostRenderMethod(actionMethod);
		} else if (actionMethod.isAnnotationPresent(PostRenderMethod.class)) {
			final PostRenderMethod postRenderMethod = actionMethod
					.getAnnotation(PostRenderMethod.class);
			final String methodName = postRenderMethod.value();
			this.invoke(action, methodName);
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
	 * アクションの指定されたメソッド名のメソッドを実行します。
	 * 
	 * @param methodName
	 *            メソッド名
	 */
	private void invoke(final Object action, final String methodName) {
		try {
			final Method method = action.getClass().getMethod(methodName);
			method.invoke(action);
		} catch (final NoSuchMethodException e) {
			throw new ActionException(e);
		} catch (final IllegalAccessException e) {
			throw new ActionException(e);
		} catch (final InvocationTargetException e) {
			throw new ActionException(e);
		}
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
