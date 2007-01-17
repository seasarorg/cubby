package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクションであることを表明します。
 * クラスに指定：全てのpublicかつ戻り値がStringのメソッドをActionとみなします。
 * メソッドに指定：指定されたpublicかつ戻り値がStringのメソッドをActionとみなします。
 * @author agata
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Url {
	enum Method {GET,POST}
	String value() default "";
	Method method() default Url.Method.GET;
}
