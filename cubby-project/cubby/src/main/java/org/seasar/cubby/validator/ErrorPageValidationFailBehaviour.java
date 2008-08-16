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

import static org.seasar.cubby.validator.ValidationUtils.getValidation;
import static org.seasar.cubby.validator.ValidationUtils.getValidationRules;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Validation;

class ErrorPageValidationFailBehaviour implements
		ValidationFailBehaviour, Serializable {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 1L;

	/** メッセージ。 */
	private final String errorMessage;

	/** フィールド名。 */
	private final String[] fieldNames;

	/**
	 * インスタンス化します。
	 */
	public ErrorPageValidationFailBehaviour() {
		this(null);
	}

	/**
	 * インスタンス化します。
	 * 
	 * @param errorMessage
	 *            メッセージ
	 * @param fieldNames
	 *            フィールド名
	 */
	public ErrorPageValidationFailBehaviour(final String errorMessage,
			final String... fieldNames) {
		this.errorMessage = errorMessage;
		this.fieldNames = fieldNames;
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult getActionResult(final Action action, final Method method) {
		if (errorMessage != null && errorMessage.length() > 0) {
			action.getErrors().add(errorMessage, fieldNames);
		}
		final String errorPage;
		final Validation validation = getValidation(method);
		if (validation == null) {
			errorPage = null;
		} else {
			errorPage = validation.errorPage();
		}

		final ValidationRules validationRules = getValidationRules(action,
				validation.rules());
		return validationRules.fail(errorPage);
	}

}
