package org.seasar.cubby.controller.filters;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.seasar.cubby.controller.MockController;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.controller.results.Redirect;
import org.seasar.cubby.util.ClassUtils;

public class InvocationFilterTest extends TestCase {
	InvocationFilter f = new InvocationFilter();

	MockController controller = new MockController();

	private Method actionMethod1;

	private Method actionMethod2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		actionMethod1 = ClassUtils.getMethod(MockController.class, "dummy1",
				new Class[] {});
		actionMethod2 = ClassUtils.getMethod(MockController.class, "dummy2",
				new Class[] {});
		assertNotNull(actionMethod1);
		assertNotNull(actionMethod2);
	}

	public void testInvokeActionMethod() throws Throwable {
		assertTrue("dummy1の戻り値はForward", f.invokeActionMethod(controller,
				actionMethod1) instanceof Forward);
		assertTrue("dummy2の戻り値はRedirect", f.invokeActionMethod(controller,
				actionMethod2) instanceof Redirect);
	}
}
