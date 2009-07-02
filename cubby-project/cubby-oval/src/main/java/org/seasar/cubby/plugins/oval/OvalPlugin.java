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

import javax.servlet.ServletContext;

import net.sf.oval.Validator;
import net.sf.oval.localization.context.OValContextRenderer;
import net.sf.oval.localization.message.MessageResolver;
import net.sf.oval.logging.LoggerFactorySLF4JImpl;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

/**
 * Oval によるアノテーションベースの入力検証を追加するプラグインです。
 * 
 * @author baba
 */
public class OvalPlugin extends AbstractPlugin {

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link LoggerFactorySLF4JImpl} を引数に {link
	 * {@link Validator#setLoggerFactory(net.sf.oval.logging.LoggerFactory)}
	 * を実行して、ロガーに SLF4J を使用するように設定します。
	 * </p>
	 */
	@Override
	public void initialize(final ServletContext servletContext)
			throws Exception {
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

	/**
	 * {@link MessageResolver} を生成します。
	 * <p>
	 * 指定されたコンテナから {@link MessageResolver} のインスタンスを取得しますが、コンテナに登録されていない場合は
	 * <code>null</code> を返します。その場合は
	 * {@link Validator#setMessageResolver(MessageResolver)} を実行しないので Oval
	 * デフォルトの動作になります。
	 * </p>
	 * 
	 * @param container
	 *            コンテナ
	 * @return {@link MessageResolver}
	 */
	protected MessageResolver buildMessageResolver(final Container container) {
		try {
			return container.lookup(MessageResolver.class);
		} catch (final LookupException e) {
			return null;
		}
	}

	/**
	 * {@link OValContextRenderer} を生成します。
	 * <p>
	 * 指定されたコンテナから {@link OValContextRenderer} のインスタンスを取得しますが、コンテナに登録されていない場合は
	 * <code>null</code> を返します。その場合は
	 * {@link Validator#setContextRenderer(OValContextRenderer)} を実行しないので Oval
	 * デフォルトの動作になります。
	 * </p>
	 * 
	 * @param container
	 *            コンテナ
	 * @return {@link MessageResolver}
	 */
	protected OValContextRenderer buildOvalContextRenderer(
			final Container container) {
		try {
			return container.lookup(OValContextRenderer.class);
		} catch (final LookupException e) {
			return null;
		}
	}

}
