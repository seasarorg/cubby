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
