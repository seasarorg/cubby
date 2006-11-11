package org.seasar.cubby.util;

import java.util.ResourceBundle;

/**
 * 例外ベースクラス。
 * propertiesファイルからメッセージを取得して、
 * エラーメッセージを組み立てます。
 * @author agata
 */
public abstract class AbstractMessagedException extends RuntimeException {

	private static final long serialVersionUID = -8329650183819950799L;

	private static final Object[] EMPTY_ARGS = new Object[]{};
	
	/**
	 * メッセージコード
	 */
	private final String code;
	
	/**
	 * 置換文字列の配列
	 */
	private final Object[] args;
	private final ResourceBundle resource;

	/**
	 * コンストラクタ
	 * @param resource メッセージのリソース
	 * @param code メッセージコード
	 */
	public AbstractMessagedException(ResourceBundle resource, String code) {
		this(resource, code, EMPTY_ARGS);
	}
	
	/**
	 * コンストラクタ
	 * @param resource メッセージのリソース
	 * @param code メッセージコード
	 * @param args 置換文字列
	 */
	public AbstractMessagedException(ResourceBundle resource, String code, Object... args) {
		this.code = code;
		this.args = args;
		this.resource = resource;
	}
	
	/**
	 * メッセージコードを取得します。
	 * @return メッセージコード
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * エラーメッセージを取得します。
	 * @return メッセージコードを元に生成されたエラーメッセージ
	 */
	@Override
	public String getMessage() {
		if (args == null || args.length == 0) {
			return Messages.getString(resource, code);
		} else {
			return Messages.getString(resource, code, args);
		}
	}
}
