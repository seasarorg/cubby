package org.seasar.cubby.validator;

/**
 * 入力検証を行うクラスのインターフェイスです。
 * @author agata
 */
public interface Validator {
	/**
	 * 入力検証を行います。
	 * 検証エラーがある場合、エラーメッセージを返します。
	 * 検証エラーがない場合、nullを返します。
	 * @param ctx 入力値
	 * @return エラーメッセージ。nullなら検証エラーなし。
	 */
	String validate(ValidationContext ctx);
}
