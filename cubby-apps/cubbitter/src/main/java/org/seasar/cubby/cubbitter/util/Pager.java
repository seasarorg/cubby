package org.seasar.cubby.cubbitter.util;

public class Pager {

	private final boolean prev, next;

	private final int prevPageNo, nextPageNo;

	private final int firstResult, maxResults;

	public Pager(long count, int pageNo, int maxResults) {
		this.prev = pageNo > 1;
		this.next = pageNo * maxResults < count;
		if (prev) {
			prevPageNo = pageNo - 1;
		} else {
			prevPageNo = 1;
		}
		if (next) {
			nextPageNo = pageNo + 1;
		} else {
			nextPageNo = (int) count / maxResults;
		}

		int firstResult = (pageNo - 1) * maxResults;
		if (count < firstResult) {
			this.firstResult = 0;
		} else {
			this.firstResult = firstResult;
		}
		this.maxResults = maxResults;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public int getPrevPageNo() {
		return prevPageNo;
	}

	public int getNextPageNo() {
		return nextPageNo;
	}

}
