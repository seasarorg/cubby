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
package org.seasar.cubby.action;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

/**
 * 揮発性メッセージへアクセスするプロキシです。
 * 
 * @since 2.1.0
 * @author baba
 */
public class FlashMapProxy implements FlashMap {

	/** アクションコンテキストのヘルパー */
	private final ActionContextHelper actionContextHelper;

	/**
	 * 指定された要求の属性に設定された揮発性メッセージへアクセスするプロキシを生成します。
	 * 
	 * @param request
	 *            要求
	 */
	public FlashMapProxy(final ServletRequest request) {
		this.actionContextHelper = new ActionContextHelper(request);
	}

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return subject().size();
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
	public boolean containsKey(final Object key) {
		return subject().containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsValue(final Object value) {
		return subject().containsValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(final Object key) {
		return subject().get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object put(final String key, final Object value) {
		return subject().put(key, value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object remove(final Object key) {
		return subject().remove(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public void putAll(final Map<? extends String, ? extends Object> t) {
		subject().putAll(t);
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		subject().clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<String> keySet() {
		return subject().keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Object> values() {
		return subject().values();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return subject().entrySet();
	}

	/**
	 * 要求の属性から被代理オブジェクトを取得します。
	 * 
	 * @return 被代理オブジェクト
	 */
	protected Map<String, Object> subject() {
		return actionContextHelper.getActionContext().getFlashMap();
	}
}
