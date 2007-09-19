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
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定されたパスにフォワードします。
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
		Action action = context.getAction();
		String actionClassName = CubbyUtils.getActionClassName(context
				.getComponentDef().getComponentClass());

		String path = null;
		if (this.path.startsWith("/")) {
			path = this.path;
		} else if (StringUtil.isEmpty(actionClassName)) {
			path = "/" + this.path;
		} else {
			path = "/" + actionClassName + "/" + this.path;
		}
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0001", new String[] { path });
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0002", new String[] { path });
		}
		action.postrender();

		action.getFlash().clear();
	}

	/**
	 * フォワード前に {@link Action#prerender()} を実行します。
	 */
	@Override
	public void prerender(final ActionContext context) {
		final Action action = context.getAction();
		action.prerender();
	}

}
