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
package org.seasar.cubby.plugins.s2.factory;

import java.util.ArrayList;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.internal.factory.PathResolverFactory;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.impl.PathResolverImpl;
import org.seasar.cubby.internal.util.ClassUtils;
import org.seasar.cubby.internal.util.CubbyUtils;
import org.seasar.cubby.plugins.s2.detector.ClassDetector;
import org.seasar.cubby.plugins.s2.detector.DetectClassProcessor;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * クラスパスから {@link Action} を検索しアクションクラスを登録します。
 * 
 * @author baba
 * @since 2.0.0
 */
public class S2PathResolverFactory implements PathResolverFactory,
		DetectClassProcessor, Disposable {

	/** 命名規約。 */
	private NamingConvention namingConvention;

	public static final String namingConvention_BINDING = "bindingType=must";

	/** クラスパスを走査してクラスを検出するクラス。 */
	public ClassDetector classDetector;

	public static final String classDetector_BINDING = "bindingType=must";

	/** インスタンスが初期化済みであることを示します。 */
	private boolean initialized;

	/** アクションクラスのリスト。 */
	private List<Class<? extends Action>> actionClasses = new ArrayList<Class<? extends Action>>();

	private PathResolver pathResolver = new PathResolverImpl();

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

	// /**
	// * ルーティング情報を取得します。
	// *
	// * @return ルーティング情報
	// */
	// public List<Routing> getRoutings() {
	// initialize();
	// return Collections.unmodifiableList(new ArrayList<Routing>(routings
	// .values()));
	// }
	//
	// /**
	// * アクションクラスのコレクションを取得します。
	// *
	// * @return アクションクラスのコレクション
	// */
	// public Collection<Class<? extends Action>> getActionClasses() {
	// return actionClasses;
	// }

	/**
	 * 初期化します。
	 */
	public void initialize() {
		if (initialized) {
			return;
		}
		classDetector.detect();
		pathResolver.addAllActionClasses(actionClasses);

		DisposableUtil.add(this);
		initialized = true;
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		actionClasses.clear();
		pathResolver.clearAllActionClasses();
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
		final String className = ClassUtils.concatName(packageName,
				shortClassName);
		if (!namingConvention.isTargetClassName(className)) {
			return;
		}
		if (!className.endsWith(namingConvention.getActionSuffix())) {
			return;
		}
		final Class<?> clazz = ClassUtils.forName(className);
		if (namingConvention.isSkipClass(clazz)) {
			return;
		}
		if (!CubbyUtils.isActionClass(clazz)) {
			return;
		}
		final Class<? extends Action> actionClass = cast(clazz);
		actionClasses.add(actionClass);
	}

	/**
	 * 指定されたクラスを <code>Class&lt;? extends Action&gt;</code> にキャストします。
	 * 
	 * @param clazz
	 *            クラス
	 * @return キャストされたクラス
	 */
	@SuppressWarnings("unchecked")
	private static Class<? extends Action> cast(final Class<?> clazz) {
		return Class.class.cast(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	public PathResolver getPathResolver() {
		initialize();
		return pathResolver;
	}

}
