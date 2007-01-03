package org.seasar.cubby.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFactory;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.convert.ValueConverter;
import org.seasar.cubby.util.CubbyHelperFunctions;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class CubbyFilter implements Filter {
	
	private static final Log LOG = LogFactory.getLog(CubbyFilter.class);
	
	@SuppressWarnings("unused")
	protected FilterConfig config;
	protected ActionFactory actionFactory;
	protected ActionProcessor actionProcesser;
	protected boolean initiallize = false;
	
	public void setActionFactory(ActionFactory actionFactory) {
		this.actionFactory = actionFactory;
	}
	
	public void setActionProcesser(ActionProcessor actionProcessor) {
		this.actionProcesser = actionProcessor;
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}

	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			if (!initiallize) {
				initialize();
				initiallize = true;
			}
			
			
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String appUri = getAppPath(request);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Dispath request[uri=" + appUri + "]");
			}
			ActionContext action = actionFactory
					.createAction(appUri, request, response);
			if (action == null) {
				chain.doFilter(request, response);
			} else {
				@SuppressWarnings("unused")
				ActionResult result = actionProcesser.processAction(action);
			}
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}
	
    protected void initialize() {
        synchronized (this) {
            if (!initiallize) {
                S2Container container = SingletonS2ContainerFactory.getContainer();
                container.injectDependency(this);
                actionFactory.initialize(config);
                ValueConverter valueConverter = (ValueConverter) container
					.getComponent(ValueConverter.class);
                CubbyHelperFunctions.setValueConverter(valueConverter);
            }
        }
    }
    
	public String getAppPath(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String contextPath = request.getContextPath();
		return uri.substring(contextPath.length());
	}

}

