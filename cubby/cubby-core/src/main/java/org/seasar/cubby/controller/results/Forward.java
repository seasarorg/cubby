package org.seasar.cubby.controller.results;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.util.StringUtils;

public class Forward implements ActionResult {

	private static final Log LOG = LogFactory.getLog(Forward.class);
	
	private final String result;

	public Forward(String result) {
		this.result = result;
	}

	public void execute(ActionContext action)
			throws ServletException, IOException {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		HttpServletResponse response = action.getResponse();
		String cname = action.getControllerName();

		String path = null;
		if (result.startsWith("/")) {
			path = result;
		} else if (StringUtils.isEmpty(cname)) {
			path = "/" + result;
		} else {
			path = "/" + cname + "/" + result;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("BEGIN forward[path=" + path + "]");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		if (LOG.isDebugEnabled()) {
			LOG.debug("END forward[path=" + path + "]");
		}
		action.getController().postrender();
		controller.getFlash().clear();
	}
}
