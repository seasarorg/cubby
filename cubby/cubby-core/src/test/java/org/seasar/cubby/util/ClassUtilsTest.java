package org.seasar.cubby.util;

import junit.framework.TestCase;

public class ClassUtilsTest extends TestCase {
	
	public void testGetSetter() throws Exception {
		assertEquals("setName", ClassUtils.getSetter(Hoge.class, "name").getName());
		assertEquals("setAge", ClassUtils.getSetter(Fuga.class, "age").getName());
	}

	public void testGetSetterNested() throws Exception {
		//		assertEquals("setAge", ClassUtils.getSetter(Hoge.class, "name.age").getName());
	}

	class Hoge {
		private String name;
		private Fuga fuga;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	
	class Fuga {
		private Integer age;

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}
	}
	
}
