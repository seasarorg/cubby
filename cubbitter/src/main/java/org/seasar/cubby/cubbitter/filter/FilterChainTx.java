package org.seasar.cubby.cubbitter.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface FilterChainTx {

	void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException;

}