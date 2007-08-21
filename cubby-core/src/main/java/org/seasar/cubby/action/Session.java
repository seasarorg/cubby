package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * セッションスコープを表明します。
 * これで指定されたフィールドはセッションスコープとして扱われます。
 * @author agata
 * @since 1.0
 */
@Deprecated
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Session {
}
