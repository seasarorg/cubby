package org.seasar.cubby.plugins.guice.factory;

import java.util.Collection;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.factory.PathResolverFactory;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;

import com.google.inject.Inject;

public class GuicePathResolverFactory implements PathResolverFactory {

	private final PathResolver pathResolver;

	@Inject
	public GuicePathResolverFactory(ActionClassesFactory actionClassesFactory) {
		this.pathResolver = new PathResolverImpl();
		this.pathResolver.addAllActionClasses(actionClassesFactory.getActionClasses());
	}

	public PathResolver getPathResolver() {
		return pathResolver;
	}

	public interface ActionClassesFactory {
		Collection<Class<? extends Action>> getActionClasses();
	}

}
