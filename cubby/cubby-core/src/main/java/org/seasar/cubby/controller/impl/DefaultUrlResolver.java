package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.controller.UrlResolver;
import org.seasar.cubby.controller.filters.InitializeFilter;
import org.seasar.cubby.controller.filters.InvocationFilter;
import org.seasar.cubby.controller.filters.ValidationFilter;
import org.seasar.cubby.util.ClassUtils;

public class DefaultUrlResolver implements UrlResolver {
	
	private static final Log LOG = LogFactory.getLog(ActionProcessorImpl.class);
	
	private static Pattern urlRewritePattern = Pattern.compile("([{]([^}]+)[}])([^{]*)");
	private final Map<Pattern, ActionMethod> actionCache = new LinkedHashMap<Pattern, ActionMethod>();
	private final Map<Class, ActionFilter> actionFilters = new LinkedHashMap<Class, ActionFilter>();
	private final Map<String, ActionMethod> dispatchActionCache = new LinkedHashMap<String, ActionMethod>();
	private static final String DISPACHER_PATH_PREFIX = "/cubby-dispacher/";

	public void add(String patternStr, Class<? extends Action> c, String methodName) {
		Method m = ClassUtils.getMethod(c, methodName, null);
		String actionFullName = patternStr;
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

	public ActionMethod resolve(String url) {
		ActionMethod method = null;
		Map<String, String> uriParams = new HashMap<String, String>();
		if (LOG.isDebugEnabled()) {
			LOG.debug("request[path=" + url + "]");
		}
		if (url.startsWith(DISPACHER_PATH_PREFIX)) {
			// format:/cubby-dispacher/(controllerClassName)/(actionMethod)
			String dispatchKey = StringUtils.removeStart(url,
					DISPACHER_PATH_PREFIX);
			method = dispatchActionCache.get(dispatchKey);
		} else {
			for (Pattern p : actionCache.keySet()) {
				Matcher matcher = p.matcher(url);
				if (matcher.find()) {
					method = actionCache.get(p);
					for (int i = 1; i < matcher.groupCount() + 1; i++) {
						String group = matcher.group(i);
						String paramName = method.getUriConvertNames()[i - 1];
						uriParams.put(paramName, group);
					}
					break;
				}
			}
		}
		return method;
	}
}
