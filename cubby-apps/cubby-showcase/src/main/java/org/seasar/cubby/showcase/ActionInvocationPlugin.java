package org.seasar.cubby.showcase;

import java.util.HashSet;
import java.util.Set;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.plugin.ActionInvocation;
import org.seasar.cubby.showcase.todo.helper.ActionInvocationHelper;
import org.seasar.framework.container.SingletonS2Container;

public class ActionInvocationPlugin extends AbstractPlugin {

	private static final Set<String> appliedClassNames;
	static {
		final Set<String> set = new HashSet<String>();
		set.add("org.seasar.cubby.showcase.todo.action.TodoAction");
		set.add("org.seasar.cubby.showcase.todo.action.TodoListAction");
		appliedClassNames = set;
	}

	@Override
	public ActionResult invokeAction(final ActionInvocation invocation)
			throws Exception {
		final Class<?> actionClass = invocation.getActionContext()
				.getActionClass();
		if (appliedClassNames.contains(actionClass.getName())) {
			final ActionInvocationHelper helper = SingletonS2Container
					.getComponent(ActionInvocationHelper.class);
			helper.initialize(invocation);
			return helper.proceed();
		} else {
			return super.invokeAction(invocation);
		}
	}

}
