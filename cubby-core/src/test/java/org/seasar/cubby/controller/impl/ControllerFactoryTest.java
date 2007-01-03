package org.seasar.cubby.controller.impl;

import java.util.Collection;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import junit.framework.TestCase;

public class ControllerFactoryTest extends TestCase {

	public void testGetContorollerClassList() {
		SingletonS2ContainerFactory.init();
		ControllerFactoryImpl factory = new ControllerFactoryImpl();
		Collection<Class> controllerClassList = factory.getContorollerClassList(null);
		assertEquals("diconに定義された２つのコントローラークラスを取得", 2, controllerClassList.size());
	}
	
}
