package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.seasar.cubby.controller.filters.ParameterFilter;
import org.seasar.cubby.controller.filters.ValidationFilter;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.CubbyUtils;

public class ActionFactoryImpl implements ActionFactory {

	private static final Log LOG = LogFactory.getLog(ActionFactoryImpl.class);

	@SuppressWarnings("unchecked")
	public static final Class<ActionFilter>[] NULL_FILTERS = (Class<ActionFilter>[]) new Class[0];

	private final Map<String, ActionHolder> actionCache = new HashMap<String, ActionHolder>();

	private final Map<Class, ActionFilter> actionFilterRegistory = new HashMap<Class, ActionFilter>();

	private final ControllerFactory controllerFactory;

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
		ActionHolder holder = new ActionHolder(m, chain);
		actionCache.put(actionFullName, holder);
		if (LOG.isDebugEnabled()) {
			LOG.debug("アクションメソッドを登録[uri=" + actionFullName + ", method=" + m
					+ "]");
		}
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
		chain.add(actionFilterRegistory.get(ParameterFilter.class));
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
		ActionHolder holder = actionCache.get(appUri);
		if (holder == null) {
			return null;
		}
		Controller controller = controllerFactory.createController(holder
				.getActionMethod());
		return new ActionContextImpl(request, response, controller, holder);
	}
}
