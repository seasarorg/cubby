package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

public class Redirect extends AbstractActionResult {

	private final Logger logger = Logger.getLogger(this.getClass());

	private final String result;

	public Redirect(String result) {
		this.result = result;
	}

	public void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = null;

		String cpath = request.getContextPath();
		if (result.charAt(0) == '/') {
			path = cpath + result;
		} else {
			String actionClassName = CubbyUtils.getActionClassName(context
					.getComponentDef().getComponentClass());
			if (StringUtil.isEmpty(actionClassName)) {
				path = cpath + "/" + result;
			} else {
				path = cpath + "/" + actionClassName + "/" + result;

			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("redirect[path=" + path + "]");
		}
		response.sendRedirect(path);
	}

}
