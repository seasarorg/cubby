package org.seasar.cubby.validator;

import java.text.MessageFormat;
import java.util.MissingResourceException;

import org.seasar.cubby.util.Messages;

/**
 * 入力検証の基底クラスです。
 * <p>
 * エラーメッセージのリソースからの取得・作成をサポートします。
 * </p>
 * 
 * @author agata
 * @author baba
 */
public abstract class BaseValidator implements Validator {

	/**
	 * エラーメッセージのキー
	 */
	private String messageKey;

	/**
	 * エラーメッセージのキーをセットします。
	 * 
	 * @param messageKey
	 *            エラーメッセージのキー
	 */
	protected void setMessageKey(final String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * エラーメッセージのキーを元に、メッセージを作成して取得します。
	 * 
	 * @param args
	 *            置換文字列
	 * @return 置換後のエラーメッセージ
	 */
	protected String getMessage(final Object... args) {
		String message = Messages.getText(messageKey);
		return MessageFormat.format(message, args);
	}

	/**
	 * 項目名をメッセージリソースから取得します。
	 * 
	 * @param key
	 *            項目名のメッセージキー
	 * @return 項目名の文字列
	 */
	protected String getPropertyMessage(final String key) {
		try {
			return Messages.getText(key);
		} catch (MissingResourceException ex) {
			return key;
		}
	}

}