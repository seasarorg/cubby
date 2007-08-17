package org.seasar.cubby.controller.impl;

import java.util.Map;

import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.util.Uri;

public class UrlRewriteDispatcher {

	private ActionMethod actionHolder;
	private final Map<String, String> uriParams;

	public UrlRewriteDispatcher(ActionMethod actionHolder, Map<String, String> uriParams) {
		this.actionHolder = actionHolder;
		this.uriParams = uriParams;
	}

	public ActionMethod getActionHolder() {
		return actionHolder;
	}

	public String getResult() {
		StringBuilder builder = new StringBuilder();
		builder.append("/cubby/");
		builder.append(actionHolder.getMethod().getDeclaringClass().getName());
		builder.append("/");
		builder.append(actionHolder.getMethod().getName());
		if (!uriParams.isEmpty()) {
			builder.append("?");
			Uri uri = new Uri();
			for (String key : uriParams.keySet()) {
				String value = uriParams.get(key);
				uri.setParam(key, value);
			}
			builder.append(uri.getQueryString());
		}
		return builder.toString();
	}
}
