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

	private final Logger logger = Logger.getLogger(this.getClass());

	private final String result;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param path
	 *            リダイレクト先のパス
	 */
	public Redirect(String result) {
		this.result = result;
	}

	public void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = null;

		String cpath = request.getContextPath();
		if (result.charAt(0) == '/') {
			path = cpath + result;
		} else {
			String actionClassName = CubbyUtils.getActionClassName(context
					.getComponentDef().getComponentClass());
			if (StringUtil.isEmpty(actionClassName)) {
				path = cpath + "/" + result;
			} else {
				path = cpath + "/" + actionClassName + "/" + result;

			}
		}
		if (logger.isDebugEnabled()) {
			logger.log("DCUB0003", new String[] { path });
		}
		response.sendRedirect(path);
	}

}
