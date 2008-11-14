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
package org.seasar.cubby.util;

import static org.seasar.cubby.util.LogMessages.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * サービスプロバイダロード機構です。
 * <p>
 * サービスプロバイダは、リソースディレクトリ <code>META-INF/cubby</code>
 * に「プロバイダ構成ファイル」を配置することによって識別されます。
 * このファイルの名前は、サービスの型の完全修飾名になります。このファイルには、具象プロバイダクラスの完全修飾名が 1 行に 1
 * つずつ記述されます。それぞれの名前を囲む空白文字とタブ文字、および空白行は無視されます。コメント文字は '#' ('\u0023'、NUMBER
 * SIGN) です。 行頭にコメント文字が挿入されている場合、その行のすべての文字は無視されます。ファイルは UTF-8 で符号化されている必要があります。
 * </p>
 * 
 * @param <S>
 *            このローダーによってロードされるサービスの型
 * @author baba
 * @since 2.0.0
 */
public class ServiceLoader<S> implements Iterable<S> {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(ServiceLoader.class);

	/** リソースディレクトリ。 */
	private static final String PREFIX = "META-INF/cubby/";

	/** プロバイダのキャッシュ。 */
	private final Map<String, S> providers = new LinkedHashMap<String, S>();

	/** サービスの型。 */
	private final Class<S> service;

	/** クラスローダ。 */
	private final ClassLoader classLoader;

	/**
	 * 指定されたサービスのローダーを構築します。
	 * 
	 * @param service
	 *            サービスを表すインターフェイス
	 * @param classLoader
	 *            プロバイダ構成ファイルとプロバイダクラスのロードに使用するクラスローダー
	 */
	private ServiceLoader(final Class<S> service, final ClassLoader classLoader) {
		this.service = service;
		this.classLoader = classLoader;
		reload();
	}

	/**
	 * プロバイダを再ロードします。
	 */
	public void reload() {
		providers.clear();

		final String resourceName = PREFIX + service.getName();
		try {
			final Enumeration<URL> urls = classLoader
					.getResources(resourceName);
			while (urls.hasMoreElements()) {
				final URL url = urls.nextElement();
				for (final String providerClassName : parse(url)) {
					providers.put(providerClassName, null);
				}
			}
		} catch (final IOException e) {
			throw new ServiceLoadingException(e);
		}
	}

	/**
	 * 指定された URL からプロバイダ構成ファイルを読み込み、パースします。
	 * 
	 * @param url
	 *            プロバイダ構成ファイルの URL
	 * @return プロバイダを実装したクラスの <code>List</code>
	 * @throws IOException
	 *             プロバイダ構成ファイルの読み込み時に {@link IOException} が発生した場合
	 */
	private List<String> parse(final URL url) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0017", service, url));
		}
		final List<String> providerClassNames = new ArrayList<String>();
		InputStream input = null;
		BufferedReader reader = null;
		try {
			input = url.openStream();
			reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
			for (String line; (line = reader.readLine()) != null;) {
				final String providerClassName = cleanup(line);
				if (providerClassName != null) {
					providerClassNames.add(providerClassName);
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (input != null) {
				input.close();
			}
		}
		return providerClassNames;
	}

	/**
	 * 指定された文字列からコメントや空白を取り除きます。
	 * 
	 * @param line
	 *            文字列
	 * @return <code>line</code> からコメントや空白を取り除いた文字列
	 */
	private String cleanup(String line) {
		final int commentIndex = line.indexOf('#');
		if (commentIndex >= 0) {
			line = line.substring(0, commentIndex);
		}
		line = line.trim();
		if (line.length() == 0) {
			return null;
		}
		return line;
	}

	/**
	 * 単一のプロバイダを取得します。
	 * <p>
	 * プロバイダが見つからなかった場合、またはプロバイダが複数見つかった場合は例外が発生します。
	 * </p>
	 * 
	 * @return プロバイダ
	 * @throws ServiceLoadingException
	 *             プロバイダが見つからなかった場合、またはプロバイダが複数見つかった場合
	 */
	public S getProvider() {
		final Iterator<S> iterator = iterator();
		if (!iterator.hasNext()) {
			throw new ServiceLoadingException(format("ECUB0053", service));
		}
		final S provider = iterator.next();
		if (iterator.hasNext()) {
			throw new ServiceLoadingException(format("ECUB0054", service));
		}
		return provider;
	}

	/**
	 * プロバイダの {@link Iterator} を取得します。
	 */
	public Iterator<S> iterator() {
		return new ProviderIterator();
	}

	/**
	 * 指定された型とクラスローダーに対応する新しいサービスローダーを作成します。
	 * 
	 * @param <S>
	 *            サービスの型
	 * @param service
	 *            サービスの型
	 * @param classLoader
	 *            クラスローダー
	 * @return サービスローダー
	 */
	public static <S> ServiceLoader<S> load(final Class<S> service,
			final ClassLoader classLoader) {
		return new ServiceLoader<S>(service, classLoader);
	}

	/**
	 * 指定された型に対応する新しいサービスローダーを作成します。
	 * 
	 * @param <S>
	 *            サービスの型
	 * @param service
	 *            サービスの型
	 * @return サービスローダー
	 */
	public static <S> ServiceLoader<S> load(final Class<S> service) {
		final ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return load(service, classLoader);
	}

	/**
	 * プロバイダの {@link Iterator}
	 * 
	 * @author baba
	 * @since 2.0.0
	 */
	private class ProviderIterator implements Iterator<S> {

		/** プロバイダのキャッシュの {@link Iterator} */
		private final Iterator<Entry<String, S>> providerIterator;

		/**
		 * 新しい <code>ProviderIterator</code> を構築します。
		 */
		ProviderIterator() {
			providerIterator = providers.entrySet().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean hasNext() {
			return providerIterator.hasNext();
		}

		/**
		 * {@inheritDoc}
		 */
		public S next() {
			final Entry<String, S> entry = providerIterator.next();
			if (entry.getValue() == null) {
				final String providerClassName = entry.getKey();
				final S provider = newInstance(providerClassName);
				entry.setValue(provider);
				if (logger.isDebugEnabled()) {
					logger.debug(format("DCUB0018", service, providerClassName,
							provider));
				}
			}
			return entry.getValue();
		}

		/**
		 * 指定されたクラスのインスタンスを生成します。
		 * 
		 * @param className
		 *            クラス名
		 * @return 生成したインスタンス
		 */
		private S newInstance(final String className) {
			try {
				final Class<?> providerClass = Class.forName(className, true,
						classLoader);
				final Object providerInstance = providerClass.newInstance();
				final S provider = service.cast(providerInstance);
				return provider;
			} catch (final ClassNotFoundException e) {
				throw new ServiceLoadingException(e);
			} catch (final InstantiationException e) {
				throw new ServiceLoadingException(e);
			} catch (final IllegalAccessException e) {
				throw new ServiceLoadingException(e);
			} catch (final ClassCastException e) {
				throw new ServiceLoadingException(e);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

}
