package org.seasar.cubby.validator;

/**
 * 入力検証を行うクラスのインターフェイスです。
 * 
 * @author agata
 * @author baba
 */
public interface Validator {

	/**
	 * 入力検証を行います。
	 * <p>
	 * 検証エラーがある場合、コンテキストにエラーメッセージを追加します。これは、
	 * {@link ValidationContext#hasError()} で確認できます。
	 * </p>
	 * 
	 * @param context
	 *            入力検証コンテキスト
	 * @see ValidationContext#hasError()
	 */
	void validate(ValidationContext context);

}
