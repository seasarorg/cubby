/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.spi.ActionHandlerChainProvider;
import org.seasar.framework.container.S2Container;

public class S2ActionHandlerChainProvider implements ActionHandlerChainProvider {

	public static final String s2Container_BINDING = "bindingType=must";

	private S2Container s2Container;

	public void setS2Container(final S2Container s2Container) {
		this.s2Container = s2Container;
	}

	public ActionHandlerChain getActionHandlerChain() {
		final ActionHandlerChain actionHandlerChain = (ActionHandlerChain) s2Container
				.getRoot().getComponent(ActionHandlerChain.class);
		return actionHandlerChain;
	}

}
