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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.plugins.s2.detector.ClassDetector;
import org.seasar.cubby.plugins.s2.detector.DetectClassProcessor;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * クラスパスを走査しアクションクラスを登録します。
 * 
 * @author baba
 * @since 2.0.0
 */
public class S2PathResolverProvider implements PathResolverProvider {

	private final PathResolverProvider component;

	public S2PathResolverProvider() {
		final S2Container container = SingletonS2ContainerFactory
				.getContainer();
		this.component = (PathResolverProvider) container
				.getComponent(Component.class);
	}

	public PathResolver getPathResolver() {
		return component.getPathResolver();
	}

	public static class Component implements PathResolverProvider,
			DetectClassProcessor, Disposable {

		private PathResolver pathResolver;

		public static final String pathResolver_BINDING = "bindingType=must";

		/** 命名規約。 */
		private NamingConvention namingConvention;

		public static final String namingConvention_BINDING = "bindingType=must";

		/** クラスパスを走査してクラスを検出するクラス。 */
		public ClassDetector classDetector;

		public static final String classDetector_BINDING = "bindingType=must";

		/** インスタンスが初期化済みであることを示します。 */
		private boolean initialized;

		/** アクションクラスのリスト。 */
		private final List<Class<?>> actionClasses = new ArrayList<Class<?>>();

		/**
		 * クラスパスを走査してクラスを検出するクラスを設定します。
		 * 
		 * @param classDetector
		 *            クラスパスを走査してクラスを検出するクラス
		 */
		public void setClassDetector(final ClassDetector classDetector) {
			this.classDetector = classDetector;
		}

		/**
		 * 命名規約を設定します。
		 * 
		 * @param namingConvention
		 *            命名規約
		 */
		public void setNamingConvention(final NamingConvention namingConvention) {
			this.namingConvention = namingConvention;
		}

		public void setPathResolver(final PathResolver pathResolver) {
			this.pathResolver = pathResolver;
		}

		/**
		 * 初期化します。
		 */
		public void initialize() {
			if (initialized) {
				return;
			}
			classDetector.detect();
			pathResolver.addAll(actionClasses);

			DisposableUtil.add(this);
			initialized = true;
		}

		/**
		 * {@inheritDoc}
		 */
		public void dispose() {
			actionClasses.clear();
			pathResolver.clear();
			initialized = false;
		}

		/**
		 * {@inheritDoc}
		 * <p>
		 * 指定されたパッケージ名、クラス名から導出されるクラスがアクションクラスだった場合はルーティングを登録します。
		 * </p>
		 */
		public void processClass(final String packageName,
				final String shortClassName) {
			if (shortClassName.indexOf('$') != -1) {
				return;
			}
			final String className = ClassUtil.concatName(packageName,
					shortClassName);
			if (!namingConvention.isTargetClassName(className)) {
				return;
			}
			if (!className.endsWith(namingConvention.getActionSuffix())) {
				return;
			}
			final Class<?> clazz = ClassUtil.forName(className);
			if (namingConvention.isSkipClass(clazz)) {
				return;
			}
			if (Modifier.isAbstract(clazz.getModifiers())) {
				return;
			}
			actionClasses.add(clazz);
		}

		/**
		 * {@inheritDoc}
		 */
		public PathResolver getPathResolver() {
			initialize();
			return pathResolver;
		}

	}

}