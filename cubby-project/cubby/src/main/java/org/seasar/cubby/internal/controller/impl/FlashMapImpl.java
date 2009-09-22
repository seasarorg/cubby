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
package org.seasar.cubby.internal.controller.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.cubby.action.FlashMap;
import org.seasar.cubby.internal.controller.ThreadContext;

/**
 * {@link FlashMap} の実装です。
 * 
 * @author baba
 */
class FlashMapImpl implements FlashMap {

	/** セッションの属性に格納する {@link Map} のキー。 */
	private static final String ATTRIBUTE_NAME = FlashMapImpl.class.getName()
			+ ".MAP";

	/** 要求。 */
	private final HttpServletRequest request;

	/** 実際に値を格納する {@link Map}。 */
	private final Map<String, Object> map;

	/**
	 * インスタンス化します。
	 * <p>
	 * 内部で使用する要求は {@link ThreadContext} から取得します。
	 * </p>
	 */
	public FlashMapImpl() {
		this(ThreadContext.getRequest());
	}

	/**
	 * インスタンス化します。
	 * 
	 * @param request
	 *            要求
	 */
	public FlashMapImpl(final HttpServletRequest request) {
		this.request = request;
		this.map = buildMap(request);
	}

	private Map<String, Object> buildMap(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			final Map<String, Object> map = getAttribute(session,
					ATTRIBUTE_NAME);
			if (map != null) {
				return map;
			}
		}
		return createMap();
	}

	protected Map<String, Object> createMap() {
		return new ConcurrentHashMap<String, Object>();
	}

	private void export(final HttpSession session) {
		session.setAttribute(ATTRIBUTE_NAME, this.map);
	}

	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return map.size();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsKey(final Object key) {
		return map.containsKey(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object get(final Object key) {
		return map.get(key);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object put(final String key, final Object value) {
		final Object previousValue = map.put(key, value);
		export(request.getSession());
		return previousValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object remove(final Object key) {
		final Object removedValue = map.remove(key);
		final HttpSession session = request.getSession(false);
		if (session != null) {
			export(session);
		}
		return removedValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public void putAll(final Map<? extends String, ? extends Object> t) {
		map.putAll(t);
		export(request.getSession());
	}

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		map.clear();
		final HttpSession session = request.getSession(false);
		if (session != null) {
			export(session);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<String> keySet() {
		return map.keySet();
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<Object> values() {
		return map.values();
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}

	@SuppressWarnings("unchecked")
	private static <T> T getAttribute(final HttpSession session,
			final String name) {
		return (T) session.getAttribute(name);
	}

}
