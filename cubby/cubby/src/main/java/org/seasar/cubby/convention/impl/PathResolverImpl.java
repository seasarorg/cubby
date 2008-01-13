/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.convention.ForwardInfo;
import org.seasar.cubby.convention.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.util.QueryStringBuilder;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;
import org.seasar.framework.util.StringUtil;

/**
 * クラスパスから {@link Action} を検索し、そのメソッドに指定された {@link org.seasae.cubby.action.Path}
 * の情報によって、リクエストされたパスをどのメソッドに振り分けるかを決定します。
 * 
 * @author baba
 */
public class PathResolverImpl implements PathResolver, Disposable {

	private static Pattern routingPattern = Pattern
			.compile("([{]([^}]+)[}])([^{]*)");

	private static final Logger logger = Logger
			.getLogger(PathResolverImpl.class);

	private boolean initialized;

	private NamingConvention namingConvention;

	private final Map<Pattern, RoutingInfo> routingPatterns = new LinkedHashMap<Pattern, RoutingInfo>();

	private final Map<Pattern, RoutingInfo> customRoutingPatterns = new LinkedHashMap<Pattern, RoutingInfo>();

	private String uriEncoding;

	public PathResolverImpl() {
	}

	public void setUriEncoding(final String uriEncoding) {
		this.uriEncoding = uriEncoding;
	}

	public void initialize() {
		if (!initialized) {
			routingPatterns.clear();
			final ClassCollector classCollector = new ActionClassCollector();
			classCollector.collect();

			DisposableUtil.add(this);
			initialized = true;
		}
	}

	public void dispose() {
		routingPatterns.clear();
		initialized = false;
	}

	public void add(final String regexp,
			final Class<? extends Action> actionClass, final String methodName,
			final RequestMethod... requestMethods) {

		final Method method = ClassUtil.getMethod(actionClass, methodName,
				new Class<?>[0]);
		this.add(regexp, actionClass, method, customRoutingPatterns,
				requestMethods);
	}

	private void add(final String regexp,
			final Class<? extends Action> actionClass, final Method method,
			final Map<Pattern, RoutingInfo> patternToRoutingInfoMap,
			final RequestMethod... requestMethods) {

		String actionFullName = regexp;
		final List<String> uriParameterNames = new ArrayList<String>();
		final Matcher matcher = routingPattern.matcher(actionFullName);
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

		final String forwardPath = this.fromActionClassToPath(actionClass,
				method);

		final RoutingInfo routingInfo = new RoutingInfo(actionClass, method,
				uriParameterNames, forwardPath, requestMethods);
		final Pattern pattern = Pattern.compile("^" + actionFullName + "$");

		patternToRoutingInfoMap.put(pattern, routingInfo);
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
				customRoutingPatterns);
		if (forwardInfo == null) {
			forwardInfo = findForwardInfo(decodedPath, routingPatterns);
		}
		return forwardInfo;
	}

	private ForwardInfo findForwardInfo(final String path,
			final Map<Pattern, RoutingInfo> routingPatterns) {
		final Map<String, String> uriParams = new HashMap<String, String>();
		for (final Entry<Pattern, RoutingInfo> entry : routingPatterns
				.entrySet()) {
			final Matcher matcher = entry.getKey().matcher(path);
			if (matcher.find()) {
				final RoutingInfo routingInfo = entry.getValue();
				final HttpServletRequest request = SingletonS2Container
						.getComponent(HttpServletRequest.class);
				if (routingInfo.isAcceptable(request)) {
					for (int i = 1; i < matcher.groupCount() + 1; i++) {
						final String name = routingInfo.getUriParameterNames()
								.get(i - 1);
						final String value = matcher.group(i);
						uriParams.put(name, value);
					}
					final ForwardInfoImpl forwardInfo = new ForwardInfoImpl(
							routingInfo, uriParams);
					return forwardInfo;
				}
			}
		}
		return null;
	}

	public void setNamingConvention(final NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

	class RoutingInfo {

		private final Class<? extends Action> actionClass;

		private final Method method;

		private final List<String> uriParameterNames;

		private final String forwardPath;

		private final RequestMethod[] requestMethods;

		public RoutingInfo(final Class<? extends Action> actionClass,
				final Method method, final List<String> uriParameterNames,
				final String forwardPath,
				final RequestMethod[] requestMethods) {
			this.actionClass = actionClass;
			this.method = method;
			this.uriParameterNames = uriParameterNames;
			this.forwardPath = forwardPath;
			this.requestMethods = requestMethods;
		}

		public String buildForwardPath(final Map<String, String> uriParams) {
			final StringBuilder builder = new StringBuilder(100);
			builder.append(forwardPath);
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

		public String getForwardPath() {
			return forwardPath;
		}

		public RequestMethod[] getRequestMethods() {
			return requestMethods;
		}

		public boolean isAcceptable(final HttpServletRequest request) {
			final String requestMethod = request.getMethod();
			for (final RequestMethod acceptableRequestMethod : requestMethods) {
				if (StringUtil.equalsIgnoreCase(acceptableRequestMethod.name(),
						requestMethod)) {
					return true;
				}
			}
			return false;
		}

	}

	class ActionClassCollector extends ClassCollector {

		public ActionClassCollector() {
			super(namingConvention);
		}

		public void processClass(final String packageName,
				final String shortClassName) {
			if (shortClassName.indexOf('$') != -1) {
				return;
			}
			final String className = ClassUtil.concatName(packageName,
					shortClassName);
			if (!namingConvention.isTargetClassName(className)) {
				return;
			}
			if (!className.endsWith(namingConvention.getActionSuffix())) {
				return;
			}
			final Class<? extends Action> clazz = classForName(className);
			if (namingConvention.isSkipClass(clazz)) {
				return;
			}

			for (final Method method : clazz.getMethods()) {
				if (CubbyUtils.isActionMethod(method)) {
					final String actionFullName = CubbyUtils.getActionUrl(
							clazz, method);
					final RequestMethod[] acceptableRequestMethods = CubbyUtils
							.getAcceptableRequestMethods(clazz, method);
					add(actionFullName, clazz, method, routingPatterns,
							acceptableRequestMethods);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> classForName(final String className) {
		return ClassUtil.forName(className);
	}

}
