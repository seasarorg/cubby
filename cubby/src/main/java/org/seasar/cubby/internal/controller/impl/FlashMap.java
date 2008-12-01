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
package org.seasar.cubby.internal.controller.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class FlashMap<K, V> implements Map<K, V> {

	private static final String ATTRIBUTE_NAME = FlashMap.class.getName()
			+ ".MAP";

	private final HttpServletRequest request;

	private final Map<K, V> map;

	FlashMap(final HttpServletRequest request) {
		this.request = request;
		this.map = buildMap(request);
	}

	private Map<K, V> buildMap(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			final Map<K, V> map = getAttribute(session, ATTRIBUTE_NAME);
			if (map != null) {
				return map;
			}
		}
		return new ConcurrentHashMap<K, V>();
	}

	private void export(final HttpSession session) {
		session.setAttribute(ATTRIBUTE_NAME, this.map);
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean containsKey(final Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(final Object value) {
		return map.containsValue(value);
	}

	public V get(final Object key) {
		return map.get(key);
	}

	public V put(final K key, final V value) {
		final V previousValue = map.put(key, value);
		export(request.getSession());
		return previousValue;
	}

	public V remove(final Object key) {
		final V removedValue = map.remove(key);
		final HttpSession session = request.getSession(false);
		if (session != null) {
			export(session);
		}
		return removedValue;
	}

	public void putAll(final Map<? extends K, ? extends V> t) {
		map.putAll(t);
		export(request.getSession());
	}

	public void clear() {
		map.clear();
		final HttpSession session = request.getSession(false);
		if (session != null) {
			export(session);
		}
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public Collection<V> values() {
		return map.values();
	}

	public Set<Entry<K, V>> entrySet() {
		return map.entrySet();
	}

	@SuppressWarnings("unchecked")
	private static <T> T getAttribute(final HttpSession session,
			final String name) {
		return (T) session.getAttribute(name);
	}

}
