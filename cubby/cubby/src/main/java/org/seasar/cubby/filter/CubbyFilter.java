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
import org.seasar.cubby.util.RequestHolder;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;

public class CubbyFilter implements Filter {

	private final Logger logger = Logger.getLogger(this.getClass());

	public void init(final FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		try {
			final HttpServletRequest request = (HttpServletRequest) req;
			final HttpServletResponse response = (HttpServletResponse) res;
			RequestHolder.setRequest(request);

			final S2Container container = SingletonS2ContainerFactory
					.getContainer();
			final ActionProcessor processor = (ActionProcessor) container
					.getComponent(ActionProcessor.class);
			processor.process(request, response, chain);
		} catch (final Throwable e) {
			logger.log(e);
			throw new ServletException(e);
		} finally {
			RequestHolder.remove();
		}
	}

}
