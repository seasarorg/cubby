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
package org.seasar.cubby.controller.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.dxo.FormDxo;
import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.log.Logger;

/**
 * アクションメソッドの実行時コンテキストの実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ActionContextImpl implements ActionContext {

	/** ロガー。 */
	private static final Logger logger = Logger
			.getLogger(ActionContextImpl.class);

	/** 空の引数。 */
	private static final Object[] EMPTY_ARGS = new Object[0];

	/** アクションの定義。 */
	private ActionDef actionDef;

	/** アクション。 */
	private Action action;

	/** リクエストパラメータとフォームオブジェクトを変換する DXO。 */
	private FormDxo formDxo;

	/**
	 * {@inheritDoc}
	 */
	public void initialize(final ActionDef actionDef) {
		this.actionDef = actionDef;
		this.action = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isInitialized() {
		return this.actionDef != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public FormDxo getFormDxo() {
		return formDxo;
	}

	/**
	 * リクエストパラメータとフォームオブジェクトを変換する DXO を設定します。
	 * 
	 * @param formDxo
	 *            リクエストパラメータとフォームオブジェクトを変換する DXO
	 */
	public void setFormDxo(final FormDxo formDxo) {
		this.formDxo = formDxo;
	}

	/**
	 * {@inheritDoc}
	 */
	public ComponentDef getComponentDef() {
		return actionDef.getComponentDef();
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return actionDef.getMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public Action getAction() {
		if (action == null) {
			action = (Action) actionDef.getComponentDef().getComponent();
		}
		return action;
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult invoke() throws Exception {
		try {
			final ActionResult result = (ActionResult) actionDef.getMethod()
					.invoke(getAction(), EMPTY_ARGS);
			return result;
		} catch (final InvocationTargetException ex) {
			logger.log(ex);
			final Throwable target = ex.getTargetException();
			if (target instanceof Error) {
				throw (Error) target;
			} else if (target instanceof RuntimeException) {
				throw (RuntimeException) target;
			} else {
				throw (Exception) target;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws ActionRuntimeException
	 *             &#064;Formでフォームオブジェクトとなるプロパティを指定しているが、そのプロパティが
	 *             <code>null</code> だった場合
	 */
	public Object getFormBean() {
		final Object formBean;
		final Action action = getAction();
		final Form form = actionDef.getMethod().getAnnotation(Form.class);
		if (form != null && !form.binding()) {
			formBean = null;
		} else {
			if (form == null || Form.THIS.equals(form.value())) {
				formBean = action;
			} else {
				final String propertyName = form.value();
				final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action
						.getClass());
				final PropertyDesc propertyDesc = beanDesc
						.getPropertyDesc(propertyName);
				formBean = propertyDesc.getValue(action);
				if (formBean == null) {
					throw new ActionRuntimeException("ECUB0102",
							new Object[] { propertyName });
				}
			}
		}
		return formBean;
	}

}
