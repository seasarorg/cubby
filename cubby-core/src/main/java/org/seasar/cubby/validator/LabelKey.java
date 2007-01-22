package org.seasar.cubby.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * フィールドのリソースキーを表明します。 Examples: public class HogeDto1 { // key="name1" private
 * String name1;
 *  // key="aaa"
 * @ResourceKey("aaa") private String name2; }
 * 
 * @ResourceKey("hoge2.") public class HogeDto2 { // key="hoge2.name1" private
 *                        String name1;
 *  // key="hoge2.aaa"
 * @ResourceKey("aaa") private String name2; }
 * 
 * @author agata
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE, ElementType.FIELD })
public @interface LabelKey {
	String value();
}
