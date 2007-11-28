package org.seasar.cubby.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * アクションメソッドの URL。
 * <p>
 * アクションメソッドを起動するための URL を指定するアノテーションです。
 * </p>
 * <p>
 * 使用例
 * 
 * <pre>
 * &#064;Url(&quot;fuga&quot;)
 * public class HogeAction {
 * 	// -&gt; &quot;/fuga/m1&quot;
 * 	public ActionResult m1() {
 * 	}
 * 
 * 	&#064;Url(&quot;list&quot;)
 * 	// -&gt; &quot;/fuga/list&quot;
 * 	public ActionResult m2() {
 * 	}
 * 
 * 	&#064;Url(&quot;/xxx/yyy&quot;)
 * 	// -&gt; &quot;/xxx/yyy&quot;
 * 	public ActionResult m3() {
 * 	}
 * 
 * 	&#064;Url(&quot;/{id}/edit&quot;)
 * 	// {id}部分をリクエストパラメータに追加
 * 	public ActionResult m4() {
 * 	}
 * 
 * 	&#064;Url(&quot;/{userId,a-z}/edit&quot;)
 * 	// {userId}部分をリクエストパラメータに追加。ユーザID部分は小文字アルファベットのみ。
 * 	public ActionResult m5() {
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.TYPE })
public @interface Url {

	/**
	 * アクションメソッドを起動する対象となる HTTP メソッド。
	 * 
	 * @author agata
	 * @author baba
	 * @since 1.0
	 */
	enum RequestMethod {
		/** HTTP GET */
		GET,
		/** HTTP HEAD */
		HEAD,
		/** HTTP POST */
		POST,
		/** HTTP PUT */
		PUT,
		/** HTTP DELETE */
		DELETE,
		/** HTTP OPTIONS */
		OPTIONS,
		/** HTTP TRACE */
		TRACE,
	}

	/**
	 * アクションメソッドのバインディング用URLを指定します。
	 * <p>
	 * URLはアクションクラスのURL＋アクションメソッドのURLで決定されます。
	 * ただし、先頭が『/』の場合コンテキストルートからの絶対パスとして解釈されます。
	 * </p>
	 * <p>
	 * {パラメータ名,正規表現}でプレースホルダーの指定ができます。
	 * </p>
	 * <p>
	 * 正規表現にマッチした場合、マッチした箇所が指定されたパラメータ名に追加され、アクションメソッドが実行されます。
	 * 正規表現は省略可能です。省略した場合「0-9a-zA-Z」と同じ意味になります。
	 * </p>
	 * 
	 * @return アクションメソッドのバインディング用URL
	 */
	String value() default "";

	/**
	 * アクションメソッドが対応するHTTPのメソッドを指定します。
	 * 
	 * @return アクションメソッドが対応するHTTPのメソッド
	 */
	RequestMethod[] accept() default { RequestMethod.GET,
			RequestMethod.POST };

}
