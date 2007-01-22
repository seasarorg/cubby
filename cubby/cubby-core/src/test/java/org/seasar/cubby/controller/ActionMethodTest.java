package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Form;
import org.seasar.cubby.util.ClassUtils;

import junit.framework.TestCase;

public class ActionMethodTest extends TestCase {

	public void testForm() throws Exception {
		ActionMethod actionMethod = makeActionMethod("dummy2");
		assertNull(actionMethod.getForm());
		actionMethod = makeActionMethod("dummy1");
		assertEquals(Form.THIS, actionMethod.getForm().value());
	}

	private ActionMethod makeActionMethod(String methodName) {
		Method method = ClassUtils.getMethod(MockAction.class, methodName, null);
		ActionFilterChain chain = new ActionFilterChain();
		String[] uriConvationNames = {};
		ActionMethod actionMethod = new ActionMethod(method, chain, uriConvationNames);
		return actionMethod;
	}
	
}
