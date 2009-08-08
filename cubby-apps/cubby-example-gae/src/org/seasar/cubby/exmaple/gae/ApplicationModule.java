package org.seasar.cubby.exmaple.gae;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.cubby.action.FlashMap;
import org.seasar.cubby.action.impl.FlashMapImpl;
import org.seasar.cubby.exmaple.gae.action.FileuploadAction;
import org.seasar.cubby.exmaple.gae.action.HelloAction;
import org.seasar.cubby.exmaple.gae.action.IndexAction;
import org.seasar.cubby.exmaple.gae.action.SearchAction;
import org.seasar.cubby.fileupload.StreamFileItemFactory;
import org.seasar.cubby.plugins.guice.CubbyModule;
import org.seasar.cubby.plugins.guice.FileUploadModule;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.ServletModule;
import com.google.inject.util.Modules;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ServletModule());
		install(Modules.override(new CubbyModule(), new FileUploadModule())
				.with(new MyCubbyModule()));

		bind(IndexAction.class);
		bind(HelloAction.class);
		bind(FileuploadAction.class);
		bind(SearchAction.class);
	}

	public static class MyCubbyModule extends AbstractModule {

		@Override
		protected void configure() {
		}

		@Provides
		FileUpload provideFileUpload() {
			final FileItemFactory fileItemFactory = new StreamFileItemFactory();
			final FileUpload fileUpload = new ServletFileUpload(fileItemFactory);
			return fileUpload;
		}

		@Provides
		@RequestScoped
		FlashMap provideFlashMap(final HttpServletRequest request) {
			return new FlashMapImpl(request) {
				@Override
				protected Map<String, Object> createMap() {
					return new HashMap<String, Object>();
				}
			};
		}
	}

}
