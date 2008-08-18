package org.seasar.cubby.cubbitter.util;

import java.util.List;

public class Pager<T> {

	private boolean prev, next;

	private List<T> subList;

	public Pager(List<T> list, int pageNo, int maxResults) {
		this.prev = pageNo > 1;
		this.next = pageNo * maxResults < list.size();

		int fromIndex = (pageNo - 1) * maxResults;
		if (list.size() < fromIndex) {
			fromIndex = 0;
		}
		int toIndex;
		if (next) {
			toIndex = fromIndex + maxResults;
		} else {
			toIndex = list.size();
		}
		this.subList = list.subList(fromIndex, toIndex);
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public List<T> subList() {
		return subList;
	}

}
