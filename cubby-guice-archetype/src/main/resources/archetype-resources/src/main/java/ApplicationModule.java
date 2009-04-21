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
package ${package};

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
#if ($use-guice-servlet.matches("(?i)y|yes|true|on"))
import com.google.inject.servlet.ServletModule;
#end

import ${package}.action.HelloAction;
import ${package}.action.IndexAction;
import ${package}.service.HelloService;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected PathResolver getPathResolver() {
				final PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();
				final PathResolver pathResolver = new PathResolverImpl(pathTemplateParser);
				pathResolver.add(IndexAction.class);
				pathResolver.add(HelloAction.class);
				return pathResolver;
			}

		});
#if ($use-guice-servlet.matches("(?i)y|yes|true|on"))
		install(new ServletModule());
#end
		// アプリケーションがマルチパート要求を使用する場合は以下のコメント行を有効にしてください。
		// configureFileUpload();
	}

// アプリケーションがマルチパート要求を使用する場合は以下のコメント行を有効にしてください。
//	/**
//	 * commons-fileupload の設定を行います。
//	 * <p>
//	 * {@link org.apache.commons.fileupload.disk.DiskFileItemFactory}
//	 * はテンポラリにローカルファイルシステムを使用します。Goolgle&nbsp;App&nbsp;Engine
//	 * のようにファイルシステムへの書き込みに制限がある場合は
//	 * {@link org.apache.commons.fileupload.disk.DiskFileItemFactory} ではなく、
//	 * {@link org.seasar.cubby.fileupload.StreamFileItemFactory} を試してみてください。
//	 * これはマルチパートのデータをテンポラリを使用せずにオンメモリで処理します。
//	 * </p>
//	 */
//	protected void configureFileUpload() {
//		final FileItemFactory fileItemFactory = new DiskFileItemFactory();
//		bind(FileUpload.class).toInstance(
//				new ServletFileUpload(fileItemFactory));
//		bind(RequestContext.class).toProvider(RequestContextProvider.class).in(
//				RequestScoped.class);
//	}
//
//	/**
//	 * {@link RequestContext} のプロバイダです。
//	 */
//	private static class RequestContextProvider implements
//			Provider<RequestContext> {
//
//		/** {@link RequestContext} です。 */
//		private RequestContext requestContext;
//
//		/**
//		 * インスタンス化します。
//		 * 
//		 * @param request
//		 *            要求
//		 */
//		@Inject
//		public RequestContextProvider(final HttpServletRequest request) {
//			this.requestContext = new ServletRequestContext(request);
//		}
//
//		/**
//		 * {@link RequestContext} を取得します。
//		 */
//		public RequestContext get() {
//			return requestContext;
//		}
//
//	}

}
