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

import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class FlashHashMap<K, V> implements Map<K, V> {

	private final HttpServletRequest request;

	private final Map<K, V> map;

	private boolean exportedToSession;

	FlashHashMap(final HttpServletRequest request) {
		this.request = request;
		final HttpSession session = request.getSession(false);
		if (session == null) {
			this.map = new HashMap<K, V>();
			this.exportedToSession = false;
		} else {
			final Map<K, V> map = getAttribute(session, ATTR_FLASH);
			if (map == null) {
				this.exportedToSession = false;
				this.map = new HashMap<K, V>();
			} else {
				this.exportedToSession = true;
				this.map = map;
			}
		}
	}

	private void exportToSession() {
		if (!this.exportedToSession) {
			final HttpSession session = request.getSession();
			session.setAttribute(ATTR_FLASH, this.map);
			this.exportedToSession = true;
		}
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
		exportToSession();
		return previousValue;
	}

	public V remove(final Object key) {
		return map.remove(key);
	}

	public void putAll(final Map<? extends K, ? extends V> t) {
		map.putAll(t);
		exportToSession();
	}

	public void clear() {
		map.clear();
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
