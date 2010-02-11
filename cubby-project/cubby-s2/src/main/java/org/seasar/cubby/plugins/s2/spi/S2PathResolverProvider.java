/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
import org.seasar.cubby.util.ActionUtils;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * S2Container 向けの {@link PathResolver} のプロバイダです。
 * <p>
 * クラスパスを走査しアクションクラスを登録します。
 * </p>
 * 
 * @author baba
 */
public class S2PathResolverProvider implements PathResolverProvider,
		DetectClassProcessor, Disposable {

	public static final String pathResolver_BINDING = "bindingType=must";

	public static final String namingConvention_BINDING = "bindingType=must";

	public static final String classDetector_BINDING = "bindingType=must";

	/** パスに対応するアクションメソッドを解決するためのクラス。 */
	private PathResolver pathResolver;

	/** 命名規約。 */
	private NamingConvention namingConvention;

	/** クラスパスを走査してクラスを検出するクラス。 */
	public ClassDetector classDetector;

	/** インスタンスが初期化済みであることを示します。 */
	private boolean initialized;

	/** アクションクラスのリスト。 */
	private final List<Class<?>> actionClasses = new ArrayList<Class<?>>();

	/**
	 * パスに対応するアクションメソッドを解決するためのクラスを設定します。
	 * 
	 * @param pathResolver
	 *            パスに対応するアクションメソッドを解決するためのクラス
	 * 
	 */
	public void setPathResolver(final PathResolver pathResolver) {
		this.pathResolver = pathResolver;
	}

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

	/**
	 * 初期化します。
	 */
	public void initialize() {
		synchronized (pathResolver) {
			if (initialized) {
				return;
			}
			classDetector.detect();
			pathResolver.addAll(actionClasses);

			DisposableUtil.add(this);
			initialized = true;
		}
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
		if (!ActionUtils.isActionClass(clazz)) {
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
