package org.seasar.cubby.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class Uri {

	private String path;
	private Map<String,Object> params = new LinkedHashMap<String, Object>();
	private String encode = "UTF-8";

	public void setPath(String path) {
		this.path = path;
	}

	public void setParam(String name, Object value) {
		params.put(name, value);
	}

	public void setParam(String name, Object[] values) {
		params.put(name, values);
	}

	public String getQueryString() {
		StringBuilder sb = new StringBuilder();
		if (!params.isEmpty()) {
			for (String name : params.keySet()) {
				Object value = params.get(name);
				if (value == null) {
					appendParams(sb, name, value);
				} else if (value.getClass().isArray()) {
					Object[] values = (Object[])value;
					for (Object v : values) {
						appendParams(sb, name, v);
					}
				} else {
					appendParams(sb, name, value);
				}
			}
		}
		return sb.toString();
	}

	private void appendParams(StringBuilder sb, String name, Object value) {
		if(sb.length() > 0) {
			sb.append("&");
		}
		try {
			sb.append(URLEncoder.encode(name, encode));
			sb.append("=");
			if (value != null) {
				sb.append(URLEncoder.encode(value.toString(), encode));
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
}
