package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.filters.InitializeFilter;
import org.seasar.cubby.controller.filters.InvocationFilter;
import org.seasar.cubby.controller.filters.ValidationFilter;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.Uri;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class ActionProcessorImpl implements ActionProcessor {

	private static final Log LOG = LogFactory.getLog(ActionProcessorImpl.class);

	public static final String[] EMPTY_STRING_ARRAY = new String[] {};

	private static final String DISPACHER_PATH_PREFIX = "/cubby-dispacher/";

	private static Pattern urlRewritePattern = Pattern.compile("([{]([^}]+)[}])([^{]*)");

	private final Map<Pattern, ActionMethod> actionCache = new LinkedHashMap<Pattern, ActionMethod>();

	private final Map<String, ActionMethod> dispatchActionCache = new LinkedHashMap<String, ActionMethod>();

	private final Map<Class, ActionFilter> actionFilters = new LinkedHashMap<Class, ActionFilter>();

	public void addActionFilter(ActionFilter actionFilter) {
		if (actionFilters.containsKey(actionFilter.getClass())) {
			throw new RuntimeException("CUB001");
		}
		actionFilters.put(actionFilter.getClass(), actionFilter);
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録します。");
		}
		S2Container container = SingletonS2ContainerFactory.getContainer();
		ComponentDef[] defs = container.findAllComponentDefs(Action.class);
		for (ComponentDef def : defs) {
			Class concreteClass = def.getComponentClass();
			if (CubbyUtils.isActionClass(concreteClass)) {
				initAction(concreteClass);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録しました。");
		}
	}

	private void initAction(Class c) {
		for (Method m : c.getMethods()) {
			if (CubbyUtils.isActionMethod(m)) {
				initAction(c, m);
			}
		}
	}

	private void initAction(Class c, Method m) {
		String actionFullName = CubbyUtils.getActionFullName(c, m);
		ActionFilterChain chain = createActionFilterChain(c, m);
		List<String> uriConvertNames = new ArrayList<String>();
		Matcher matcher = urlRewritePattern.matcher(actionFullName);
		while (matcher.find()) {
			String name = matcher.group(2);
			String[] names = name.split(",", 2);
			if (names.length == 1) {
				actionFullName = StringUtils.replace(actionFullName, matcher
						.group(1), "([a-zA-Z0-9]+)");
				uriConvertNames.add(matcher.group(2));
			} else {
				actionFullName = StringUtils.replace(actionFullName, matcher
						.group(1), "(" + names[1] + ")");
				uriConvertNames.add(names[0]);
			}
		}
		ActionMethod holder = new ActionMethod(m, chain, uriConvertNames
				.toArray(new String[uriConvertNames.size()]));
		Pattern pattern = Pattern.compile("^" + actionFullName + "$");
		actionCache.put(pattern, holder);
		dispatchActionCache.put(makeDispacherActionCacheKey(holder), holder);
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録[uri=" + actionFullName + ", method=" + m
					+ "uri params="
					+ StringUtils.join(holder.getUriConvertNames()) + "]");
		}
	}

	private String makeDispacherActionCacheKey(ActionMethod holder) {
		StringBuilder builder = new StringBuilder();
		builder.append(holder.getMethod().getDeclaringClass().getName());
		builder.append("/");
		builder.append(holder.getMethod().getName());
		return builder.toString();
	}

	@SuppressWarnings("unchecked")
	private ActionFilterChain createActionFilterChain(Class c, Method m) {
		ActionFilterChain chain = new ActionFilterChain();
		chain.add(actionFilters.get(InitializeFilter.class));
		chain.add(actionFilters.get(ValidationFilter.class));
		chain.add(actionFilters.get(InvocationFilter.class));
		return chain;
	}

	public void execute(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Throwable {

		ActionMethod holder = null;
		Map<String, String> uriParams = new HashMap<String, String>();
		String path = CubbyUtils.getPath(request);
		if (LOG.isDebugEnabled()) {
			LOG.debug("request[path=" + path + "]");
		}
		if (path.startsWith(DISPACHER_PATH_PREFIX)) {
			// format:/cubby-dispacher/(controllerClassName)/(actionMethod)
			String dispatchKey = StringUtils.removeStart(path,
					DISPACHER_PATH_PREFIX);
			holder = dispatchActionCache.get(dispatchKey);
		} else {
			for (Pattern p : actionCache.keySet()) {
				Matcher matcher = p.matcher(path);
				if (matcher.find()) {
					holder = actionCache.get(p);
					for (int i = 1; i < matcher.groupCount() + 1; i++) {
						String group = matcher.group(i);
						String paramName = holder.getUriConvertNames()[i - 1];
						uriParams.put(paramName, group);
					}
					break;
				}
			}
		}
		if (holder == null) {
			chain.doFilter(request, response);
		} else if (!uriParams.isEmpty()) {
			forwardRewriteUrl(request, response, holder, uriParams);
		} else {
			processAction(request, response, holder);
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
