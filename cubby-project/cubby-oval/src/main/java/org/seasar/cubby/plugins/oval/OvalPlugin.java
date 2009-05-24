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
package org.seasar.cubby.plugins.oval;

import javax.servlet.ServletConfig;

import net.sf.oval.Validator;
import net.sf.oval.localization.context.OValContextRenderer;
import net.sf.oval.localization.message.MessageResolver;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class OvalPlugin extends AbstractPlugin {

	public void initialize(final ServletConfig config) {
		Validator.setLoggerFactory(new LoggerFactorySLF4JImpl());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void ready() {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();

		final MessageResolver messageResolver = buildMessageResolver(container);
		if (messageResolver != null) {
			Validator.setMessageResolver(messageResolver);
		}

		final OValContextRenderer ovalContextRenderer = buildOvalContextRenderer(container);
		if (ovalContextRenderer != null) {
			Validator.setContextRenderer(ovalContextRenderer);
		}
	}

	// /**
	// * {@inheritDoc}
	// */
	// public <S extends Provider> S getProvider(final Class<S> service) {
	// return null;
	// }

	protected MessageResolver buildMessageResolver(final Container container) {
		try {
			return container.lookup(MessageResolver.class);
		} catch (final LookupException e) {
			return null;
		}
	}

	protected OValContextRenderer buildOvalContextRenderer(
			final Container container) {
		try {
			return container.lookup(OValContextRenderer.class);
		} catch (final LookupException e) {
			return null;
		}
	}

	public static class Context {

	}

}
