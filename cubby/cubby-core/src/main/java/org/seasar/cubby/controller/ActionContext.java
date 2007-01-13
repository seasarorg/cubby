package org.seasar.cubby.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * アクションメソッドの実行時のコンテキスト情報を保持するインターフェイス
 * 
 * @author agata
 * @since 1.0
 */
public interface ActionContext {
	Controller getController();

	HttpServletRequest getRequest();

	HttpServletResponse getResponse();

	ActionMethod getActionMethod();
}
