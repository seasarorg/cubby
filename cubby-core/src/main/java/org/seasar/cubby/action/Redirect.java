package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.StringUtils;

public class Redirect implements ActionResult {
	
	private static final Log LOG = LogFactory.getLog(Redirect.class);
	
	private final String result;

	public Redirect(String result) {
		this.result = result;
	}
	
	public void execute(ActionContext context) throws Exception {
		String path = null;
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();

		String cpath = request.getContextPath();
		if (result.charAt(0) == '/') {
			path = cpath + result;
		} else {
			String cname = context.getActionMethod().getActionClassName();
			if (StringUtils.isEmpty(cname)) {
				path = cpath + "/" + result;
			} else {
				path = cpath + "/" + cname + "/" + result;

			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("redirect[path=" + path + "]");
		}
		response.sendRedirect(path);
	}
}
