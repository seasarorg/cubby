package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 入力値検証のコンテキスト。
 * 
 * @author agata
 * @author baba
 */
public class ValidationContext {

	private List<MessageInfo> messageInfos = new ArrayList<MessageInfo>();

	/**
	 * メッセージ情報を追加します。
	 * 
	 * @param messageInfo
	 *            メッセージ情報
	 */
	public void addMessageInfo(MessageInfo messageInfo) {
		messageInfos.add(messageInfo);
	}

	public List<MessageInfo> getMessageInfos() {
		return messageInfos;
	}

}
