/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.dxo.FormDxo;
import org.seasar.framework.container.ComponentDef;

/**
 * アクションメソッドの実行時のコンテキスト情報を保持するインターフェイス。
 * 
 * @author agata
 * @author baba
 * @since 1.0
 */
public interface ActionContext {

	void initialize(ActionDef actionDef);

	boolean isInitialized();

	ActionResult invoke() throws Exception;

	ComponentDef getComponentDef();

	Action getAction();

	Method getMethod();

	Validation getValidation();

	Object getFormBean();

	FormDxo getFormDxo();

}
