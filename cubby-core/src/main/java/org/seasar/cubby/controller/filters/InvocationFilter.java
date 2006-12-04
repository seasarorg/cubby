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

public class InvocationFilter implements ActionFilter {
	private static final Object[] EMPTY_ARGS = new Object[0];
	private static final Log LOG = LogFactory.getLog(InvocationFilter.class);
	
	public ActionResult doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {
		Controller controller = action.getController();
		Method method = action.getMethod();
		try {
			ActionResult result = (ActionResult) method.invoke(controller, EMPTY_ARGS);
			return result;
		} catch (InvocationTargetException ex) {
			LOG.error(ex.getMessage(), ex);
			throw ex.getCause();
		}
	}
}
