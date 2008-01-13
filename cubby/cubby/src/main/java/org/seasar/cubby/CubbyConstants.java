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
 * @author agata
 */
public class CubbyConstants {
	
	/**
	 * Cubbyのデフォルトメッセージリソースの名前
	 */
	public static final String RES_MESSAGES = "messages";
	
	/**
	 * パラメータオブジェクトのリクエストの属性中の名前
	 */
	public static final String ATTR_PARAMS = "params";

	/**
	 * アクションオブジェクトのリクエストの属性中の名前
	 */
	public static final String ATTR_ACTION = "action";

	/**
	 * バリデーションエラーを表すオブジェクトのリクエストの属性中の名前
	 */
	public static final String ATTR_VALIDATION_FAIL = "validationFail";

	/**
	 * TODO
	 */
	public static final String ATTR_OUTPUT_VALUES = "outputValues";

	/**
	 * メッセージリソースをMapに変換したオブジェクトのリクエストの属性中の名前
	 */
	public static final String ATTR_MESSAGES = "messages";

	/**
	 * コンテキストパスのリクエストの属性中の名前
	 */
	public static final String ATTR_CONTEXT_PATH = "contextPath";

	/**
	 * TODO
	 */
	public static final String ATTR_ACTION_CLASS_NAME = "org.seasar.cubby.actionClassName";

	/**
	 * TODO
	 */
	public static final String ATTR_METHOD_NAME = "org.seasar.cubby.methodName";

	/**
	 * TODO
	 */
	public static final String ATTR_TOKEN = "org.seasar.cubby.token";
}
