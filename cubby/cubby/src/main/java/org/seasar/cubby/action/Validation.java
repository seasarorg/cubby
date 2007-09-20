package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクションメソッド実行前に入力検証を行うことを表します。
 * <p>
 * 入力検証を行うことを表明します。 入力検証はアクションの実行前に行われます。 検証に成功した場合：アクションメソッドが実行されます。
 * 検証に失敗した場合：errorPageで指定されたビューへForwardします。
 * 
 * @author agata
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validation {
	static String DEFAULT_VALIDATION_RULES_FILED = "VALIDATION";

	/**
	 * 入力検証でエラーがあった場合にフォワードするパスを指定します。
	 */
	String errorPage();

	/**
	 * 入力検証の定義を取得するプロパティ名を指定します。
	 */
	String rulesField() default DEFAULT_VALIDATION_RULES_FILED;
}
