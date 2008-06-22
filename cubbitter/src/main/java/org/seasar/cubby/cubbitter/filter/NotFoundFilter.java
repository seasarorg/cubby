package org.seasar.cubby.cubbitter.filter;

/**
 * パターンに一致するURLでアクセスがあった場合、NotFoundを返すFilterクラス
 */

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundFilter implements Filter {

	public static final String DESPATCH_PATH_PATTERN = "despatchPathPattern";

	private Pattern despatchPattern;

	public void init(FilterConfig filterConfig) throws ServletException {

		String str = filterConfig.getInitParameter(DESPATCH_PATH_PATTERN);
		despatchPattern = Pattern.compile(str);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		Matcher m = despatchPattern.matcher(req.getServletPath());

		if (m.find()) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void destroy() {
		
	}
}
