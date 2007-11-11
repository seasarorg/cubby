package org.seasar.cubby.examples.todo.interceptor;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.examples.todo.entity.User;

public class AuthActionInterceptor implements MethodInterceptor {

	public Map<String, Object> sessionScope;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		final User user = (User) sessionScope.get("user");
		if (user == null) {
			final Action action = (Action) invocation.getThis();
			action.getFlash().put("notice", "ログインしていません。");
			return new Redirect("/todo/login/");
		}
		return invocation.proceed();
	}

}
