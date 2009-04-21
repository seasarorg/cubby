package org.seasar.cubby.guice_examples;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.seasar.cubby.guice_examples.action.HelloAction;
import org.seasar.cubby.guice_examples.action.IndexAction;
import org.seasar.cubby.guice_examples.service.HelloService;
import org.seasar.cubby.guice_examples.service.impl.HelloServiceImpl;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected PathResolver getPathResolver() {
				final PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();
				final PathResolver pathResolver = new PathResolverImpl(
						pathTemplateParser);
				pathResolver.add(IndexAction.class);
				pathResolver.add(HelloAction.class);
				return pathResolver;
			}

		});
		install(new ServletModule());

		bind(HelloService.class).to(HelloServiceImpl.class).in(Singleton.class);

		configureFileUpload();
	}

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
	protected void configureFileUpload() {
		final FileItemFactory fileItemFactory = new DiskFileItemFactory();
		bind(FileUpload.class).toInstance(
				new ServletFileUpload(fileItemFactory));
		bind(RequestContext.class).toProvider(RequestContextProvider.class).in(
				RequestScoped.class);
	}

	/**
	 * {@link RequestContext} のプロバイダです。
	 */
	private static class RequestContextProvider implements
			Provider<RequestContext> {

		/** {@link RequestContext} です。 */
		private RequestContext requestContext;

		/**
		 * インスタンス化します。
		 * 
		 * @param request
		 *            要求
		 */
		@Inject
		public RequestContextProvider(final HttpServletRequest request) {
			this.requestContext = new ServletRequestContext(request);
		}

		/**
		 * {@link RequestContext} を取得します。
		 */
		public RequestContext get() {
			return requestContext;
		}

	}

}
