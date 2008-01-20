package org.seasar.cubby.util;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>();

	public static HttpServletRequest getRequest() {
		return REQUEST.get();
	}

	public static void setRequest(final HttpServletRequest request) {
		REQUEST.set(request);
	}

	public static void remove() {
		REQUEST.remove();
	}

}
