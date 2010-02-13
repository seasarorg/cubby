/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.action;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * アクションコンテキストへアクセスするプロキシです。
 * 
 * @since 2.0.5
 * @author baba
 */
public class ActionContextProxy implements ActionContext {

	/** アクションコンテキストのヘルパー */
	private final ActionContextHelper actionContextHelper;

	/**
	 * 指定された要求の属性に設定されたアクションコンテキストへアクセスするプロキシを生成します。
	 * 
	 * @param request
	 *            要求
	 */
	public ActionContextProxy(final ServletRequest request) {
		this.actionContextHelper = new ActionContextHelper(request);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getAction() {
		return subject().getAction();
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getActionClass() {
		return subject().getActionClass();
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getActionMethod() {
		return subject().getActionMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getFormBean() {
		return subject().getFormBean();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isBindRequestParameterToAllProperties() {
		return subject().isBindRequestParameterToAllProperties();
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokeInitializeMethod() {
		subject().invokeInitializeMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokePreRenderMethod() {
		subject().invokePreRenderMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokePostRenderMethod() {
		subject().invokePostRenderMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionErrors getActionErrors() {
		return subject().getActionErrors();
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getFlashMap() {
		return subject().getFlashMap();
	}

	/**
	 * {@inheritDoc}
	 */
	public void clearFlash() {
		subject().clearFlash();
	}

	/**
	 * 要求の属性から被代理オブジェクトを取得します。
	 * 
	 * @return 被代理オブジェクト
	 */
	protected ActionContext subject() {
		return actionContextHelper.getActionContext();
	}

}
