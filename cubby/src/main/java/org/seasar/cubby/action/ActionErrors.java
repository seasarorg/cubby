package org.seasar.cubby.action;

import java.util.List;
import java.util.Map;

/**
 * アクションで発生したエラーを保持するクラス。
 * 
 * @author agata
 * @author baba
 */
public interface ActionErrors {
	/**
	 * エラーが存在しないかどうかを判定します。
	 * 
	 * @return エラーが存在しなければtrue
	 */
	boolean isEmpty();

	/**
	 * メッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 */
	void add(String message);

	/**
	 * メッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param fieldInfo
	 *            フィールド情報
	 */
	void add(String message, FieldInfo... fieldInfo);

	/**
	 * メッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param fieldNames
	 *            フィールド名
	 */
	void add(String message, String... fieldNames);

	/**
	 * アクションで発生した全てのエラーの一覧を取得します。
	 * 
	 * @return アクションで発生した全てのエラーの一覧
	 */
	List<String> getAll();

	/**
	 * フィールドで発生したエラーの一覧を取得します。
	 * 
	 * @return フィールドで発生したエラーの一覧
	 */
	Map<String, List<String>> getFields();

	/**
	 * インデックス付きフィールドで発生したエラーの一覧を取得します。
	 * 
	 * @return インデックス付きフィールドで発生したエラーの一覧
	 */
	Map<String, Map<Integer, List<String>>> getIndexedFields();

	/**
	 * フィールド以外で発生したエラーの一覧を取得します。
	 * 
	 * @return フィールド以外で発生したエラーの一覧
	 */
	List<String> getOthers();

	/**
	 * エラーをクリアします。
	 */
	void clear();

}