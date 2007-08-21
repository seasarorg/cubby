package org.seasar.cubby.convention.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.convention.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.Uri;
import org.seasar.framework.container.ComponentCreator;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.TooManyRegistrationComponentDef;
import org.seasar.framework.container.cooldeploy.CoolComponentAutoRegister;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

public class PathResolverImpl implements PathResolver, Disposable {

	private static Pattern urlRewritePattern = Pattern
			.compile("([{]([^}]+)[}])([^{]*)");

	private final Logger logger = Logger.getLogger(this.getClass());

	private S2Container container;

	private ComponentCreator[] creators;

	private NamingConvention namingConvention;

	private Map<Pattern, RewriteInfo> patternToRewriteInfoMap;

	public PathResolverImpl() {
		DisposableUtil.add(this);
	}

	public void dispose() {
		patternToRewriteInfoMap = null;
	}

	public void add(final String patternStr,
			final Class<? extends Action> actionClass, final String methodName) {
		final ComponentDef componentDef = container
				.getComponentDef(actionClass);
		final Method method = ClassUtil.getMethod(actionClass, methodName,
				new Class<?>[0]);
		this.add(patternStr, componentDef, method);
	}

	public void add(final String patternStr, final ComponentDef componentDef,
			final Method method) {
		String actionFullName = patternStr;
		final List<String> uriParameterNames = new ArrayList<String>();
		final Matcher matcher = urlRewritePattern.matcher(actionFullName);
		while (matcher.find()) {
			final String name = matcher.group(2);
			final String[] names = name.split(",", 2);
			if (names.length == 1) {
				actionFullName = StringUtils.replace(actionFullName, matcher
						.group(1), "([a-zA-Z0-9]+)");
				uriParameterNames.add(matcher.group(2));
			} else {
				actionFullName = StringUtils.replace(actionFullName, matcher
						.group(1), "(" + names[1] + ")");
				uriParameterNames.add(names[0]);
			}
		}

		final String rewritePath = this.fromComponentNameToPath(componentDef,
				method);

		final RewriteInfo rewriteInfo = new RewriteInfo(rewritePath,
				uriParameterNames);
		final Pattern pattern = Pattern.compile("^" + actionFullName + "$");

		patternToRewriteInfoMap.put(pattern, rewriteInfo);
		if (logger.isDebugEnabled()) {
			logger.debug("アクションメソッドを登録[uri=" + actionFullName + ", method="
					+ method + " uri params=" + uriParameterNames + "]");
		}
	}

	private String fromComponentNameToPath(final ComponentDef componentDef,
			final Method method) {
		final StringBuilder builder = new StringBuilder(100);
		builder.append('/');
		builder.append(componentDef.getComponentName().substring(
				0,
				componentDef.getComponentName().length()
						- namingConvention.getActionSuffix().length())
				.replaceAll("_", "/"));
		builder.append('/');
		builder.append(method.getName());
		return builder.toString();
	}

	private ComponentDef[] getActionComponentDefs(final S2Container container) {
		ComponentDef[] componentDefs;
		if (container.getRoot().hasComponentDef(Action.class)) {
			final ComponentDef componentDef = container.getRoot()
					.getComponentDef(Action.class);
			if (componentDef instanceof TooManyRegistrationComponentDef) {
				componentDefs = ((TooManyRegistrationComponentDef) componentDef)
						.getComponentDefs();
			} else {
				componentDefs = new ComponentDef[] { componentDef };
			}
		} else {
			componentDefs = new ComponentDef[0];
		}
		return componentDefs;
	}

	private Map<Pattern, RewriteInfo> getPatternToRewriteInfoMap() {
		if (patternToRewriteInfoMap == null) {
			synchronized (this) {
				if (patternToRewriteInfoMap == null) {
					patternToRewriteInfoMap = new LinkedHashMap<Pattern, RewriteInfo>();

					if (HotdeployUtil.isHotdeploy()) {
						registerActions();
					}

					final ComponentDef[] actionComponentDefs = getActionComponentDefs(container);
					for (final ComponentDef actionComponentDef : actionComponentDefs) {
						final Class<?> type = actionComponentDef
								.getComponentClass();
						for (final Method method : type.getMethods()) {
							if (CubbyUtils.isActionMethod(method)) {
								final String actionFullName = CubbyUtils
										.getActionUrl(type, method);
								this.add(actionFullName, actionComponentDef,
										method);
							}
						}
					}
				}
			}
		}
		return patternToRewriteInfoMap;
	}

	public String getRewritePath(final String path) {
		final Map<String, String> uriParams = new HashMap<String, String>();
		if (logger.isDebugEnabled()) {
			logger.debug("request[path=" + path + "]");
		}

		final Map<Pattern, RewriteInfo> patternToRewriteInfoMap = this
				.getPatternToRewriteInfoMap();
		for (final Pattern p : patternToRewriteInfoMap.keySet()) {
			final Matcher matcher = p.matcher(path);
			if (matcher.find()) {
				final RewriteInfo rewriteInfo = patternToRewriteInfoMap.get(p);
				for (int i = 1; i < matcher.groupCount() + 1; i++) {
					final String group = matcher.group(i);
					final String paramName = rewriteInfo.getUriParameterNames()
							.get(i - 1);
					uriParams.put(paramName, group);
				}
				final String rewritePath = rewriteInfo
						.buildRewritePath(uriParams);
				return rewritePath;
			}
		}
		return null;
	}

	private void registerActions() {
		final CoolComponentAutoRegister autoRegister = new CoolComponentAutoRegister();
		autoRegister.setContainer(container);
		autoRegister.setCreators(creators);
		autoRegister.setNamingConvention(namingConvention);
		autoRegister.registerAll();
	}

	public void setContainer(final S2Container container) {
		this.container = container;
	}

	public void setCreators(final ComponentCreator[] creators) {
		this.creators = creators;
	}

	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	class RewriteInfo {
		private final String rewritePath;
		private final List<String> uriParameterNames;

		public RewriteInfo(final String rewritePath,
				final List<String> uriParameterNames) {
			super();
			this.rewritePath = rewritePath;
			this.uriParameterNames = uriParameterNames;
		}

		public String buildRewritePath(final Map<String, String> uriParams) {
			final StringBuilder builder = new StringBuilder(100);
			builder.append(rewritePath);
			if (!uriParams.isEmpty()) {
				builder.append("?");
				final Uri uri = new Uri();
				for (final String key : uriParams.keySet()) {
					final String value = uriParams.get(key);
					uri.setParam(key, value);
				}
				builder.append(uri.getQueryString());
			}
			return builder.toString();
		}

		public String getRewritePath() {
			return rewritePath;
		}

		public List<String> getUriParameterNames() {
			return uriParameterNames;
		}
	}
}
