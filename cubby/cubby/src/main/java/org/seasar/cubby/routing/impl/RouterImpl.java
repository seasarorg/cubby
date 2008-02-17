package org.seasar.cubby.routing.impl;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.InternalForwardInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Router;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;

/**
 * ルーターの実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class RouterImpl implements Router {

	/** ロガー */
	private static final Logger logger = Logger.getLogger(RouterImpl.class);

	/** 空の対象外パターンのリスト */
	private static final List<Pattern> EMPTY_IGNORE_PATH_PATTERNS = Collections
			.emptyList();

	/** フォワードするための情報を抽出するクラス。 */
	private PathResolver pathResolver;

	/**
	 * フォワードするための情報を抽出するクラスを設定します。
	 * 
	 * @param pathResolver
	 *            フォワードするための情報を抽出するクラス
	 */
	public void setPathResolver(final PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}

	/**
	 * {@inheritDoc}
	 */
	public InternalForwardInfo routing(final HttpServletRequest request,
			final HttpServletResponse response) {
		return routing(request, response, EMPTY_IGNORE_PATH_PATTERNS);
	}

	/**
	 * {@inheritDoc}
	 */
	public InternalForwardInfo routing(final HttpServletRequest request,
			final HttpServletResponse response, List<Pattern> ignorePathPatterns) {
		final String path = CubbyUtils.getPath(request);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0006", new Object[] { path });
		}

		if (isIgnorePath(path, ignorePathPatterns)) {
			return null;
		}

		final InternalForwardInfo internalForwardInfo = pathResolver
				.getInternalForwardInfo(path, request.getMethod());
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
			List<Pattern> ignorePathPatterns) {
		for (final Pattern pattern : ignorePathPatterns) {
			final Matcher matcher = pattern.matcher(path);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}