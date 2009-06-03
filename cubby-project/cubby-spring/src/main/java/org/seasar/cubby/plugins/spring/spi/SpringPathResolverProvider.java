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
package org.seasar.cubby.plugins.spring.spi;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.spi.PathResolverProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * パスリゾルバプロバイダの実装クラスです。
 * 
 * @author suzuki-kei
 * @author someda
 * @since 2.0.0
 */
public class SpringPathResolverProvider implements PathResolverProvider,
		ApplicationContextAware {

	@Autowired
	private PathResolver pathResolver;

	private ApplicationContext applicationContext;

	private Class<Action> actionClass = Action.class;

	private boolean initialized = false;

	public void setApplicationContext(
			final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public PathResolver getPathResolver() {
		return pathResolver;
	}

	public void initialize() {
		if (initialized) {
			return;
		}
		String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				applicationContext, actionClass);
		for (String name : names) {
			pathResolver.add(applicationContext.getType(name));
		}
		initialized = true;
	}

}
