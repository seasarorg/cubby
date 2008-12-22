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

import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.spi.ActionHandlerChainProvider;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class S2ActionHandlerChainProvider implements ActionHandlerChainProvider {

	public ActionHandlerChain getActionHandlerChain() {
		final S2Container container = SingletonS2ContainerFactory
				.getContainer();
		final ActionHandlerChain actionHandlerChain = (ActionHandlerChain) container
				.getComponent(ActionHandlerChain.class);
		return actionHandlerChain;
	}

}
