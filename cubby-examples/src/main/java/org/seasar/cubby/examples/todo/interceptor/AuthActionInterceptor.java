/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
