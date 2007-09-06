package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * フォームを表明します。
 * フォームはフィールド名を指定します。
 * クラスに指定：全てのアクションで使用するFormを指定します。
 * メソッドに指定：個々のアクションで使用するFormを指定します。
 * @author agata
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Form {
	public static final String THIS = "this";
	String value() default THIS;
}
