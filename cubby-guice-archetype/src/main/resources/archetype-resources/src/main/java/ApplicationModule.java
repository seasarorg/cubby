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

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
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
		install(new ServletModule());

		configureFileUpload();
	}

	protected void configureFileUpload() {
		// "org.apache.commons.fileupload.disk.DiskFileItemFactory" uses the local file system temporary.
		// When there is a limitation in writing in the filesystem like Goolgle App Engine,
		// please try not DiskFileItemFactory but "org.seasar.cubby.fileupload.StreamFileItemFactory".
		// This processes multipart-data on memory without using temporary. 

		final FileItemFactory fileItemFactory = new DiskFileItemFactory();
		bind(FileUpload.class).toInstance(
				new ServletFileUpload(fileItemFactory));
		bind(RequestContext.class).toProvider(RequestContextProvider.class).in(
				RequestScoped.class);
	}

	/**
	 * {@link RequestContext} provider
	 */
	private static class RequestContextProvider implements
			Provider<RequestContext> {

		/** {@link RequestContext} */
		private RequestContext requestContext;

		/**
		 * Instantiate.
		 * 
		 * @param request
		 *            Request
		 */
		@Inject
		public RequestContextProvider(final HttpServletRequest request) {
			this.requestContext = new ServletRequestContext(request);
		}

		/**
		 * Get {@link RequestContext}
		 */
		public RequestContext get() {
			return requestContext;
		}

	}

}
