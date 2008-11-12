package org.seasar.cubby.controller.chain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.container.Container;
import org.seasar.cubby.container.ContainerFactory;
import org.seasar.cubby.controller.ActionContext;

public class DefaultActionHandlerChainFactory implements
		ActionHandlerChainFactory {

	public ActionHandlerChain getActionHandlerChain() {
		return new DefaultActionHandlerChain(iterator());
	}

	protected Iterator<ActionHandler> iterator() {
		List<ActionHandler> handlers = new ArrayList<ActionHandler>();
		Container container = ContainerFactory.getContainer();
		handlers.add(container.lookup(ExceptionActionHandler.class));
		handlers.add(container.lookup(InitializeActionHandler.class));
		handlers.add(container.lookup(ValidationActionHandler.class));
		handlers.add(container.lookup(InvocationActionHandler.class));
		return handlers.iterator();
	}

	private static class DefaultActionHandlerChain implements
			ActionHandlerChain {

		private final Iterator<ActionHandler> iterator;

		DefaultActionHandlerChain(Iterator<ActionHandler> iterator) {
			this.iterator = iterator;
		}

		public ActionResult chain(HttpServletRequest request,
				HttpServletResponse response, ActionContext actionContext)
				throws Exception {
			if (!iterator.hasNext()) {
				// TODO
				throw new RuntimeException("no more handler");
			}
			final ActionHandler handler = iterator.next();
			final ActionResult actionResult = handler.handle(request, response,
					actionContext, this);
			return actionResult;
		}

	}

}
