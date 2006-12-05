package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.annotation.Filter;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFactory;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionHolder;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.ControllerFactory;
import org.seasar.cubby.controller.filters.InitializeFilter;
import org.seasar.cubby.controller.filters.InvocationFilter;
import org.seasar.cubby.controller.filters.ValidationFilter;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.CubbyUtils;

public class ActionFactoryImpl implements ActionFactory {

	private static final Log LOG = LogFactory.getLog(ActionFactoryImpl.class);

	@SuppressWarnings("unchecked")
	public static final Class<ActionFilter>[] NULL_FILTERS = (Class<ActionFilter>[]) new Class[0];

	public static final String[] EMPTY_STRING_ARRAY = new String[]{};

	private final Map<Pattern, ActionHolder> actionCache = new HashMap<Pattern, ActionHolder>();

	private final Map<Class, ActionFilter> actionFilterRegistory = new LinkedHashMap<Class, ActionFilter>();

	private final ControllerFactory controllerFactory;

	private static Pattern pattern = Pattern.compile("([{]([^}]+)[}])([^{]*)");

	public ActionFactoryImpl(ControllerFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
	}

	public void addActionFilter(ActionFilter actionFilter) {
		if (actionFilterRegistory.containsKey(actionFilter.getClass())) {
			throw new RuntimeException("CUB001");
		}
		actionFilterRegistory.put(actionFilter.getClass(), actionFilter);
	}

	@SuppressWarnings("unchecked")
	public void initialize(FilterConfig config) {
		ServletContext context = config.getServletContext();

		// setup controller factory

		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録します。[Context="
					+ context.getServletContextName() + "]");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("context", context);
		Collection<Class> controllerClassList = controllerFactory
				.getContorollerClassList(params);
		for (Class c : controllerClassList) {
			if (isController(c)) {
				initController(c);
			}
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録しました。");
		}
	}

	private void initController(Class c) {
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
		Matcher matcher = pattern.matcher(actionFullName);
		while (matcher.find()) {
			String name = matcher.group(2);
			String[] names = name.split(",", 2);
			if (names.length == 1) {
				actionFullName = StringUtils.replace(actionFullName, matcher.group(1), "([a-zA-Z0-9]+)");
				uriConvertNames.add(matcher.group(2));
			} else {
				actionFullName = StringUtils.replace(actionFullName, matcher.group(1), "(" + names[1] + ")");
				uriConvertNames.add(names[0]);
			}
		}
		ActionHolder holder = new ActionHolder(m, chain, uriConvertNames.toArray(new String[uriConvertNames.size()]));
		Pattern pattern = Pattern.compile("^" + actionFullName + "$");
		actionCache.put(pattern, holder);
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録[uri=" + actionFullName + ", method=" + m
					+ "uri params=" + StringUtils.join(holder.getUriConvertNames()) + "]");
		}
	}

	static String[] getUriConvertNames(String value) {
		return null;
	}
	
	private boolean isController(Class c) {
		return ClassUtils.isSubClass(Controller.class, c);
	}

	@SuppressWarnings("unchecked")
	private ActionFilterChain createActionFilterChain(Class c, Method m) {
		ActionFilterChain chain = new ActionFilterChain();
		chain.add(actionFilterRegistory.get(InitializeFilter.class));
		addCustomFilters(c, m, chain);
		chain.add(actionFilterRegistory.get(ValidationFilter.class));
		//chain.add(actionFilterRegistory.get(ParameterFilter.class));
		chain.add(actionFilterRegistory.get(InvocationFilter.class));
		return chain;
	}

	private void addCustomFilters(Class<Controller> c, Method m,
			ActionFilterChain chain) {
		for (Class<? extends ActionFilter> filterClass : getFilters(c)) {
			ActionFilter filter = ClassUtils.newInstance(filterClass);
			chain.add(filter);
		}
	}

	private Class<? extends ActionFilter>[] getFilters(Class<Controller> c) {
		Filter filter = c.getAnnotation(Filter.class);
		if (filter == null) {
			return NULL_FILTERS;
		}
		return filter.value();
	}

	public ActionContext createAction(String appUri,
			HttpServletRequest request, HttpServletResponse response) {
		ActionHolder holder = null;
		Map<String, Object> uriParams = new HashMap<String, Object>();
		for (Pattern p : actionCache.keySet()) {
			Matcher matcher = p.matcher(appUri);
			if(matcher.find()) {
				holder = actionCache.get(p);
				for (int i = 1; i < matcher.groupCount() + 1; i++) {
					String group = matcher.group(i);
					String paramName = holder.getUriConvertNames()[i - 1];
					uriParams.put(paramName, group);
				}
				break;
			}
		}
		if (holder == null) {
			return null;
		}
		Controller controller = controllerFactory.createController(holder
				.getActionMethod());
		return new ActionContextImpl(request, response, controller, holder, uriParams);
	}

}
