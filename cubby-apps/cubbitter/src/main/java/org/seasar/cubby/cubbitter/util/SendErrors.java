package org.seasar.cubby.cubbitter.util;

import static javax.servlet.http.HttpServletResponse.*;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.SendError;

public class SendErrors {

	public static final SendError FORBIDDEN = new SendError(SC_FORBIDDEN);

	public static final SendError NOT_FOUND = new SendError(SC_NOT_FOUND);

	public static final SendError REQUEST_BASIC_AUTHENTICATION = new SendError(
			SC_UNAUTHORIZED) {

		@Override
		public void execute(Action action, Class<? extends Action> actionClass,
				Method method, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			response.setHeader("WWW-Authenticate", "BASIC realm=\"users\"");
			super.execute(action, actionClass, method, request, response);
		}

	};

}
