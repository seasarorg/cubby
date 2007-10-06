package org.seasar.cubby.convention.impl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.convention.ForwardInfo;
import org.seasar.cubby.convention.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.QueryStringBuilder;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.exception.IORuntimeException;
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

	private Map<Pattern, RewriteInfo> patternToRewriteInfoMap = new LinkedHashMap<Pattern, RewriteInfo>();

	private Map<Pattern, RewriteInfo> customPatternToRewriteInfoMap = new LinkedHashMap<Pattern, RewriteInfo>();

	private String uriEncoding;

	public void setUriEncoding(final String uriEncoding) {
		this.uriEncoding = uriEncoding;
	}

	public void initialize() {
		if (!initialized) {
			patternToRewriteInfoMap.clear();
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
		this
				.add(patternStr, actionClass, method,
						customPatternToRewriteInfoMap);
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

		final RewriteInfo rewriteInfo = new RewriteInfo(actionClass, method,
				uriParameterNames, rewritePath);
		final Pattern pattern = Pattern.compile("^" + actionFullName + "$");

		patternToRewriteInfoMap.put(pattern, rewriteInfo);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0007", new Object[] { actionFullName, method,
					uriParameterNames });
		}
	}

	private String fromActionClassToPath(
			final Class<? extends Action> actionClass, final Method method) {
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

	public ForwardInfo getForwardInfo(final String path) {
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0006", new Object[] { path });
		}

		initialize();

		final String decodedPath;
		try {
			decodedPath = URLDecoder.decode(path, uriEncoding);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}

		ForwardInfo forwardInfo = findForwardInfo(decodedPath,
				customPatternToRewriteInfoMap);
		if (forwardInfo == null) {
			forwardInfo = findForwardInfo(decodedPath, patternToRewriteInfoMap);
		}
		return forwardInfo;
	}

	private ForwardInfo findForwardInfo(final String path,
			final Map<Pattern, RewriteInfo> patternToRewriteInfoMap) {
		final Map<String, String> uriParams = new HashMap<String, String>();
		for (final Entry<Pattern, RewriteInfo> entry : patternToRewriteInfoMap
				.entrySet()) {
			final Matcher matcher = entry.getKey().matcher(path);
			if (matcher.find()) {
				final RewriteInfo rewriteInfo = entry.getValue();
				for (int i = 1; i < matcher.groupCount() + 1; i++) {
					final String name = rewriteInfo.getUriParameterNames().get(
							i - 1);
					final String value = matcher.group(i);
					uriParams.put(name, value);
				}
				final ForwardInfoImpl forwardInfo = new ForwardInfoImpl(
						rewriteInfo, uriParams);
				return forwardInfo;
			}
		}
		return null;
	}

	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	class RewriteInfo {

		private final Class<? extends Action> actionClass;

		private final Method method;

		private final List<String> uriParameterNames;

		private final String rewritePath;

		public RewriteInfo(final Class<? extends Action> actionClass,
				final Method method, final List<String> uriParameterNames,
				final String rewritePath) {
			this.actionClass = actionClass;
			this.method = method;
			this.uriParameterNames = uriParameterNames;
			this.rewritePath = rewritePath;
		}

		public String buildRewritePath(final Map<String, String> uriParams) {
			final StringBuilder builder = new StringBuilder(100);
			builder.append(rewritePath);
			if (!uriParams.isEmpty()) {
				builder.append("?");
				final QueryStringBuilder query = new QueryStringBuilder();
				final String encoding = PathResolverImpl.this.uriEncoding;
				if (!StringUtil.isEmpty(encoding)) {
					query.setEncode(encoding);
				}
				for (final Entry<String, String> entry : uriParams.entrySet()) {
					query.addParam(entry.getKey(), entry.getValue());
				}
				builder.append(query.toString());
			}
			return builder.toString();
		}

		public Class<? extends Action> getActionClass() {
			return actionClass;
		}

		public Method getMethod() {
			return method;
		}

		public List<String> getUriParameterNames() {
			return uriParameterNames;
		}

		public String getRewritePath() {
			return rewritePath;
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
					final String actionFullName = CubbyUtils.getActionUrl(
							clazz, method);
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
