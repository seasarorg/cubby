package org.seasar.cubby.validator;

import java.util.Map;

import org.seasar.cubby.action.FormatPattern;

/**
 * 入力値のコンテキストオブジェクト
 * @author agata
 */
public class ValidationContext {

	/**
	 * パラメータ名
	 */
	private final String name;

	/**
	 * 入力値
	 */
	private final Object value;

	/**
	 * 入力パラメータの一覧
	 */
	private final Map<String, Object> params;

	/**
	 * 日付フォーマットパターン
	 */
	private final FormatPattern formatPattern;

	/**
	 * コンストラクタ
	 * @param name パラメータ名
	 * @param value 入力値
	 * @param params 入力パラメータの一覧
	 * @param formatPattern 日付フォーマットパターン
	 */
	public ValidationContext(final String name, final Object value,
			final Map<String, Object> params, final FormatPattern formatPattern) {
		this.name = name;
		this.value = value;
		this.params = params;
		this.formatPattern = formatPattern;
	}

	/**
	 * パラメータ名を取得します。
	 * @return パラメータ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 入力値を取得します。
	 * @return 入力値
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 入力パラメータの一覧を取得します。
	 * @return　入力パラメータの一覧
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * 日付フォーマットパターンを取得します。
	 * @return 日付フォーマットパターン
	 */
	public FormatPattern getFormatPattern() {
		return formatPattern;
	}

}
