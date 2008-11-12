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
package org.seasar.cubby.plugins.s2.detector.impl;

import org.seasar.cubby.plugins.s2.detector.ClassDetector;
import org.seasar.cubby.plugins.s2.detector.DetectClassProcessor;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.cooldeploy.CoolComponentAutoRegister;
import org.seasar.framework.util.Disposable;
import org.seasar.framework.util.DisposableUtil;

/**
 * クラスパスを走査してクラスを検出するクラスの実装です。
 * <p>
 * Cool Deploy の挙動と合わせるため、{@link CoolComponentAutoRegister} を継承しています。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class ClassDetectorImpl extends CoolComponentAutoRegister implements
		ClassDetector, Disposable {

	/** スーパークラスの初期化メソッドをクリアします。 */
	public static final String INIT_METHOD = null;

	/** 初期化フラグ。 */
	private boolean initialized;

	/** 検出されたクラスを処理するクラスの配列です。 */
	private DetectClassProcessor[] processors;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void processClass(final String packageName,
			final String shortClassName) {
		for (final DetectClassProcessor processor : processors) {
			processor.processClass(packageName, shortClassName);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void detect() {
		if (!initialized) {
			processors = DetectClassProcessor[].class.cast(getContainer()
					.getRoot().findAllComponents(DetectClassProcessor.class));
			registerAll();
			DisposableUtil.add(this);
			initialized = true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		initialized = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected ComponentDef createComponentDef(final Class componentClass) {
		throw new UnsupportedOperationException();
	}

}
