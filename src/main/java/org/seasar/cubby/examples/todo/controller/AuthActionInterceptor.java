package org.seasar.cubby.examples.todo.controller;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Redirect;

public class AuthActionInterceptor implements MethodInterceptor {
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Controller controller = (Controller) invocation.getThis();
		if (controller.getSession().get("user") == null) {
			controller.getFlash().put("notice", "ログインしていません。");
			return new Redirect("/login");
		} else {
			return invocation.proceed();
		}
	}
}
