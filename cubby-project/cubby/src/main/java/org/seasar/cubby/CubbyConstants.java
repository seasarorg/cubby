/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby;

/**
 * Cubbyフレームワークで使用する定数クラスです。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class CubbyConstants {

	/**
	 * 内部フォワード先のディレクトリ。
	 */
	public static final String INTERNAL_FORWARD_DIRECTORY = "/__internal_forward";

	/**
	 * Cubbyのデフォルトメッセージリソースの名前。
	 */
	public static final String RES_MESSAGES = "messages";

	/**
	 * アクションオブジェクトのリクエストの属性中の名前。
	 */
	public static final String ATTR_ACTION = "action";

	/**
	 * メッセージリソースをMapに変換したオブジェクトのリクエストの属性中の名前。
	 */
	public static final String ATTR_MESSAGES = "messages";

	/**
	 * コンテキストパスのリクエストの属性中の名前。
	 */
	public static final String ATTR_CONTEXT_PATH = "contextPath";

	/**
	 * パラメータオブジェクトのリクエストの属性中の名前。
	 */
	public static final String ATTR_PARAMS = "org.seasar.cubby.params";

	/**
	 * 内部フォワード時にルーティング情報を引き渡すための名前。
	 */
	public static final String ATTR_ROUTINGS = "org.seasar.cubby.routings";

	/**
	 * バリデーションエラーを表すオブジェクトのリクエストの属性中の名前。
	 */
	public static final String ATTR_VALIDATION_FAIL = "org.seasar.cubby.validationFail";

	/**
	 * トークンのMapをセッションに保存する名前。
	 */
	public static final String ATTR_TOKEN = "org.seasar.cubby.token";

}