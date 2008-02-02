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
package org.seasar.cubby.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * アクションの定義を組み立てるビルダです。
 * 
 * @author baba
 */
public interface ActionDefBuilder {

	/**
	 * リクエストからアクションの定義を組み立てます。
	 * <p>
	 * リクエストに対応するアクションが定義されていない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param request
	 *            リクエスト
	 * @return アクション定義
	 */
	ActionDef build(HttpServletRequest request);

}