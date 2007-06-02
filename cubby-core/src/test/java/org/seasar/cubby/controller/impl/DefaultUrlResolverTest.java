package org.seasar.cubby.controller.impl;

import junit.framework.TestCase;

import org.seasar.cubby.controller.ResolveResult;
import org.seasar.cubby.seasar.Sample1Controller;
import org.seasar.cubby.util.ClassUtils;

public class DefaultUrlResolverTest extends TestCase {

	public void testResolve() throws Exception {
		DefaultUrlResolver resolver = new DefaultUrlResolver();
		resolver.add("/sample/{id}", Sample1Controller.class, "show");
		ResolveResult result = resolver.resolve("/sample/1");
		assertEquals(ClassUtils.getMethod(Sample1Controller.class, "show",null), result.getActionMethod().getMethod());
		
		// format:/cubby-dispacher/(controllerClassName)/(actionMethod)
		result = resolver.resolve("/cubby-dispacher/org.seasar.cubby.seasar.Sample1Controller/show");
		assertEquals(ClassUtils.getMethod(Sample1Controller.class, "show",null), result.getActionMethod().getMethod());
	}
	
	class AppUrlResolver extends DefaultUrlResolver {
		public AppUrlResolver() {
			add("/sub/sample/{id,[0-9]+}", Sample1Controller.class, "show");
			add("/sub/{name}/list", Sample1Controller.class, "list");
		}
	}
}
