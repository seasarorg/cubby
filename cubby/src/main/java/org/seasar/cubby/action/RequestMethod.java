package org.seasar.cubby.action;

/**
 * アクションメソッドを起動する対象となる HTTP メソッド。
 * 
 * @author agata
 * @author baba
 * @since 1.0
 */
public enum RequestMethod {
	/** HTTP GET */
	GET,
	/** HTTP HEAD */
	HEAD,
	/** HTTP POST */
	POST,
	/** HTTP PUT */
	PUT,
	/** HTTP DELETE */
	DELETE,
	/** HTTP OPTIONS */
	OPTIONS,
	/** HTTP TRACE */
	TRACE,
}
