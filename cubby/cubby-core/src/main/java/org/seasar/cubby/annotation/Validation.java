package org.seasar.cubby.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.cubby.validator.FormValidator;
import org.seasar.cubby.validator.Validatable;


/**
 * 入力検証を行うことを表明します。
 * 入力検証はアクションの実行前に行われます。
 * 検証に成功した場合：アクションメソッドが実行されます。
 * 検証に失敗した場合：valueで指定されたビューへ遷移します。
 * @author agata
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Validation {
	/**
	 * 検証失敗時の遷移先を指定します。
	 * @return
	 */
	String errorPage();
	Class<? extends Validatable> validator() default FormValidator.class;
	/**
	 * 除外するプロパティ名
	 * @return
	 */
	String[] excludes() default {};
}
