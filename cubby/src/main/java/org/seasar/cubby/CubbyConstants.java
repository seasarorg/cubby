/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * フレームワークで使用する定数クラスです。
 * 
 * @author agata
 * @author baba
 */
public class CubbyConstants {

	/** プレフィックス */
	private static final String PREFIX = "org.seasar.cubby.";

	// request --------------------------------------------------------

	/** アクションオブジェクトの要求の属性名 */
	public static final String ATTR_ACTION = "action";

	/** メッセージリソースを <code>Map</code> に変換したオブジェクトの要求の属性名 */
	public static final String ATTR_MESSAGES = "messages";

	/** アクションエラーの要求の属性名 */
	public static final String ATTR_ERRORS = "errors";

	/** コンテキストパスの要求の属性名 */
	public static final String ATTR_CONTEXT_PATH = "contextPath";

	/** パラメータオブジェクトの要求の属性名 */
	public static final String ATTR_PARAMS = PREFIX + "params";

	/** 内部フォワード時にルーティング情報を引き渡すための要求の属性名 */
	public static final String ATTR_ROUTING = PREFIX + "routing";

	/** アクションコンテキストの要求の属性名 */
	public static final String ATTR_ACTION_CONTEXT = PREFIX + "actionContext";

	/** 型変換失敗の要求の属性名 */
	public static final String ATTR_CONVERSION_FAILURES = PREFIX
			+ "conversionFailures";

	/** バリデーションエラーを表すオブジェクトの要求の属性名 */
	public static final String ATTR_VALIDATION_FAIL = PREFIX + "validationFail";

	/** フォームオブジェクトのラッパーファクトリの要求の属性名 */
	public static final String ATTR_FORM_WRAPPER_FACTORY = PREFIX
			+ "formWrapperFactory";

	/** フィルターチェインの要求の属性名 */
	public static final String ATTR_FILTER_CHAIN = PREFIX + "filterChain";

	/** メッセージリソースの属性名 */
	public static final String ATTR_MESSAGES_RESOURCE_BUNDLE = PREFIX
			+ "messagesResourceBundle";

	public static final String ATTR_WRAPEE_REQUEST = PREFIX + "wrapeeRequest";

	// session --------------------------------------------------------

	/** フラッシュメッセージのセッションの属性名 */
	public static final String ATTR_FLASH = "flash";

	/** セッショントークンの <code>Map</code> のセッションの属性名 */
	public static final String ATTR_TOKEN = PREFIX + "token";

}
