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
package org.seasar.cubby.plugin;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.spi.Provider;

/**
 * プラグインの抽象的な実装です。
 * 
 * @author baba
 */
public abstract class AbstractPlugin implements Plugin {

	/** プラグインがサポートするサービスのセット。 */
	private final Set<Class<? extends Provider>> supportedServices = new LinkedHashSet<Class<? extends Provider>>();

	/**
	 * プラグインがサポートするサービスを追加します。
	 * 
	 * @param service
	 *            サービス
	 */
	protected void support(final Class<? extends Provider> service) {
		supportedServices.add(service);
	}

	/**
	 * このプラグインが指定されたサービスをサポートするかを示します。
	 * 
	 * @param service
	 *            サービス
	 * @return このプラグインが指定されたサービスをサポートする場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	protected boolean isSupport(final Class<? extends Provider> service) {
		return supportedServices.contains(service);
	}

	/**
	 * {@inheritDoc}
	 */
	public void initialize(final ServletContext servletContext) {
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Class<? extends Provider>> getSupportedServices() {
		return supportedServices;
	}

	/**
	 * {@inheritDoc}
	 */
	public <S extends Provider> S getProvider(final Class<S> service) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void ready() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokeRequestProcessing(
			final RequestProcessingInvocation invocation) throws Exception {
		invocation.proceed();
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult invokeAction(final ActionInvocation invocation)
			throws Exception {
		return invocation.proceed();
	}

	/**
	 * {@inheritDoc}
	 */
	public void invokeActionResult(final ActionResultInvocation invocation)
			throws Exception {
		invocation.proceed();
	}

}
