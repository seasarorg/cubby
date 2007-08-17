package org.seasar.cubby.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.convention.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

public class UrlRewriteFilter implements Filter {

	private final Logger logger = Logger.getLogger(this.getClass());

	private final List<Pattern> ignorePathPatterns = new ArrayList<Pattern>();

	public void init(final FilterConfig config) throws ServletException {
		final String ignorePathPatternString = config
				.getInitParameter("ignorePathPattern");
		if (!StringUtil.isEmpty(ignorePathPatternString)) {

			for (final StringTokenizer tokenizer = new StringTokenizer(
					ignorePathPatternString, ","); tokenizer.hasMoreTokens();) {
				final String token = tokenizer.nextToken();
				final Pattern pattern = Pattern.compile(token);
				ignorePathPatterns.add(pattern);
			}
		}
	}

	public void destroy() {
	}

	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final String rewritePath = rewrite((HttpServletRequest) request,
				(HttpServletResponse) response);
		if (!StringUtil.isEmpty(rewritePath)) {
			if (logger.isDebugEnabled()) {
				logger.debug("request forward to [" + rewritePath + "]");
			}
			final RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(rewritePath);
			requestDispatcher.forward(request, response);

		} else {
			chain.doFilter(request, response);
		}
	}

	private String rewrite(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		final String path = CubbyUtils.getPath(request);

		if (isIgnorePath(path)) {
			return null;
		}

		final PathResolver pathResolver = SingletonS2Container
				.getComponent(PathResolver.class);

		final String rewritePath = pathResolver.getRewritePath(path);
		return rewritePath;
	}

	private boolean isIgnorePath(final String path) {
		for (final Pattern pattern : ignorePathPatterns) {
			final Matcher matcher = pattern.matcher(path);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
}
