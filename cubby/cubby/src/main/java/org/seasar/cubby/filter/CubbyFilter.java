package org.seasar.cubby.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.log.Logger;

public class CubbyFilter implements Filter {

	private final Logger logger = Logger.getLogger(this.getClass());

	public void init(final FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest req, final ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			final ActionProcessor processor = SingletonS2Container
					.getComponent(ActionProcessor.class);
			processor.process(request, response, chain);
		} catch (final Throwable e) {
			logger.log(e);
			throw new ServletException(e);
		}
	}

}
