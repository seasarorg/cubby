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

import java.util.Collection;
import java.util.List;

import org.seasar.cubby.action.ActionResult;

/**
 * アクションメソッドに対する入力検証のルールの集合です。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public interface ValidationRules {

	/**
	 * 入力検証ルールのリストを取得します。
	 * 
	 * @return 入力検証ルールのリスト
	 * @deprecated {@link DefaultValidationRules#addAll(ValidationRules)} を使用してください。
	 */
	@Deprecated
	List<ValidationRule> getRules();

	/**
	 * 入力検証にエラーがあった場合に呼び出されます。
	 * 
	 * @param errorPage
	 *            エラーページ
	 * @return アクションメソッド実行後の処理
	 * @see org.seasar.cubby.action.Validation#errorPage()
	 * @since 1.0.2
	 */
	ActionResult fail(String errorPage);

	/**
	 * 入力検証のフェーズの一覧を実行順に並べた{@link Collection}として取得します。
	 * 
	 * @return 入力検証のフェーズ
	 * @since 1.1.0
	 */
	Collection<ValidationPhase> getValidationPhases();

	/**
	 * 指定された入力検証フェーズに対応する入力検証ルールの{@link Collection}を取得します。
	 * 
	 * @param validationPhase
	 *            入力検証フェーズ
	 * @return 指定された入力検証フェーズに対応する入力検証ルールの{@link Collection}
	 * @since 1.1.0
	 */
	Collection<ValidationRule> getPhaseValidationRules(
			ValidationPhase validationPhase);

}