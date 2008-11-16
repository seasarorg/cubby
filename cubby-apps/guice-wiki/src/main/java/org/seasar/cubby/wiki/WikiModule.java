package org.seasar.cubby.wiki;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.wiki.action.PageAction;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;

public class WikiModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new AbstractCubbyModule() {

			@Override
			protected Collection<Class<? extends Action>> getActionClasses() {
				List<Class<? extends Action>> actionClasses = new ArrayList<Class<? extends Action>>();
				actionClasses.add(PageAction.class);
				return actionClasses;
			}

		});
		install(new ServletModule());

		//bind(PageService.class).to(Pagese.class).in(Singleton.class);
	}

}
