package org.seasar.cubby.convention.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.convention.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.Uri;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.StringUtil;

public class PathResolverImpl implements PathResolver, Disposable {

	private static Pattern urlRewritePattern = Pattern
			.compile("([{]([^}]+)[}])([^{]*)");

	private final Logger logger = Logger.getLogger(this.getClass());

	private boolean initialized;

	private NamingConvention namingConvention;

	private Map<Pattern, RewriteInfo> patternToRewriteInfoMap;

	private Map<Pattern, RewriteInfo> customPatternToRewriteInfoMap = new HashMap<Pattern, RewriteInfo>();

	public void initialize() {
		if (!initialized) {
			patternToRewriteInfoMap = new LinkedHashMap<Pattern, RewriteInfo>();
			ClassCollector classCollector = new ActionClassCollector();
			classCollector.collect();

			DisposableUtil.add(this);
			initialized = true;
		}
	}

	public void dispose() {
		patternToRewriteInfoMap.clear();
		initialized = false;
	}

	public void add(final String patternStr,
			final Class<? extends Action> actionClass, final String methodName) {
		final Method method = ClassUtil.getMethod(actionClass, methodName,
				new Class<?>[0]);
		this.add(patternStr, actionClass, method, customPatternToRewriteInfoMap);
	}

	private void add(final String patternStr,
			final Class<? extends Action> actionClass, final Method method,
			Map<Pattern, RewriteInfo> patternToRewriteInfoMap) {
		String actionFullName = patternStr;
		final List<String> uriParameterNames = new ArrayList<String>();
		final Matcher matcher = urlRewritePattern.matcher(actionFullName);
		while (matcher.find()) {
			final String name = matcher.group(2);
			final String[] names = name.split(",", 2);
			if (names.length == 1) {
				actionFullName = StringUtil.replace(actionFullName, matcher
						.group(1), "([a-zA-Z0-9]+)");
				uriParameterNames.add(matcher.group(2));
			} else {
				actionFullName = StringUtil.replace(actionFullName, matcher
						.group(1), "(" + names[1] + ")");
				uriParameterNames.add(names[0]);
			}
		}

		final String rewritePath = this.fromActionClassToPath(actionClass,
				method);

		final RewriteInfo rewriteInfo = new RewriteInfo(rewritePath,
				uriParameterNames);
		final Pattern pattern = Pattern.compile("^" + actionFullName + "$");

		patternToRewriteInfoMap.put(pattern, rewriteInfo);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0007", new Object[] { actionFullName, method,
					uriParameterNames });
		}
	}

	private String fromActionClassToPath(final Class<? extends Action> actionClass,
			final Method method) {
		final String componentName = namingConvention
				.fromClassNameToComponentName(actionClass.getCanonicalName());
		final StringBuilder builder = new StringBuilder(100);
		builder.append('/');
		builder.append(componentName.substring(
				0,
				componentName.length()
						- namingConvention.getActionSuffix().length())
				.replaceAll("_", "/"));
		builder.append('/');
		builder.append(method.getName());
		return builder.toString();
	}

	public String getRewritePath(final String path) {
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0006", new Object[] { path });
		}

		initialize();

		String rewritePath = findRewritePath(path, customPatternToRewriteInfoMap);
		if (StringUtil.isEmpty(rewritePath)) {
			rewritePath = findRewritePath(path, patternToRewriteInfoMap);
		}
		return rewritePath;
	}

	private String findRewritePath(final String path,
			final Map<Pattern, RewriteInfo> patternToRewriteInfoMap) {
		final Map<String, String> uriParams = new HashMap<String, String>();
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

	class ActionClassCollector extends ClassCollector {

		public ActionClassCollector() {
			super(namingConvention);
		}

		public void processClass(String packageName, String shortClassName) {
			if (shortClassName.indexOf('$') != -1) {
				return;
			}
			String className = ClassUtil
					.concatName(packageName, shortClassName);
			if (!namingConvention.isTargetClassName(className)) {
				return;
			}
			if (!className.endsWith(namingConvention.getActionSuffix())) {
				return;
			}
			Class<? extends Action> clazz = classForName(className);
			if (namingConvention.isSkipClass(clazz)) {
				return;
			}

			for (final Method method : clazz.getMethods()) {
				if (CubbyUtils.isActionMethod(method)) {
					final String actionFullName = CubbyUtils
							.getActionUrl(clazz, method);
					add(actionFullName, clazz, method, patternToRewriteInfoMap);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> classForName(String className) {
		return ClassUtil.forName(className);
	}

}
