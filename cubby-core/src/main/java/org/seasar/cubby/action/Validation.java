package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 入力検証を行うことを表明します。
 * 入力検証はアクションの実行前に行われます。
 * 検証に成功した場合：アクションメソッドが実行されます。
 * 検証に失敗した場合：errorPageで指定されたビューへForwardします。
 * @author agata
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validation {
	static String DEFAULT_VALIDATORS_FILED = "VALIDATORS";
	/**
	 * 検証失敗時の遷移先を指定します。
	 * @return
	 */
	String errorPage();
	String validator() default DEFAULT_VALIDATORS_FILED;
}
