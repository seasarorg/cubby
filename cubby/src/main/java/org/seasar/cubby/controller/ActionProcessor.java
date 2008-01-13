package org.seasar.cubby.controller;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;

/**
 * リクエストのパスを元にアクションメソッドを決定して実行するクラスです。
 * 
 * @author agata
 */
public interface ActionProcessor {
	
	/**
	 * リクエストのパスを元にアクションメソッドを決定して実行します。
	 * <ul>
	 * <li>リクエストパスを元に実行するアクションとそのアクションメソッドを決定します。</li>
	 * <li>アクションメソッドを実行します。</li>
	 * <li>アクションメソッドの実行結果である{@link ActionResult}を実行します。</li>
	 * </ul>
	 * @param request リクエスト
	 * @param response レスポンス
	 * @param chain フィルターチェイン
	 * @return 実行結果。アクションメソッドが存在しない場合はnull。
	 * @throws Exception
	 */
	ActionResult process(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;

}
