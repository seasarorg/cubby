package org.seasar.cubby.validator;

public class MessageHelper {

	/**
	 * メッセージのキー。
	 */
	private String messageKey;

	/**
	 * 初期化します。
	 * 
	 * @param messageKey
	 *            エラーメッセージのキー
	 */
	public MessageHelper(final String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * メッセージのキーを元に、メッセージ情報を作成して取得します。
	 * 
	 * @param arguments
	 *            置換文字列
	 * @return メッセージ情報
	 */
	public MessageInfo createMessageInfo(final Object... arguments) {
		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		messageInfo.setArguments(arguments);
		return messageInfo;
	}

}
