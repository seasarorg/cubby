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
package org.seasar.cubby.routing.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarFile;

import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.ClassLoaderUtil;
import org.seasar.framework.util.ClassTraversal;
import org.seasar.framework.util.JarFileUtil;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.StringUtil;
import org.seasar.framework.util.URLUtil;
import org.seasar.framework.util.ZipFileUtil;
import org.seasar.framework.util.ClassTraversal.ClassHandler;

/**
 * クラスを抽出するクラスです。
 * 
 */
abstract class ClassCollector implements ClassHandler {

	private final Map<String, Strategy> strategies = new HashMap<String, Strategy>();

	private final NamingConvention namingConvention;

	/**
	 * {@link ClassCollector}を作成します。
	 */
	public ClassCollector(NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
		addStrategy("file", new FileSystemStrategy());
		addStrategy("jar", new JarFileStrategy());
		addStrategy("zip", new ZipFileStrategy());
		addStrategy("code-source", new CodeSourceFileStrategy());
	}

	/**
	 * 登録されているストラテジを返します。
	 * 
	 * @return 登録されているストラテジ
	 */
	public Map<String, Strategy> getStrategies() {
		return strategies;
	}

	/**
	 * {@link Strategy} を返します。
	 * 
	 * @param protocol
	 * @return {@link Strategy}
	 */
	protected Strategy getStrategy(String protocol) {
		return (Strategy) strategies.get(URLUtil.toCanonicalProtocol(protocol));
	}

	/**
	 * {@link Strategy}を追加します。
	 * 
	 * @param protocol
	 * @param strategy
	 */
	protected void addStrategy(String protocol, Strategy strategy) {
		strategies.put(protocol, strategy);
	}

	/**
	 * 自動登録を行います。
	 */
	public void collect() {
		final String[] rootPackageNames = namingConvention
				.getRootPackageNames();
		if (rootPackageNames != null) {
			for (int i = 0; i < rootPackageNames.length; ++i) {
				final String rootDir = rootPackageNames[i].replace('.', '/');
				for (final Iterator<?> it = ClassLoaderUtil.getResources(rootDir); it
						.hasNext();) {
					final URL url = (URL) it.next();
					final Strategy strategy = getStrategy(URLUtil
							.toCanonicalProtocol(url.getProtocol()));
					strategy.collect(rootDir, url, rootPackageNames[i]);
				}
			}
			webSphereClassLoaderFix();
		}
	}

	/**
	 * Jarファイルからコンポーネントの登録を行います。
	 * <p>
	 * WebSphere のクラスローダーはJarファイル中のディレクトリエントリを<code>ClassLoader#getResource()</code>で
	 * 返してくれないので、 S2のJarと同じ場所にあるJarファイルからコンポーネントの登録を行います。
	 * </p>
	 */
	protected void webSphereClassLoaderFix() {
		final URL url = ResourceUtil.getResourceNoException(getClass()
				.getName().replace('.', '/')
				+ ".class");
		if ("wsjar".equals(url.getProtocol())) {
			final File s2JarFile = new File(JarFileUtil.toJarFile(url)
					.getName());
			final File libDir = s2JarFile.getParentFile();
			final File[] jarFiles = libDir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".jar");
				}
			});
			for (int i = 0; i < jarFiles.length; ++i) {
				final JarFile jarFile = JarFileUtil.create(jarFiles[i]);
				ClassTraversal.forEach(jarFile, this);
			}
		}
	}

	/**
	 * プロトコルに応じた自動登録を行なうストラテジです。
	 * 
	 */
	protected interface Strategy {
		/**
		 * 自動登録を行います。
		 * 
		 * @param path
		 * @param url
		 */
		void collect(String path, URL url, String rootPackageName);
	}

	/**
	 * ファイルシステム用の
	 * {@link org.seasar.framework.container.cooldeploy.CoolComponentAutoRegister.Strategy}です。
	 * 
	 */
	protected class FileSystemStrategy implements Strategy {

		public void collect(String path, URL url, String rootPackageName) {
			File rootDir = getRootDir(path);
			ClassTraversal.forEach(rootDir, rootPackageName,
					ClassCollector.this);
		}
 
		protected File getRootDir(String path) {
            File file = ResourceUtil.getResourceAsFile(path);
            String[] names = StringUtil.split(path, "/");
            for (int i = 0; i < names.length; ++i) {
                file = file.getParentFile();
            }
            return file;
        }
    }

	/**
	 * jarファイル用の {@link ClassCollector.Strategy}です。
	 * 
	 */
	protected class JarFileStrategy implements Strategy {

		public void collect(String path, URL url, String rootPackageName) {
			JarFile jarFile = createJarFile(url);
			ClassTraversal.forEach(jarFile, ClassCollector.this);
		}

		/**
		 * {@link JarFile}を作成します。
		 * 
		 * @param url
		 * @return {@link JarFile}
		 */
		protected JarFile createJarFile(URL url) {
			return JarFileUtil.toJarFile(url);
		}
	}

	/**
	 * WebLogic固有の<code>zip:</code>プロトコルで表現されるURLをサポートするストラテジです。
	 */
	protected class ZipFileStrategy implements Strategy {

		public void collect(String path, URL url, String rootPackageName) {
			final JarFile jarFile = createJarFile(url);
			ClassTraversal.forEach(jarFile, ClassCollector.this);
		}

		/**
		 * {@link JarFile}を作成します。
		 * 
		 * @param url
		 * @return {@link JarFile}
		 */
		protected JarFile createJarFile(URL url) {
			final String jarFileName = ZipFileUtil.toZipFilePath(url);
			return JarFileUtil.create(new File(jarFileName));
		}
	}

	/**
	 * OC4J固有の<code>code-source:</code>プロトコルで表現されるURLをサポートするストラテジです。
	 */
	protected class CodeSourceFileStrategy implements Strategy {

		public void collect(String path, URL url, String rootPackageName) {
			final JarFile jarFile = createJarFile(url);
			ClassTraversal.forEach(jarFile, ClassCollector.this);
		}

		/**
		 * {@link JarFile}を作成します。
		 * 
		 * @param url
		 * @return {@link JarFile}
		 */
		protected JarFile createJarFile(final URL url) {
			final URL jarUrl = URLUtil.create("jar:file:" + url.getPath());
			return JarFileUtil.toJarFile(jarUrl);
		}
	}
}