package org.seasar.cubby.controller.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.util.StringUtils;

public class ActionProcessorImpl implements ActionProcessor {

	private static final Log LOG = LogFactory.getLog(ActionProcessorImpl.class);

	public String processAction(ActionContext action) throws Throwable {
		// TODO resultを処理するクラスに処理を委譲する
		ActionFilterChain actionFilterChain = action.getActionHolder().getFilterChain();
		String result = actionFilterChain.doFilter(action);
		if (result == null) {
			return null;
		}
		if (result.startsWith("@")) {
			redirect(action, result);
		} else {
			forward(action, result);
		}
		return result;
	}

	private void redirect(ActionContext action, String result) throws IOException {
		String path = null;
		HttpServletRequest request = action.getRequest();
		HttpServletResponse response = action.getResponse();

		String cpath = request.getContextPath();
		if (result.charAt(1) == '/') {
			path = cpath + "/" + result.substring(2);
		} else {
			String cname = action.getControllerName();
			if (StringUtils.isEmpty(cname)) {
				path = cpath + "/" + result.substring(1);
			} else {
				path = cpath + "/" + cname + "/" + result.substring(1);

			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("redirect[path=" + path + "]");
		}
		response.sendRedirect(path);
	}

	private void forward(ActionContext action, String result)
			throws ServletException, IOException {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		HttpServletResponse response = action.getResponse();
		String cname = action.getControllerName();

		String path = null;
		if (result.startsWith("/")){
			path = result;
		} else if (StringUtils.isEmpty(cname)) {
			path = "/" + result;
		} else {
			path = "/" + cname + "/" + result;
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("forward[path=" + path + "]");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		controller.getFlash().clear();
	}
}
