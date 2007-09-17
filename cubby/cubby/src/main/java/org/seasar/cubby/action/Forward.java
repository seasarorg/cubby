package org.seasar.cubby.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

public class Forward extends AbstractActionResult {

	private final Logger logger = Logger.getLogger(this.getClass());

	private final String result;

	public Forward(String result) {
		this.result = result;
	}

	public void execute(final ActionContext context,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Action action = context.getAction();
		String actionClassName = CubbyUtils.getActionClassName(context
				.getComponentDef().getComponentClass());

		String path = null;
		if (result.startsWith("/")) {
			path = result;
		} else if (StringUtil.isEmpty(actionClassName)) {
			path = "/" + result;
		} else {
			path = "/" + actionClassName + "/" + result;
		}
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0001", new String[] { path });
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0002", new String[] { path });
		}
		action.postrender();

		action.getFlash().clear();
	}

	@Override
	public void prerender(ActionContext context) {
		final Action action = context.getAction();
		action.prerender();
	}

}
