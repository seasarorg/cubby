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
