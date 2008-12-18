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
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.routing.PathProcessor;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.routing.RoutingException;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.RequestParserProvider;
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
		final Routing routing = RequestUtils
				.getAttribute(request, ATTR_ROUTING);
		if (routing != null) {
			this.pathInfo = new PathInfoImpl(routing);
			request.removeAttribute(ATTR_ROUTING);
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
		final ActionProcessorInvokeCommand actionProcessorInvokeCommand = new ActionProcessorInvokeCommand(
				wrappedRequest, response, routing);
		try {
			ThreadContext.runInContext(wrappedRequest, response,
					actionProcessorInvokeCommand);
		} catch (final Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else if (e instanceof ServletException) {
				throw (ServletException) e;
			} else {
				throw new ServletException(e);
			}
		}
	}

	private class ActionProcessorInvokeCommand implements Command<Void> {

		private final HttpServletRequest request;

		private final HttpServletResponse response;

		private final Routing routing;

		public ActionProcessorInvokeCommand(HttpServletRequest request,
				HttpServletResponse response, Routing routing) {
			this.request = request;
			this.response = response;
			this.routing = routing;
		}

		public Void execute() throws Exception {
			final ActionResultWrapper actionResultWrapper = actionProcessor
					.process(request, response, routing);
			actionResultWrapper.execute(request, response);
			return null;
		}

	}

	/**
	 * PathInfo を利用し、オリジナルのリクエストをラップする
	 * 
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
		final RequestParserProvider requestParserProvider = ProviderFactory
				.get(RequestParserProvider.class);
		final RequestParser requestParser = requestParserProvider
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
