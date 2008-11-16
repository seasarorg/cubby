package org.seasar.cubby.wiki;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.action.impl.FormatPatternImpl;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.wiki.action.PageAction;
import org.seasar.cubby.wiki.converter.PageConverter;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class ApplicationModule extends AbstractModule {
	@Override
	protected void configure() {
		install(new ServletModule());
		install(new JpaModule());
		bind(FormatPattern.class).to(FormatPatternImpl.class).in(Singleton.class);
		install(new ApplicationCubbyModule());
	}

	private static class ApplicationCubbyModule extends AbstractCubbyModule {
		@Override
		protected Collection<Class<? extends Action>> getActionClasses() {
			List<Class<? extends Action>> actionClasses = new ArrayList<Class<? extends Action>>();
			actionClasses.add(PageAction.class);
			return Collections.unmodifiableCollection(actionClasses);
		}

		@Override
		protected Collection<Class<? extends Converter>> getConverterClasses() {
			List<Class<? extends Converter>> converterClasses = new ArrayList<Class<? extends Converter>>();
			converterClasses.addAll(super.getConverterClasses());
			converterClasses.add(PageConverter.class);
			return Collections.unmodifiableCollection(converterClasses);
		}
	}
}
