package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.FormatPattern;

/**
 * 入力値のコンテキストオブジェクト
 * 
 * @author agata
 * @author baba
 */
public class ValidationContext {

	/**
	 * パラメータ名
	 */
	private final String name;

	/**
	 * 入力値
	 */
	private final Object[] values;

	/**
	 * 入力パラメータの一覧
	 */
	private final Map<String, Object[]> params;

	/**
	 * 日付フォーマットパターン
	 */
	private final FormatPattern formatPattern;

	/**
	 * エラーメッセージ
	 */
	private final List<String> messages = new ArrayList<String>();

	/**
	 * コンストラクタ
	 * 
	 * @param name
	 *            パラメータ名
	 * @param values
	 *            入力値
	 * @param params
	 *            入力パラメータの一覧
	 * @param formatPattern
	 *            日付フォーマットパターン
	 */
	public ValidationContext(final String name, final Object[] values,
			final Map<String, Object[]> params,
			final FormatPattern formatPattern) {
		this.name = name;
		this.values = values;
		this.params = params;
		this.formatPattern = formatPattern;
	}

	/**
	 * パラメータ名を取得します。
	 * 
	 * @return パラメータ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 入力値を取得します。
	 * 
	 * @return 入力値
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * 入力パラメータの一覧を取得します。
	 * 
	 * @return 入力パラメータの一覧
	 */
	public Map<String, Object[]> getParams() {
		return params;
	}

	/**
	 * 日付フォーマットパターンを取得します。
	 * 
	 * @return 日付フォーマットパターン
	 */
	public FormatPattern getFormatPattern() {
		return formatPattern;
	}

	/**
	 * メッセージを追加します。
	 * 
	 * @param message
	 *            メッセージ
	 */
	public void addMessage(final String message) {
		this.messages.add(message);
	}

	/**
	 * このオブジェクトが保持するメッセージの一覧を取得します。
	 * 
	 * @return メッセージの一覧
	 */
	public List<String> getMessages() {
		return messages;
	}

	/**
	 * バリデーションにエラーがあったかを返します。
	 * <p>
	 * メッセージの有無でエラーがあったかどうかを判定しますので、バリデーション実行前は常に <code>true</code> を返します。
	 * </p>
	 * 
	 * @return エラーがあった場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	public boolean hasError() {
		return !messages.isEmpty();
	}

}
