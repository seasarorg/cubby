package org.seasar.cubby.validator;

/**
 * 入力値検証のコンテキスト。
 * 
 * @author agata
 * @author baba
 */
public interface ValidationContext {

	/**
	 * メッセージ情報を追加します。
	 * 
	 * @param messageInfo
	 *            メッセージ情報
	 */
	void addMessageInfo(MessageInfo messageInfo);

}
