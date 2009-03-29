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
package org.seasar.cubby.plugins.guice.spi;

import java.util.Collection;

import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.ActionHandlerChain;
import org.seasar.cubby.handler.impl.ActionHandlerChainImpl;
import org.seasar.cubby.spi.ActionHandlerChainProvider;

import com.google.inject.Inject;

public class GuiceActionHandlerChainProvider implements
		ActionHandlerChainProvider {

	private final Collection<ActionHandler> actionHandlers;

	@Inject
	public GuiceActionHandlerChainProvider(
			final Collection<ActionHandler> actionHandlers) {
		this.actionHandlers = actionHandlers;
	}

	public ActionHandlerChain getActionHandlerChain() {
		return new ActionHandlerChainImpl(actionHandlers);
	}

}
