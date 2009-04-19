package org.seasar.cubby.exmaple.gae;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.seasar.cubby.exmaple.gae.action.FileuploadAction;
import org.seasar.cubby.exmaple.gae.action.HelloAction;
import org.seasar.cubby.exmaple.gae.action.IndexAction;
import org.seasar.cubby.fileupload.StreamFileItemFactory;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected PathResolver getPathResolver() {
				PathTemplateParser parser = new PathTemplateParserImpl();
				final PathResolver pathResolver = new PathResolverImpl(parser);
				pathResolver.add(IndexAction.class);
				pathResolver.add(HelloAction.class);
				pathResolver.add(FileuploadAction.class);
				return pathResolver;
			}

		});


		install(new ServletModule());
		
		bind(FileUpload.class).toInstance(
				new ServletFileUpload(new StreamFileItemFactory()));
		bind(RequestContext.class).toProvider(RequestContextProvider.class).in(
				RequestScoped.class);

	}

	class RequestContextProvider implements Provider<RequestContext> {

		private RequestContext requestContext;

		@Inject
		public RequestContextProvider(HttpServletRequest request) {
			this.requestContext = new ServletRequestContext(request);
		}

		public RequestContext get() {
			return requestContext;
		}
	}

}
