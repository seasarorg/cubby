package org.seasar.cubby.controller.impl;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.cubby.exception.ActionRuntimeException;
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

	public void process(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain chain)
			throws Throwable {
		final String path = CubbyUtils.getPath(request);

		final ActionDef actionDef = cubbyConvention.fromPathToActionDef(
				request, path);
		if (actionDef != null) {
			context.initialize(actionDef);
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0004", new Object[] { path });
				logger.log("DCUB0005", new Object[] { context.getMethod() });
			}
			final ActionResult result = context.invoke();
			if (result == null) {
				throw new ActionRuntimeException("ECUB0001",
						new Object[] { context.getMethod() });
			}
			result.execute(context, request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

}
