package org.seasar.cubby.examples.smartdeploy.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.examples.smartdeploy.dto.AuthenticationDto;
import org.seasar.cubby.examples.smartdeploy.entity.User;

public class AuthActionInterceptor implements MethodInterceptor {

	private AuthenticationDto authenticationDto;

	public void setAuthenticationDto(AuthenticationDto authenticationDto) {
		this.authenticationDto = authenticationDto;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		final User user = authenticationDto.getUser();
		if (user == null) {
//			User newUser = new User();
//			newUser.setId(1);
//			newUser.setName("Cubby");
//			authenticationDto.setUser(newUser);

			final Action action = (Action) invocation.getThis();
			action.getFlash().put("notice", "ログインしていません。");
			return new Redirect("/login/");
		}
		return invocation.proceed();
	}

}
