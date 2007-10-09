package org.seasar.cubby.convention.impl;

import java.util.Map;

import org.seasar.cubby.convention.ForwardInfo;
import org.seasar.cubby.convention.impl.PathResolverImpl.RoutingInfo;

class ForwardInfoImpl implements ForwardInfo {

	private final String rewritePath;

	private final String actionClassName;

	private final String methodName;

	public ForwardInfoImpl(RoutingInfo rewriteInfo, Map<String, String> uriParams) {
		this.rewritePath = rewriteInfo.buildRewritePath(uriParams);
		this.actionClassName = rewriteInfo.getActionClass().getCanonicalName();
		this.methodName = rewriteInfo.getMethod().getName();;
	}

	public String getRewritePath() {
		return rewritePath;
	}

	public String getActionClassName() {
		return actionClassName;
	}

	public String getMethodName() {
		return methodName;
	}

}
