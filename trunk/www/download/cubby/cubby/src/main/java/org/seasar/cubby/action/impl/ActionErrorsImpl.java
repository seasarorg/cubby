package org.seasar.cubby.action.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

/**
 * アクションエラーの実装クラス
 * @author agata
 *
 */
public class ActionErrorsImpl implements ActionErrors {

	/**
	 * アクションで発生したエラーの一覧
	 */
	private List<String> actionErrors;

	/**
	 * フィールドで発生したエラーの一覧
	 */
	private Map<String, List<String>> fieldErrors;

	/**
	 * アクションとフィールドのエラーを合わせた全てのエラーの一覧
	 */
	private List<String> allErrors;

	public List<String> getActionErrors() {
		return actionErrors;
	}

	/**
	 * アクションで発生したエラーの一覧をセットします。
	 * @param actionErrors アクションで発生したエラーの一覧
	 */
	public void setActionErrors(final List<String> actionErrors) {
		this.actionErrors = actionErrors;
	}

	/**
	 * フィールドで発生したエラーの一覧を取得します。
	 * @return フィールドで発生したエラーの一覧
	 */
	public Map<String, List<String>> getFieldErrors() {
		return fieldErrors;
	}

	/**
	 * フィールドで発生したエラーの一覧をセットします。
	 * @param fieldErrors アクションで発生したエラーの一覧
	 */
	public void setFieldErrors(final Map<String, List<String>> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	/**
	 * アクションで発生したエラーの一覧を取得します。
	 * @return アクションで発生したエラーの一覧
	 */
	public List<String> getAllErrors() {
		return allErrors;
	}

	/**
	 * 全てのエラーをセットします。
	 * @param allErrors 全てのエラー
	 */
	public void setAllErrors(final List<String> allErrors) {
		this.allErrors = allErrors;
	}

	/**
	 * エラーが存在しないかどうかを判定します。
	 * @return エラーが存在しなければtrue
	 */
	public boolean isEmpty() {
		return actionErrors.isEmpty() && fieldErrors.isEmpty();
	}

	/**
	 * アクションエラーを追加します。
	 * @param エラーメッセージ
	 */
	public void addActionError(final String message) {
		actionErrors.add(message);

		allErrors.add(message);
	}

	/**
	 * フィールドエラーを追加します。
	 * @param name フィールド名
	 * @param message エラーメッセージ
	 */
	public void addFieldError(final String name, final String message) {
		if (!fieldErrors.containsKey(name)) {
			fieldErrors.put(name, new ArrayList<String>());
		}
		fieldErrors.get(name).add(message);

		allErrors.add(message);
	}

	/**
	 * 指定されたフィールドのエラーが存在するかどうかを判定します。
	 * @param name フィールド名
	 * @return エラーが存在すればtrue
	 */
	public boolean hasFieldError(final String name) {
		return fieldErrors.containsKey(name);
	}

}
