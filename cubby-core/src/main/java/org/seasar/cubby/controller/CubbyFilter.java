package org.seasar.cubby.controller;

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
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class CubbyFilter implements Filter {
	
	private static final Log LOG = LogFactory.getLog(CubbyFilter.class);
	
	private ActionProcessor processor;

	private boolean initiallize = false;
	
	public void setActionFactory(ActionProcessor actionFactory) {
		this.processor = actionFactory;
	}
	
	public void init(final FilterConfig config) throws ServletException {}

	public void destroy() {}

	public void doFilter(final ServletRequest req, final ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			initialize();
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			processor.execute(request, response, chain);
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
			throw new ServletException(e);
		}
	}
	
    private void initialize() {
		if (!initiallize) {
	        synchronized (this) {
	            if (!initiallize) {
	                S2Container container = SingletonS2ContainerFactory.getContainer();
	                container.injectDependency(this);
	                processor.initialize();
	            }
	        }
			initiallize = true;
		}
    }    
}

