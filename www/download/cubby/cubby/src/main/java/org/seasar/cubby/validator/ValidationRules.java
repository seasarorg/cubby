package org.seasar.cubby.validator;

import java.util.List;

/**
 * 入力検証の定義を保持するクラスのインターフェイスです。
 * @author agata
 *
 */
public interface ValidationRules {
	/**
	 * 入力検証の定義を取得します。
	 * @return
	 */
	List<ValidationRule> getRules();
}
