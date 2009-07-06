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
package org.seasar.cubby.internal.util;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * リソースバンドルをラップし、<code>Map</code> インターフェイスによって操作するクラスです。
 * 
 * @author baba
 */
public class ResourceBundleMap extends AbstractMap<String, Object> {

	/** ラップされた <code>ResourceBundle</code> */
	private final ResourceBundle resourceBundle;

	/** エントリの <code>Set</code> */
	private Set<Entry<String, Object>> entrySet;

	/**
	 * 指定されたリソースバンドルをラップする <code>ResourceBundleMap</code> を作成します。
	 * 
	 * @param resourceBundle
	 *            リソースバンドル
	 */
	public ResourceBundleMap(final ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object get(final Object key) {
		return resourceBundle.getString((String) key);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Entry<String, Object>> entrySet() {
		if (this.entrySet == null) {
			final Set<Entry<String, Object>> entrySet = new LinkedHashSet<Entry<String, Object>>();
			final Enumeration<String> keys = resourceBundle.getKeys();
			while (keys.hasMoreElements()) {
				final String key = keys.nextElement();
				final Object value = resourceBundle.getObject(key);
				final Entry<String, Object> entry = new UnmodifiableEntry<String, Object>(
						key, value);
				entrySet.add(entry);
			}
			this.entrySet = Collections.unmodifiableSet(entrySet);
		}
		return entrySet;
	}

	/**
	 * 変更不可能な <code>Entry</code> の実装です。
	 * 
	 * @param <K>
	 *            このエントリが保持するキーの型
	 * @param <V>
	 *            このエントリが保持する値の型
	 * 
	 * @author baba
	 */
	private static class UnmodifiableEntry<K, V> implements Entry<K, V> {

		/** キー。 */
		private final K key;

		/** 値。 */
		private final V value;

		/**
		 * 指定されたキーと値を持つエントリを作成します。
		 * 
		 * @param key
		 *            キー
		 * @param value
		 *            値
		 */
		public UnmodifiableEntry(final K key, final V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * {@inheritDoc}
		 */
		public K getKey() {
			return key;
		}

		/**
		 * {@inheritDoc}
		 */
		public V getValue() {
			return value;
		}

		/**
		 * {@inheritDoc}
		 * <p>
		 * 常に {@link UnsupportedOperationException} をスローします。
		 * </p>
		 */
		public V setValue(final Object value) {
			throw new UnsupportedOperationException();
		}

	}

}
