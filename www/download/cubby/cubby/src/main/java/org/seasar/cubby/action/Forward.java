package org.seasar.cubby.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * 指定されたパスにフォワードする {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定されたパスにフォワードします。
 * </p>
 * <p>
 * 使用例1 : フォワード先を相対パスで指定
 * 
 * <pre>
 * return new Forward(&quot;list.jsp&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : フォワード先を絶対パスで指定
 * 
 * <pre>
 * return new Forward(&quot;/todo/list.jsp&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * また、フォワードの前処理として {@link #prerender(ActionContext, HttpServletRequest)}
 * によって、リクエストにいくつかの属性が設定されます。
 * </p>
 * 
 * @author baba
 */
public class Forward extends AbstractActionResult {

	private final Logger logger = Logger.getLogger(this.getClass());

	private final String path;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            フォワード先のパス
	 */
	public Forward(final String path) {
		this.path = path;
	}

	public void execute(final ActionContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Action action = context.getAction();
		final String actionClassName = CubbyUtils.getActionClassName(context
				.getComponentDef().getComponentClass());

		final String absolutePath;
		if (this.path.startsWith("/")) {
			absolutePath = this.path;
		} else if (StringUtil.isEmpty(actionClassName)) {
			absolutePath = "/" + this.path;
		} else {
			absolutePath = "/" + actionClassName + "/" + this.path;
		}
		final HttpServletRequest wrappedRequest = new ForwardHttpServletRequestWrapper(
				request, context);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0001", new String[] { absolutePath });
		}
		final RequestDispatcher dispatcher = request
				.getRequestDispatcher(absolutePath);
		dispatcher.forward(wrappedRequest, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0002", new String[] { absolutePath });
		}
		action.postrender();

		action.getFlash().clear();
	}

	/**
	 * フォワード前の処理を行います。
	 * <p>
	 * 以下の処理を行います。
	 * <ul>
	 * <li>{@link Action#prerender()} を実行します。</li>
	 * <li>リクエストのアトリビュートに {@value org.seasar.cubby.CubbyConstants#ATTR_ACTION}
	 * を設定します。</li>
	 * <li>リクエストのアトリビュートに
	 * {@value org.seasar.cubby.CubbyConstants#ATTR_OUTPUT_VALUES} を設定します。</li>
	 * <li>リクエストのアトリビュートに、アクションのプロパティを設定します。</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public void prerender(final ActionContext context,
			final HttpServletRequest request) {

		final Action action = context.getAction();
		action.prerender();
	}

}