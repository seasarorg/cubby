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
package org.seasar.cubby.validator;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;

/**
 * 指定された {@link ActionResult} を実行する {@link ValidationFailBehaviour} です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ActionResultValidationFailBehaviour implements
		ValidationFailBehaviour, Serializable {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 1L;

	private final ActionResult actionResult;

	/**
	 * インスタンス化します。
	 * 
	 * @param actionResult
	 *            入力検証でエラーがあった場合に実行する {@link ActionResult} です。
	 */
	public ActionResultValidationFailBehaviour(final ActionResult actionResult) {
		this.actionResult = actionResult;
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult getActionResult(final Action action, final Method method) {
		return actionResult;
	}

}
