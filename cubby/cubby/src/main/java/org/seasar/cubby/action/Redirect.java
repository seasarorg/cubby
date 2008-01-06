package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * 指定されたパスにリダイレクトする {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定することで、指定されたパスにリダイレクトします。
 * </p>
 * <p>
 * 使用例1 : リダイレクト先を相対パスで指定
 * 
 * <pre>
 * return new Redirect(&quot;list&quot;);
 * </pre>
 * 
 * </p>
 * <p>
 * 使用例2 : リダイレクト先を絶対パスで指定
 * 
 * <pre>
 * return new Redirect(&quot;/todo/list&quot;);
 * </pre>
 * 
 * </p>
 * 
 * @author baba
 */
public class Redirect extends AbstractActionResult {

	private static final Logger logger = Logger.getLogger(Redirect.class);

	private final String path;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 */
	public Redirect(final String path) {
		this.path = path;
	}
	
	/**
	 * パスを取得します。
	 * @return パス
	 */
	public String getPath() {
		return this.path;
	}
	
	public void execute(final ActionContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		final String absolutePath;
		final String contextPath;
		if ("/".equals(request.getContextPath())) {
			contextPath = "";
		} else {
			contextPath = request.getContextPath();
		}
		if (this.path.startsWith("/")) {
			absolutePath = contextPath + this.path;
		} else {
			final String actionClassName = CubbyUtils
					.getActionClassName(context.getComponentDef()
							.getComponentClass());
			if (StringUtil.isEmpty(actionClassName)) {
				absolutePath = contextPath + "/" + this.path;
			} else {
				absolutePath = contextPath + "/" + actionClassName + "/"
						+ this.path;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0003", new String[] { absolutePath });
		}
		response.sendRedirect(absolutePath);
	}

}
