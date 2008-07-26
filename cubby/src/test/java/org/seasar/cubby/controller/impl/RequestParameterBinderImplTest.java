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
package org.seasar.cubby.controller.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.RequestParameterBinder;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.InputStreamUtil;

/**
 * 
 * @author baba
 */
public class RequestParameterBinderImplTest extends S2TestCase {

	public RequestParameterBinder requestParameterBinder;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include(getClass().getName().replace('.', '/') + ".dicon");
	}

	public void testMapToBeanNullSource() {
		TestBean bean = new TestBean();
		Method method = getActionMethod("foo");
		requestParameterBinder.bind(null, bean, method);
	}

	public void testMapToBean() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("date", new Object[] { "2006-01-01" });

		TestBean bean = new TestBean();

		Method method = getActionMethod("foo");
		requestParameterBinder.bind(map, bean, method);
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(format.format(cal.getTime()), format
				.format(bean.getDate()));
	}

	public void testMapToBean_OneValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num1", new Object[] { "1" });
		map.put("num2", new Object[] { "2" });
		map.put("num3", new Object[] { "def" });

		TestBean bean = new TestBean();

		Method method = getActionMethod("foo");
		requestParameterBinder.bind(map, bean, method);
		assertNotNull(bean.getNum1());
		assertEquals(Integer.valueOf(1), bean.getNum1());
		assertNotNull(bean.getNum2());
		assertEquals(1, bean.getNum2().length);
		assertEquals(Integer.valueOf(2), bean.getNum2()[0]);
		assertNotNull(bean.getNum3());
		assertEquals(1, bean.getNum3().size());
		assertEquals("def", bean.getNum3().get(0));
	}

	public void testMapToBean_MultiValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new Object[] { "1", "2" });
		map.put("num3", new Object[] { "abc", "def" });

		TestBean bean = new TestBean();

		Method method = getActionMethod("foo");
		requestParameterBinder.bind(map, bean, method);
		assertNotNull(bean.getNum2());
		assertEquals(2, bean.getNum2().length);
		assertEquals(Integer.valueOf(1), bean.getNum2()[0]);
		assertEquals(Integer.valueOf(2), bean.getNum2()[1]);
		assertNotNull(bean.getNum3());
		assertEquals(2, bean.getNum3().size());
		assertEquals("abc", bean.getNum3().get(0));
		assertEquals("def", bean.getNum3().get(1));
	}

	public void testMapToBean_MultiValueIncludesEmptyValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new String[] { "1", "", "2" });

		TestBean bean = new TestBean();

		Method method = getActionMethod("foo");
		requestParameterBinder.bind(map, bean, method);
		assertEquals(3, bean.getNum2().length);
		assertEquals(Integer.valueOf(1), bean.getNum2()[0]);
		assertEquals(null, bean.getNum2()[1]);
		assertEquals(Integer.valueOf(2), bean.getNum2()[2]);
	}

	public void testMapToBean_MultiValueIncludesNullValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num3", new String[] { "zzz", null, "xxx" });

		TestBean bean = new TestBean();

		Method method = getActionMethod("foo");
		requestParameterBinder.bind(map, bean, method);
		assertEquals(3, bean.getNum3().size());
		assertEquals("zzz", bean.getNum3().get(0));
		assertNull(bean.getNum3().get(1));
		assertEquals("xxx", bean.getNum3().get(2));
	}

	public void testConvertFileItem() throws UnsupportedEncodingException {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("file", new Object[] { new MockFileItem("123") });
		map.put("bytefile", new Object[] { new MockFileItem("456") });
		map.put("bytefiles", new Object[] { new MockFileItem("abc"), new MockFileItem("def") });
		map.put("input", new Object[] { new MockFileItem("QQ") });

		FileItemHolder dest = new FileItemHolder();

		Method method = getActionMethod("foo");
		requestParameterBinder.bind(map, dest, method);
		String encoding = "UTF-8";
		assertNotNull(dest.file);
		assertEquals("123", new String(dest.file.get(), encoding));
		assertNotNull(dest.bytefile);
		assertEquals("456", new String(dest.bytefile, encoding));
		assertNotNull(dest.bytefiles);
		assertEquals(2, dest.bytefiles.length);
		assertEquals("abc", new String(dest.bytefiles[0], encoding));
		assertEquals("def", new String(dest.bytefiles[1], encoding));
		assertNotNull(dest.input);
		assertEquals("QQ", new String(InputStreamUtil.getBytes(dest.input), encoding));
	}

	public static class TestBean {

		Date date;

		Integer num1;

		Integer[] num2;

		List<String> num3;

		public Date getDate() {
			return new Date(date.getTime());
		}

		public void setDate(Date date) {
			this.date = new Date(date.getTime());
		}

		public Integer getNum1() {
			return num1;
		}

		public void setNum1(Integer num1) {
			this.num1 = num1;
		}

		public Integer[] getNum2() {
			return num2 == null ? null : num2.clone();
		}

		public void setNum2(Integer[] num2) {
			this.num2 = num2 == null ? null : num2.clone();
		}

		public List<String> getNum3() {
			return num3;
		}

		public void setNum3(List<String> num3) {
			this.num3 = num3;
		}

	}

	private Method getActionMethod(String methodName) {
		return ClassUtil.getMethod(MockAction.class, methodName, null);
	}

	private class MockAction extends Action {
		public ActionResult foo() {
			return null;
		}
	}

	static class MockFileItem implements FileItem {

		private static final long serialVersionUID = 1L;

		private String name;

		public MockFileItem(String name) {
			this.name = name;
		}

		public void delete() {
		}

		public byte[] get() {
			try {
				return name.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException();
			}
		}

		public String getContentType() {
			return null;
		}

		public String getFieldName() {
			return null;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(get());
		}

		public String getName() {
			return this.name;
		}

		public OutputStream getOutputStream() throws IOException {
			return null;
		}

		public long getSize() {
			return 0;
		}

		public String getString() {
			return null;
		}

		public String getString(String encoding)
				throws UnsupportedEncodingException {
			return null;
		}

		public boolean isFormField() {
			return false;
		}

		public boolean isInMemory() {
			return false;
		}

		public void setFieldName(String name) {
		}

		public void setFormField(boolean state) {
		}

		public void write(File file) throws Exception {
		}
	}

	public static class FileItemHolder {
		public FileItem file;
		public byte[] bytefile;
		public byte[][] bytefiles;
		public InputStream input;
	}

}
