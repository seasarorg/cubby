package org.seasar.cubby.controller.filters;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;

/**
 * アクションメソッドを実行するフィルターです。
 * 
 * @author agata
 */
public class InvocationFilter implements ActionFilter {
	private static final Object[] EMPTY_ARGS = new Object[0];

	private static final Log LOG = LogFactory.getLog(InvocationFilter.class);

	public ActionResult doFilter(final ActionContext action,
			final ActionFilterChain chain) throws Throwable {
		return invokeActionMethod(action.getController(), action.getMethod());
	}

	ActionResult invokeActionMethod(final Controller controller,
			final Method method) throws Throwable {
		try {
			ActionResult result = (ActionResult) method.invoke(controller,
					EMPTY_ARGS);
			return result;
		} catch (InvocationTargetException ex) {
			LOG.error(ex.getMessage(), ex);
			throw ex.getCause();
		}
	}
}
