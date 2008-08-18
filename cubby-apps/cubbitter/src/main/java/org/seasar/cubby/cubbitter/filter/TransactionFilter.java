package org.seasar.cubby.cubbitter.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.framework.container.SingletonS2Container;

public class TransactionFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterChainTx filterChainTx = SingletonS2Container
				.getComponent(FilterChainTx.class);
		filterChainTx.doFilter(request, response, chain);
	}

}
