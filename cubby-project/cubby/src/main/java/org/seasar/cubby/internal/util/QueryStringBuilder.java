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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * パラメータ文字列を作成します。
 * <p>
 * パラメータ名、値ともURLエンコードされます。デフォルトのエンコードはUTF-8です。
 * 
 * <pre>
 * QueryStringBuilder query = new QueryStringBuilder();
 * query.addParam(&quot;p1&quot;, &quot;v1&quot;);
 * query.addParam(&quot;p2&quot;, null);
 * query.addParam(&quot;p3&quot;, new String[] { &quot;v2&quot;, &quot;v3&quot; });
 * assertEquals(&quot;p1=v1&amp;p2=&amp;p3=v2&amp;p3=v3&quot;, query.toString());
 * </pre>
 * 
 * @author agata
 */
public class QueryStringBuilder {

	/**
	 * パラメータ文字列
	 */
	private final StringBuilder queryString = new StringBuilder();

	/**
	 * エンコード
	 */
	private String encode = "UTF-8";

	/**
	 * URI 部分
	 */
	private final String baseUri;

	/**
	 * URI 部分なしでインスタンスを生成します。
	 * <p>
	 * {@code QueryStringBuilder#toString()}が呼び出された時に、URI 部分は付加されません。
	 * </p>
	 */
	public QueryStringBuilder() {
		this(null);
	}

	/**
	 * URI 部分を指定してインスタンスを生成します。
	 * <p>
	 * {@code QueryStringBuilder#toString()}が呼び出された時に、URI 部分と「?」が付加されます。
	 * </p>
	 * 
	 * @param baseUri
	 *            URI部分
	 */
	public QueryStringBuilder(final String baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * エンコードをセットします。
	 * 
	 * @param encode
	 */
	public void setEncode(final String encode) {
		this.encode = encode;
	}

	/**
	 * パラメータを追加します。
	 * 
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            値。配列の場合、要素数分パラメータが追加されます。
	 */
	public void addParam(final String name, final Object value) {
		if (value != null && value.getClass().isArray()) {
			final Object[] values = (Object[]) value;
			for (final Object v : values) {
				appendParams(name, v);
			}
		} else {
			appendParams(name, value);
		}
	}

	/**
	 * パラメータ文字列を取得します。
	 */
	@Override
	public String toString() {
		if (this.baseUri == null) {
			return queryString.toString();
		} else {
			final StringBuilder baseUrlBuf = new StringBuilder(this.baseUri);
			if (baseUrlBuf.indexOf("?") == -1) {
				baseUrlBuf.append("?");
			} else if (queryString.indexOf("?") < queryString.length()) {
				baseUrlBuf.append("&");
			}
			return baseUrlBuf.toString() + queryString.toString();
		}
	}

	/**
	 * パラメータ文字列を追加します。
	 * 
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            値
	 */
	private void appendParams(final String name, final Object value) {
		if (queryString.length() > 0) {
			queryString.append("&");
		}
		try {
			queryString.append(URLEncoder.encode(name, encode));
			queryString.append("=");
			if (value != null) {
				queryString.append(URLEncoder.encode(value.toString(), encode));
			}
		} catch (final UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
