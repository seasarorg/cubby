package org.seasar.cubby.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.cubby.controller.ActionFilter;


/**
 * Interceptorを表明します。
 * @author agata
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Filter {
	Class<? extends ActionFilter>[] value();
}