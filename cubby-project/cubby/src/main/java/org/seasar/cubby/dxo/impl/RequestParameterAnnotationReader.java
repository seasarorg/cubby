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
package org.seasar.cubby.dxo.impl;

import java.lang.reflect.Method;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.dxo.FormDxo;
import org.seasar.extension.dxo.annotation.impl.TigerAnnotationReader;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.util.StringUtil;

/**
 * Dxoインタフェースまたはクラスやそのメソッドからアノテーションを読み取るクラスで、リクエストのパラメータとアクションのプロパティとの変換に使用します。
 * <p>
 * 日付関連の処理を{@link CubbyConfiguration}から取得した{@link FormatPattern}へ移譲します。
 * </p>
 * 
 * @see FormDxo
 * @author baba
 * @since 1.0.0
 */
public class RequestParameterAnnotationReader extends TigerAnnotationReader {

	/** フォーマットパターン。 */
	private final FormatPattern formatPattern;

	/**
	 * インスタンス化します。
	 * 
	 * @param container
	 *            コンテナ
	 * @param configuration
	 *            Cubby の全体的な設定情報
	 */
	public RequestParameterAnnotationReader(final S2Container container,
			final CubbyConfiguration configuration) {
		super(container);
		this.formatPattern = configuration.getFormatPattern();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link FormatPattern#getDatePattern()}へ移譲します。
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getDatePattern(final Class dxoClass, final Method method) {
		String datePattern = super.getDatePattern(dxoClass, method);
		if (StringUtil.isEmpty(datePattern) && formatPattern != null) {
			datePattern = formatPattern.getDatePattern();
		}
		return datePattern;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link FormatPattern#getTimePattern()}へ移譲します。
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getTimePattern(final Class dxoClass, final Method method) {
		String timePattern = super.getTimePattern(dxoClass, method);
		if (StringUtil.isEmpty(timePattern) && formatPattern != null) {
			timePattern = formatPattern.getTimePattern();
		}
		return timePattern;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link FormatPattern#getTimestampPattern()}へ移譲します。
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getTimestampPattern(final Class dxoClass, final Method method) {
		String timestampPattern = super.getTimestampPattern(dxoClass, method);
		if (StringUtil.isEmpty(timestampPattern) && formatPattern != null) {
			timestampPattern = formatPattern.getTimestampPattern();
		}
		return timestampPattern;
	}

}
