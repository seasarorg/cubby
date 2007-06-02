package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ResolveResult;
import org.seasar.cubby.controller.UrlResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.Uri;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class ActionProcessorImpl implements ActionProcessor {

	private static final Log LOG = LogFactory.getLog(ActionProcessorImpl.class);

	public static final String[] EMPTY_STRING_ARRAY = new String[] {};
	private static final String DISPACHER_PATH_PREFIX = "/cubby-dispacher/";

	private UrlResolver urlResolver;
	public void setUrlResolver(UrlResolver urlResolver) {
		this.urlResolver = urlResolver;
	}
	
	@SuppressWarnings("unchecked")
	public void initialize() {
		urlResolver.initialize();
	}

	public void execute(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Throwable {

		String path = CubbyUtils.getPath(request);
		ResolveResult result = urlResolver.resolve(path);
		if (result == null) {
			chain.doFilter(request, response);
		} else if (!result.getUriParams().isEmpty()) {
			forwardRewriteUrl(request, response, result.getActionMethod(), result.getUriParams());
		} else {
			processAction(request, response, result.getActionMethod());
		}
	}

	private void processAction(HttpServletRequest request,
			HttpServletResponse response, ActionMethod holder)
			throws Throwable, Exception {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Class<?> declaringClass = holder.getMethod().getDeclaringClass();
		Action controller = (Action) container
				.getComponent(declaringClass);
		ActionContext context = new ActionContextImpl(request, response,
				controller, holder);
		ActionFilterChain actionFilterChain = context.getActionMethod()
				.getFilterChain();
		ActionResult result = actionFilterChain.doFilter(context);
		if (result != null) {
			result.execute(context);
		}
	}

	private void forwardRewriteUrl(HttpServletRequest request,
			HttpServletResponse response, ActionMethod holder,
			Map<String, String> uriParams) throws Exception{
		String path = getUrlRewriteForwardPath(holder.getMethod(), uriParams);
		if (LOG.isDebugEnabled()) {
			LOG.debug("forward[path=" + path + "]");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
	}

	public String getUrlRewriteForwardPath(Method method,
			Map<String, String> uriParams) {
		StringBuilder builder = new StringBuilder();
		builder.append(DISPACHER_PATH_PREFIX);
		builder.append(method.getDeclaringClass().getName());
		builder.append("/");
		builder.append(method.getName());
		if (!uriParams.isEmpty()) {
			builder.append("?");
			Uri uri = new Uri();
			for (String key : uriParams.keySet()) {
				String value = uriParams.get(key);
				uri.setParam(key, value);
			}
			builder.append(uri.getQueryString());
		}
		return builder.toString();
	}
}
