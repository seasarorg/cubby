package org.seasar.cubby.validator;

import org.seasar.cubby.action.FormatPattern;

/**
 * 入力値検証のコンテキスト。
 * 
 * @author agata
 * @author baba
 */
public interface ValidationContext {

	/**
	 * フォーマットパターンを取得します。
	 * 
	 * @return フォーマットパターン
	 */
	FormatPattern getFormatPattern();

	/**
	 * メッセージ情報を追加します。
	 * 
	 * @param messageInfo
	 *            メッセージ情報
	 */
	void addMessageInfo(MessageInfo messageInfo);

}
