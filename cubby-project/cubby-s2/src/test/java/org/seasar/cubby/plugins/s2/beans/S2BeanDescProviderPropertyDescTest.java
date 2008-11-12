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
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.seasar.cubby.beans.BeanDesc;
import org.seasar.cubby.beans.IllegalPropertyException;
import org.seasar.cubby.beans.PropertyDesc;
import org.seasar.cubby.plugins.s2.spi.S2BeanDescProvider;
import org.seasar.cubby.spi.BeanDescProvider;

public class S2BeanDescProviderPropertyDescTest {

	BeanDescProvider beanDescProvider = new S2BeanDescProvider();

	@Test
	public void setValue() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("fff");
        propDesc.setValue(myBean, new BigDecimal(2));
        assertEquals(2, myBean.getFff());
    }

	@Test
    public void setValue_null() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("fff");
        propDesc.setValue(myBean, null);
        assertEquals(0, myBean.getFff());
    }

	@Test
    public void setValue_notWritable() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("aaa");
        try {
            propDesc.setValue(myBean, null);
            fail();
        } catch (IllegalPropertyException e) {
            System.out.println(e);
        }
    }

	@Test
    public void setValue_notWritableWithField() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("jjj");
        try {
            propDesc.setValue(myBean, null);
            fail();
        } catch (IllegalPropertyException e) {
            System.out.println(e);
        }
    }

	@Test
    public void getValue_notReable() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("iii");
        try {
            propDesc.getValue(myBean);
            fail();
        } catch (IllegalPropertyException e) {
            System.out.println(e);
        }
    }

	@Test
    public void getValue_notReableWithField() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("kkk");
        try {
            propDesc.getValue(myBean);
            fail();
        } catch (IllegalPropertyException e) {
            System.out.println(e);
        }
    }

	@Test
    public void setIllegalValue() throws Exception {
        MyBean myBean = new MyBean();
        BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
        PropertyDesc propDesc = beanDesc.getPropertyDesc("fff");
        try {
            propDesc.setValue(myBean, "hoge");
            fail("1");
        } catch (IllegalPropertyException ex) {
            System.out.println(ex);
        }
    }

    /**
     * 
     */
    public static class MyBean {

        private int fff_;

        private BigDecimal ggg_;

        private Timestamp hhh_;

        private String jjj;

        String kkk;

        private URL url_;

        private Calendar cal;

        /**
         * 
         */
        public String str;

        /**
         * @return
         */
        public String getAaa() {
            return null;
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
            return null;
        }

        /**
         * @param eee
         */
        public void setEee(String eee) {
        }

        /**
         * @return
         */
        public int getFff() {
            return fff_;
        }

        /**
         * @param fff
         */
        public void setFff(int fff) {
            fff_ = fff;
        }

        /**
         * @return
         */
        public String getJjj() {
            return jjj;
        }

        /**
         * @param kkk
         */
        public void setKkk(String kkk) {
            this.kkk = kkk;
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
         * @return
         */
        public BigDecimal getGgg() {
            return ggg_;
        }

        /**
         * @param ggg
         */
        public void setGgg(BigDecimal ggg) {
            this.ggg_ = ggg;
        }

        /**
         * @return
         */
        public Timestamp getHhh() {
            return hhh_;
        }

        /**
         * @param hhh
         */
        public void setHhh(Timestamp hhh) {
            this.hhh_ = hhh;
        }

        /**
         * @param iii
         */
        public void setIii(String iii) {
        }

        /**
         * @return
         */
        public URL getURL() {
            return url_;
        }

        /**
         * @param url
         */
        public void setURL(URL url) {
            url_ = url;
        }

        /**
         * @return Returns the cal.
         */
        public Calendar getCal() {
            return cal;
        }

        /**
         * @param cal
         *            The cal to set.
         */
        public void setCal(Calendar cal) {
            this.cal = cal;
        }
    }

}
