package org.seasar.cubby.cubbitter.interceptor;

/**
 * セッションチェックするInterceptorクラス
 */

import java.util.Map;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.util.Messages;

public class AuthActionInterceptor implements MethodInterceptor {
	public Map<String, Object> sessionScope;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		final Member user = (Member) sessionScope.get("user");
		if (user == null) {
			final Action action = (Action) invocation.getThis();
			action.getFlash().put("notice", Messages.getText("login.msg.sessionError"));
			return new Forward("/top/loginError.jsp");
		}
		return invocation.proceed();
	}
}
