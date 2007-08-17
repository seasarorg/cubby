package org.seasar.cubby.controller.impl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;

public class ActionProcessorImpl implements ActionProcessor {

	private final Logger logger = Logger.getLogger(this.getClass());

	private ActionContext context;

	private CubbyConvention cubbyConvention;

	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	public void setCubbyConvention(final CubbyConvention cubbyConvention) {
		this.cubbyConvention = cubbyConvention;
	}

	public void process(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Throwable {
		String path = CubbyUtils.getPath(request);
		if (logger.isDebugEnabled()) {
			logger.debug("request path=" + path);
		}
		ActionDef actionDef = cubbyConvention.fromPathToActionDef(path);
		if (actionDef != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("dispatch to action ["
						+ actionDef.getComponentDef().getComponentName()
						+ "], method [" + actionDef.getMethod().getName()
						+ "]");
			}
			context.setActionDef(actionDef);
			ActionResult result = context.invoke();
			if (result != null) {
				result.execute(context, request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

}
