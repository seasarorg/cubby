package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクションメソッドが対応するHTTPのメソッド。
 * 
 * @author baba
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Accept {

	RequestMethod[] value() default { RequestMethod.GET, RequestMethod.POST };

}
