package org.seasar.cubby.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.Action;

/**
 * アクションメソッドの実行時のコンテキスト情報を保持するインターフェイス
 * 
 * @author agata
 * @since 1.0
 */
public interface ActionContext {
	Action getController();

	HttpServletRequest getRequest();

	HttpServletResponse getResponse();

	ActionMethod getActionMethod();
}
