package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;

/**
 * アクションメソッドから直接レスポンスを返すことを示す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定すると、後続の処理はレスポンスに何も出力しません。
 * アクションメソッド中でレスポンスを出力してください。
 * </p>
 * 
 * @author baba
 */
public class Direct extends AbstractActionResult {

	/**
	 * インスタンスを生成します。
	 */
	public Direct() {
	}

	public void execute(final ActionContext context,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
	}

}
