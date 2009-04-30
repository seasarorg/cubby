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
package org.seasar.cubby.admin.servlet;

import java.util.Collection;
import java.util.Iterator;

class LoopingIterator<E> implements Iterator<E> {

	private Collection<E> collection;

	private Iterator<E> iterator;

	public LoopingIterator(Collection<E> collection) {
		assert collection != null;
		this.collection = collection;
		this.iterator = collection.iterator();
	}

	public boolean hasNext() {
		return !collection.isEmpty();
	}

	public E next() {
		if (!iterator.hasNext()) {
			iterator = collection.iterator();
		}
		return iterator.next();
	}

	public void remove() {
		iterator.remove();
	}

}