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
package org.seasar.cubby.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.seasar.framework.exception.IORuntimeException;

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
 * 
 */
public class QueryStringBuilder {

	/**
	 * パラメータ文字列
	 */
	private StringBuilder sb = new StringBuilder();

	/**
	 * エンコード
	 */
	private String encode = "UTF-8";

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
		return sb.toString();
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
		if (sb.length() > 0) {
			sb.append("&");
		}
		try {
			sb.append(URLEncoder.encode(name, encode));
			sb.append("=");
			if (value != null) {
				sb.append(URLEncoder.encode(value.toString(), encode));
			}
		} catch (final UnsupportedEncodingException e) {
			throw new IORuntimeException(e);
		}
	}
}