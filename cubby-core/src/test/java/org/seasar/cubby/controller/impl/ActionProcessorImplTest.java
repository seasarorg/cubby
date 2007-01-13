package org.seasar.cubby.controller.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.seasar.extension.unit.S2TestCase;

public class ActionProcessorImplTest extends S2TestCase {
	
	ActionProcessorImpl actionProcessor;
	
	@Override
	protected void setUp() throws Exception {
		include("app.dicon");
	}
	
	public void testUriPattern() throws Exception {
		Pattern pattern = Pattern.compile("([{]([^}]+)[}])([^{]*)");
		String src = "/{name}/todo/{todoId}";
		Matcher m = pattern.matcher(src);
		while (m.find()) {
			System.out.println(m.group(2));
			src = StringUtils.replace(src, m.group(1), "[a-zA-Z0-9]+");
		}
		System.out.println(src);
	}
	
	public void testGetUrlRewriteForwardPath() throws Exception {
//		assertNotNull(actionFactory);
//		SingletonS2ContainerFactory.init();
//		actionFactory.initialize();
//		String dispacher = actionFactory.getUrlRewriteForwardPath("/sample1/todo/1", ClassUtils.getMethod(todo, methodName, parameterTypes));
//		assertEquals("/cubby/org.seasar.cubby.seasar.Sample1Controller/show?id=1", dispacher);
//
//		dispacher = actionFactory.getUrlRewriteForwardPath("/sample1/detail");
//		assertEquals("/cubby/org.seasar.cubby.seasar.Sample1Controller/detail", dispacher);
//
//		dispacher = actionFactory.getUrlRewriteForwardPath("/sample1/agata/list");
//		assertEquals("/cubby/org.seasar.cubby.seasar.Sample1Controller/list?name=agata", dispacher);
//
//		dispacher = actionFactory.getUrlRewriteForwardPath("/sample1/dummy");
//		assertNull(dispacher);
	}
	
//	public void testGetContorollerClassList() {
//		SingletonS2ContainerFactory.init();
//		Collection<Class> controllerClassList = controllerFactory.getContorollerClassList();
//		assertEquals("diconに定義された２つのコントローラークラスを取得", 2, controllerClassList.size());
//	}

}
