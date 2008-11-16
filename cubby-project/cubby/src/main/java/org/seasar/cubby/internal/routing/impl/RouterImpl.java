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
package org.seasar.cubby.internal.routing.impl;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.ContainerFactory;
import org.seasar.cubby.internal.factory.PathResolverFactory;
import org.seasar.cubby.internal.routing.PathInfo;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.util.CubbyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ルーターの実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class RouterImpl implements Router {

	/** ロガー */
	private static final Logger logger = LoggerFactory
			.getLogger(RouterImpl.class);

	/** 空の対象外パターンのリスト */
	private static final List<Pattern> EMPTY_IGNORE_PATH_PATTERNS = Collections
			.emptyList();

	/**
	 * {@inheritDoc}
	 */
	public PathInfo routing(final HttpServletRequest request,
			final HttpServletResponse response) {
		return routing(request, response, EMPTY_IGNORE_PATH_PATTERNS);
	}

	/**
	 * {@inheritDoc}
	 */
	public PathInfo routing(final HttpServletRequest request,
			final HttpServletResponse response,
			final List<Pattern> ignorePathPatterns) {
		final String path = CubbyUtils.getPath(request);
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0006", path));
		}

		if (isIgnorePath(path, ignorePathPatterns)) {
			return null;
		}

		final Container container = ContainerFactory.getContainer();
		final PathResolverFactory pathResolverFactory = container
				.lookup(PathResolverFactory.class);
		final PathResolver pathResolver = pathResolverFactory.getPathResolver();
		final PathInfo internalForwardInfo = pathResolver.getPathInfo(path,
				request.getMethod(), null);
		return internalForwardInfo;
	}

	/**
	 * 指定された path が ignorePathPatterns にマッチするかを示します。
	 * 
	 * @param path
	 *            パス
	 * @param ignorePathPatterns
	 *            対象外パターンのリスト
	 * @return path が ignorePathPatterns にマッチする場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private boolean isIgnorePath(final String path,
			final List<Pattern> ignorePathPatterns) {
		for (final Pattern pattern : ignorePathPatterns) {
			final Matcher matcher = pattern.matcher(path);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}