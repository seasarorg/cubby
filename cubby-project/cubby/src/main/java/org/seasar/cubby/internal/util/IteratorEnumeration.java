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

package org.seasar.cubby.internal.util;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * {@link Iterator 反復子}をラップする{@link Enumeration 列挙}です。
 * 
 * @author baba
 * 
 * @param <E>
 *            要素の型
 */
public class IteratorEnumeration<E> implements Enumeration<E> {

	/** イテレータ。 */
	private final Iterator<E> iterator;

	/**
	 * 指定された{@link Iterator イテレータ}をラップした{@link Enumeration 列挙}をインスタンス化します。
	 * 
	 * @param iterator
	 *            イテレータ
	 */
	public IteratorEnumeration(final Iterator<E> iterator) {
		this.iterator = iterator;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	/**
	 * {@inheritDoc}
	 */
	public E nextElement() {
		return iterator.next();
	}

}
