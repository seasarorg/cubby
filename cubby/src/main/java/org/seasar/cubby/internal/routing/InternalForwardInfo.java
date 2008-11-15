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
package org.seasar.cubby.internal.routing;

import java.util.Map;

/**
 * 内部フォワード情報です。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface InternalForwardInfo {

	/**
	 * 内部フォワード先のパスを取得します。
	 * 
	 * @return 内部フォワード先のパス
	 */
	String getInternalForwardPath();

	/**
	 * リクエストパラメータ名とルーティングのマッピングを取得します。
	 * 
	 * @return OnSubmit リクエストパラメータ名とルーティングのマッピング
	 */
	Map<String, Routing> getOnSubmitRoutings();

}
