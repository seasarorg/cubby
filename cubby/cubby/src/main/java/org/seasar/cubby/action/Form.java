package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * リクエストパラメータがバインディングされるオブジェクトを指定します。
 * 
 * <pre>
 * public class HogeAction {
 *   private FugaBean fuga;
 *   public FugaBean getFuga() {
 *     return this.fuga;
 *   }
 * 
 *   // &#64;Formの指定がないため、バインディングされません。
 *   public ActionResult m1() {
 *   }
 * 
 *   &#64;Form // -&gt; HogeActionにバインディングします。
 *   public ActionResult m2() {
 *   }
 * 
 *   &#64;Form(&quot;fuga&quot;) // プロパティfugaにバインディングします。
 *   public ActionResult m3() {
 *   }
 * }
 * 
 * &#64;Form(&quot;fuga&quot;) // 全アクションメソッドに対して一括でバインディングの指定を行います。
 * public class Hoge2Action {
 *   private FugaBean fuga;
 *   public FugaBean getFuga() {
 *     return this.fuga;
 *   }
 * 
 *   private ZzzBean zzz;
 *   public ZzzBean getZzz() {
 *     return this.zzz;
 *   }
 * 
 *   // プロパティfugaにバインディングします(クラスでの指定が有効なため)。
 *   public ActionResult m1() {
 *   }
 * 
 *   &#64;Form(&quot;zzz&quot;) // プロパティzzzにバインディングします（アクションメソッドでの指定が優先されるため）。
 *   public ActionResult m2() {
 *   }
 * }
 * </pre>
 * 
 * @author agata
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Form {
	/** アクションメソッド自身にリクエストパラメータがバインディングされることを表します */
	public static final String THIS = "this";

	/**
	 * バインディングするオブジェクトのプロパティ名。
	 * <p>
	 * "this" が指定された場合は、アクションクラス自身にリクエストパラメータがバインディングされることを表します。
	 * </p>
	 */
	String value() default THIS;
}
