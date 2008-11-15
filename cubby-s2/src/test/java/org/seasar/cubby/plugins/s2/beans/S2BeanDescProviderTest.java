/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.plugins.s2.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.seasar.cubby.internal.beans.BeanDesc;
import org.seasar.cubby.internal.beans.PropertyDesc;
import org.seasar.cubby.internal.spi.BeanDescProvider;
import org.seasar.cubby.plugins.s2.spi.S2BeanDescProvider;

public class S2BeanDescProviderTest {

	BeanDescProvider beanDescProvider = new S2BeanDescProvider();

	@Test
    public void propertyDesc() throws Exception {
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        assertEquals(5, beanDesc.getPropertyDescs().length);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("aaa");
        assertEquals("aaa", propDesc.getPropertyName());
        assertEquals(String.class, propDesc.getPropertyType());
        assertNotNull(propDesc.getReadMethod());
        assertNull(propDesc.getWriteMethod());

        propDesc = beanDesc.getPropertyDesc("CCC");
        assertEquals("CCC", propDesc.getPropertyName());
        assertEquals(boolean.class, propDesc.getPropertyType());
        assertNotNull(propDesc.getReadMethod());
        assertNull(propDesc.getWriteMethod());

        propDesc = beanDesc.getPropertyDesc("eee");
        assertEquals("eee", propDesc.getPropertyName());
        assertEquals(String.class, propDesc.getPropertyType());
        assertNotNull(propDesc.getReadMethod());
        assertNotNull(propDesc.getWriteMethod());

        propDesc = beanDesc.getPropertyDesc("fff");
        assertEquals("fff", propDesc.getPropertyName());
        assertEquals(Boolean.class, propDesc.getPropertyType());

        assertFalse(beanDesc.hasPropertyDesc("hhh"));
        assertFalse(beanDesc.hasPropertyDesc("iii"));
    }

	@Test
    public void invalidProperty() throws Exception {
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean2.class);
        assertEquals("1", false, beanDesc.hasPropertyDesc("aaa"));
    }

    /**
     * 
     */
    public static interface MyInterface {
        /**
         * 
         */
        String HOGE = "hoge";
    }

    /**
     * 
     */
    public static interface MyInterface2 extends MyInterface {
        /**
         * 
         */
        String HOGE = "hoge2";
    }

    /**
     * 
     */
    public static class MyBean implements MyInterface2 {

        private String aaa;

        private String eee;

        /**
         * 
         */
        public String ggg;

        /**
         * @return
         */
        public String getAaa() {
            return aaa;
        }

        /**
         * @param a
         * @return
         */
        public String getBbb(Object a) {
            return null;
        }

        /**
         * @return
         */
        public boolean isCCC() {
            return true;
        }

        /**
         * @return
         */
        public Object isDdd() {
            return null;
        }

        /**
         * @return
         */
        public String getEee() {
            return eee;
        }

        /**
         * @param eee
         */
        public void setEee(String eee) {
            this.eee = eee;
        }

        /**
         * @return
         */
        public Boolean isFff() {
            return null;
        }

        /**
         * @param hhh
         * @return
         */
        public MyBean setHhh(String hhh) {
            return this;
        }

        /**
         * 
         */
        public void getIii() {
        }

        /**
         * @param arg1
         * @param arg2
         * @return
         */
        public Number add(Number arg1, Number arg2) {
            return new Integer(3);
        }

        /**
         * @param arg1
         * @param arg2
         * @return
         */
        public int add2(int arg1, int arg2) {
            return arg1 + arg2;
        }

        /**
         * @param arg
         * @return
         */
        public Integer echo(Integer arg) {
            return arg;
        }

        /**
         * 
         */
        public void throwException() {
            throw new IllegalStateException("hoge");
        }
    }

    /**
     * 
     */
    public class MyBean2 {
        /**
         * 
         */
        public MyBean2() {
        }

        /**
         * @param num
         * @param text
         * @param bean1
         * @param bean2
         */
        public MyBean2(int num, String text, MyBean bean1, MyBean2 bean2) {
        }

        /**
         * @param i
         */
        public void setAaa(int i) {
        }

        /**
         * @param s
         */
        public void setAaa(String s) {
        }
    }

}
