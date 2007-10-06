package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;

/**
 * アクションメソッド実行後の処理を表す結果オブジェクトのインターフェイス
 * @author agata
 */
public interface ActionResult {

	/**
	 * 処理を実行します。
	 * @param context アクションコンテキスト
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws Exception
	 */
	void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * フォワード直前の処理を実行します。
	 * @param context アクションコンテキスト
	 * @param request リクエスト
	 */
	void prerender(ActionContext context);

}
