package org.seasar.cubby.examples.todo.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.examples.todo.entity.User;

public class AuthActionInterceptor implements MethodInterceptor {

	private HttpServletRequest request;

	public void setHttpServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		final User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			final Action action = (Action) invocation.getThis();
			action.getFlash().put("notice", "ログインしていません。");
			return new Redirect("/todo/login/");
		}
		return invocation.proceed();
	}

}
