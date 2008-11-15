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
package org.seasar.cubby.plugins.s2.spi;

import org.seasar.cubby.internal.spi.JsonProvider;
import org.seasar.framework.util.JSONSerializer;

/**
 * Seasar2 の {@link JSONSerializer} を利用した JSON の機能を提供します。
 * 
 * @author baba
 * @since 2.0.0
 */
public class S2JsonProvider implements JsonProvider {

	/**
	 * {@inheritDoc}
	 */
	public String toJson(Object bean) {
		return JSONSerializer.serialize(bean);
	}

}
