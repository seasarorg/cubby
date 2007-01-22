package org.seasar.cubby.controller.filters;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.controller.MockAction;
import org.seasar.cubby.util.ClassUtils;

public class InvocationFilterTest extends TestCase {
	InvocationFilter f = new InvocationFilter();

	MockAction action = new MockAction();

	private Method actionMethod1;

	private Method actionMethod2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		actionMethod1 = ClassUtils.getMethod(MockAction.class, "dummy1",
				new Class[] {});
		actionMethod2 = ClassUtils.getMethod(MockAction.class, "dummy2",
				new Class[] {});
		assertNotNull(actionMethod1);
		assertNotNull(actionMethod2);
	}

	public void testInvokeActionMethod() throws Throwable {
		assertTrue("dummy1の戻り値はForward", f.invokeActionMethod(action,
				actionMethod1) instanceof Forward);
		assertTrue("dummy2の戻り値はRedirect", f.invokeActionMethod(action,
				actionMethod2) instanceof Redirect);
	}
}
