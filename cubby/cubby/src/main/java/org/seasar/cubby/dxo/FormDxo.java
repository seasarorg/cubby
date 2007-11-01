package org.seasar.cubby.dxo;

import java.util.Map;

/**
 * フォームオブジェクトと {@link Map} の変換。
 * 
 * @author baba
 */
public interface FormDxo {

	/**
	 * リクエストパラメータを格納した {@link Map} からアクションのフォームオブジェクトへ値をマッピングします。
	 * 
	 * @param src
	 *            リクエストパラメータを格納した {@link Map}
	 * @param dest
	 *            フォームオブジェクト
	 */
	void convert(Map<String, Object[]> src, Object dest);

	/**
	 * アクションのフォームオブジェクトから&lt;form&gt;カスタムタグで使用する {@link Map} へ値をマッピングします。
	 * 
	 * @param src
	 *            フォームオブジェクト
	 * @param dest
	 *            &lt;form&gt;カスタムタグで使用する {@link Map}
	 */
	void convert(Object src, Map<String, String[]> dest);

}