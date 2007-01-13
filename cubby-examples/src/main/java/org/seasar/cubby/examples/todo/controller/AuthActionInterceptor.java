package org.seasar.cubby.examples.todo.controller;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Redirect;
import org.seasar.cubby.examples.todo.entity.User;

public class AuthActionInterceptor implements MethodInterceptor {
	
	HttpServletRequest request;
	public void setHttpServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		User user = getUser();
		if (user == null) {
			Controller controller = (Controller) invocation.getThis();
			controller.getFlash().put("notice", "ログインしていません。");
			return new Redirect("/login");
		} else {
			return invocation.proceed();
		}
	}
	
	public User getUser() {
		return (User) request.getSession().getAttribute("user");
	}
}
