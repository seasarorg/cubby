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

package org.seasar.cubby.plugins.guice;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;

/**
 * commons-fileupload の設定を行います。
 * <p>
 * {@link org.apache.commons.fileupload.disk.DiskFileItemFactory}
 * はテンポラリにローカルファイルシステムを使用します。Goolgle&nbsp;App&nbsp;Engine
 * のようにファイルシステムへの書き込みに制限がある場合は
 * {@link org.apache.commons.fileupload.disk.DiskFileItemFactory} ではなく、
 * {@link org.seasar.cubby.fileupload.StreamFileItemFactory} を試してみてください。
 * これはマルチパートのデータをテンポラリを使用せずにオンメモリで処理します。
 * </p>
 */
public class FileUploadModule extends AbstractModule {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configure() {
	}

	@Provides
	FileUpload provideFileUpload() {
		final FileItemFactory fileItemFactory = new DiskFileItemFactory();
		final FileUpload fileUpload = new ServletFileUpload(fileItemFactory);
		return fileUpload;
	}

	@Provides
	@RequestScoped
	RequestContext provideRequestContext(final HttpServletRequest request) {
		final RequestContext requestContext = new ServletRequestContext(request);
		return requestContext;
	}

}
