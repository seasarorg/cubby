package org.seasar.cubby.action;

import java.util.List;
import java.util.Map;

/**
 * アクションで発生したエラーを保持するクラス。
 * @author agata
 */
public interface ActionErrors {
	/**
	 * エラーが存在しないかどうかを判定します。
	 * @return エラーが存在しなければtrue
	 */
	boolean isEmpty();

	/**
	 * 指定されたフィールドのエラーが存在するかどうかを判定します。
	 * @param name フィールド名
	 * @return エラーが存在すればtrue
	 */	
	boolean hasFieldError(String name);

	/**
	 * アクションエラーを追加します。
	 * @param エラーメッセージ
	 */	
	void addActionError(String message);

	/**
	 * フィールドエラーを追加します。
	 * @param name フィールド名
	 * @param message エラーメッセージ
	 */
	void addFieldError(String name, String message);
	
	/**
	 * アクションで発生したエラーの一覧を取得します。
	 * @return アクションで発生したエラーの一覧
	 */	
	List<String> getAllErrors();
		
	/**
	 * アクションで発生したエラーの一覧を取得します。
	 * @return アクションで発生したエラーの一覧
	 */
	List<String> getActionErrors();
	
	/**
	 * フィールドで発生したエラーの一覧を取得します。
	 * @return フィールドで発生したエラーの一覧
	 */	
	Map<String, List<String>> getFieldErrors();
}