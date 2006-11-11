package org.seasar.cubby.seasar;

import java.util.Collection;

import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import junit.framework.TestCase;

public class S2ControllerFactoryTest extends TestCase {

	public void testGetContorollerClassList() {
		SingletonS2ContainerFactory.init();
		S2ControllerFactory factory = new S2ControllerFactory();
		Collection<Class> controllerClassList = factory.getContorollerClassList(null);
		assertEquals("diconに定義された２つのコントローラークラスを取得", 2, controllerClassList.size());
	}
	
}
