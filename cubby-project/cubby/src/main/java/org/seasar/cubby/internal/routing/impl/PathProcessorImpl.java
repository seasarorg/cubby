package org.seasar.cubby.internal.routing.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_ROUTING;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.ContainerFactory;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.factory.RequestParserFactory;
import org.seasar.cubby.internal.routing.PathInfo;
import org.seasar.cubby.internal.routing.PathProcessor;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.routing.RoutingException;
import org.seasar.cubby.internal.util.CubbyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * パス処理の実装クラス
 * 
 * @author someda
 */
public class PathProcessorImpl implements PathProcessor {

	/** オリジナルのリクエスト */
	private HttpServletRequest request;

	/** オリジナルのレスポンス */
	private HttpServletResponse response;

	private List<Pattern> ignorePathPatterns;

	// TODO
	private final Router router = new RouterImpl();

	// TODO
	private final ActionProcessor actionProcessor = new ActionProcessorImpl();

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(PathProcessor.class);

	/** 内部フォワード情報 */
	private PathInfo pathInfo;

	public PathProcessorImpl(HttpServletRequest request,
			HttpServletResponse response, List<Pattern> ignorePathPatterns) {
		this.request = request;
		this.response = response;
		this.ignorePathPatterns = ignorePathPatterns;
		initialize();
	}

	/**
	 * PathInfo を設定する初期化処理
	 */
	private void initialize() {
		final Routing routing = CubbyUtils.getAttribute(request, ATTR_ROUTING);
		if (routing != null) {
			this.pathInfo = new PathInfoImpl(routing);
		} else {
			this.pathInfo = router.routing(request, response,
					ignorePathPatterns);
		}
	}

	public boolean hasPathInfo() {
		return this.pathInfo != null;
	}

	public void process() throws IOException, ServletException {

		if (!hasPathInfo()) {
			throw new RoutingException(format("ECUB0110"));
		}

		final HttpServletRequest wrappedRequest = wrapRequest();
		final Map<String, Object[]> parameterMap = parseRequest(wrappedRequest);
		request.setAttribute(ATTR_PARAMS, parameterMap);

		final Routing routing = dispatch(parameterMap);
		invoke(wrappedRequest, routing);
	}

	/**
	 * PathInfo を利用し、オリジナルのリクエストをラップする
	 * @return
	 */
	protected final HttpServletRequest wrapRequest() {
		final Map<String, String[]> parameters = pathInfo.getURIParameters();
		return new CubbyHttpServletRequestWrapper(request, parameters);
	}

	/**
	 * リクエストをパースしてパラメータを取り出し、{@link Map}に変換して返します。
	 * 
	 * @param wrappedRequest
	 *            リクエスト
	 * @return リクエストパラメータの{@link Map}
	 */
	protected final Map<String, Object[]> parseRequest(
			final HttpServletRequest wrappedRequest) {
		
		final Container container = ContainerFactory.getContainer();
		final RequestParserFactory requestParserFactory = container
				.lookup(RequestParserFactory.class);
		final RequestParser requestParser = requestParserFactory
				.getRequestParser(wrappedRequest);
		if (requestParser == null) {
			throw new NullPointerException("requestParser");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0016", requestParser));
		}
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(wrappedRequest);
		return parameterMap;
	}

	protected final Routing dispatch(Map<String, Object[]> parameterMap) {

		final Map<String, Routing> routings = pathInfo.getOnSubmitRoutings();
		for (final String onSubmit : routings.keySet()) {
			if (parameterMap.containsKey(onSubmit)) {
				return routings.get(onSubmit);
			}
		}
		return routings.get(null);
	}

	/**
	 * {@link ActionProcessor} を実行する
	 * 
	 * @param wrappedRequest
	 * @param routing
	 * @throws IOException
	 * @throws ServletException
	 */
	private void invoke(final HttpServletRequest wrappedRequest,
			final Routing routing) throws IOException, ServletException {
		ThreadContext.newContext(wrappedRequest);
		try {
			final ActionResultWrapper actionResultWrapper = actionProcessor
					.process(wrappedRequest, response, routing);
			actionResultWrapper.execute(wrappedRequest, response);
		} catch (final Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else if (e instanceof ServletException) {
				throw (ServletException) e;
			} else {
				throw new ServletException(e);
			}
		} finally {
			ThreadContext.restoreContext();
		}
	}

	private class PathInfoImpl implements PathInfo {

		private Map<String, Routing> routings;

		private Map<String, String[]> uriParameters = new HashMap<String, String[]>();

		public PathInfoImpl(Routing routing) {
			routings = new HashMap<String, Routing>();
			routings.put(null, routing);
		}

		public Map<String, Routing> getOnSubmitRoutings() {
			return routings;
		}

		public Map<String, String[]> getURIParameters() {
			return uriParameters;
		}
	}

}
