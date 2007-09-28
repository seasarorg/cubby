package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.framework.container.ComponentDef;

/**
 * アクションメソッドの実行時のコンテキスト情報を保持するインターフェイス
 * 
 * @author agata
 * @since 1.0
 */
public interface ActionContext {

	void initialize(ActionDef actionDef);

	ActionResult invoke() throws Throwable;

	ComponentDef getComponentDef();

	Action getAction();

	Method getMethod();

	Validation getValidation();

	Object getFormBean();

	Populator getPopulator();

}
