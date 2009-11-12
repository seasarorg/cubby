package org.seasar.cubby.showcase.todo.helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.plugin.ActionInvocation;
import org.seasar.framework.container.annotation.tiger.Aspect;

public class ActionInvocationHelper implements ActionInvocation {

	private ActionInvocation actionInvocation;

	public void initialize(final ActionInvocation actionInvocation) {
		this.actionInvocation = actionInvocation;
	}

	public ActionContext getActionContext() {
		return actionInvocation.getActionContext();
	}

	public HttpServletRequest getRequest() {
		return actionInvocation.getRequest();
	}

	public HttpServletResponse getResponse() {
		return actionInvocation.getResponse();
	}

	@Aspect("authActionInterceptor")
	public ActionResult proceed() throws Exception {
		return actionInvocation.proceed();
	}

}
