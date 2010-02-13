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

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

/**
 * アクションエラーへアクセスするプロキシです。
 * 
 * @since 2.0.5
 * @author baba
 */
public class ActionErrorsProxy implements ActionErrors {

	/** アクションコンテキストのヘルパー */
	private final ActionContextHelper actionContextHelper;

	/**
	 * 指定された要求の属性に設定されたアクションエラーへアクセスするプロキシを生成します。
	 * 
	 * @param request
	 *            要求
	 */
	public ActionErrorsProxy(final ServletRequest request) {
		this.actionContextHelper = new ActionContextHelper(request);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return subject().isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(final String message) {
		subject().add(message);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(final String message, final FieldInfo... fieldInfo) {
		subject().add(message, fieldInfo);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(final String message, final String... fieldNames) {
		subject().add(message, fieldNames);
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getAll() {
		return subject().getAll();
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, List<String>> getFields() {
		return subject().getFields();
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Map<Integer, List<String>>> getIndexedFields() {
		return subject().getIndexedFields();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getOthers() {
		return subject().getOthers();
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		subject().clear();
	}

	/**
	 * 要求の属性から被代理オブジェクトを取得します。
	 * 
	 * @return 被代理オブジェクト
	 */
	protected ActionErrors subject() {
		return actionContextHelper.getActionContext().getActionErrors();
	}

}
