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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.controller.RequestParameterBinder;
import org.seasar.extension.unit.S2TestCase;

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
		FormDto dto = new FormDto();
		requestParameterBinder.bind(null, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
	}

	public void testMapToBean() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("date", new Object[] { "2006-01-01" });

		FormDto dto = new FormDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(format.format(cal.getTime()), format.format(dto.date));
	}

	public void testMapToBean_OneValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num1", new Object[] { "1" });
		map.put("num2", new Object[] { "2" });
		map.put("num3", new Object[] { "def" });

		FormDto dto = new FormDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		assertNotNull(dto.num1);
		assertEquals(Integer.valueOf(1), dto.num1);
		assertNotNull(dto.num2);
		assertEquals(1, dto.num2.length);
		assertEquals(Integer.valueOf(2), dto.num2[0]);
		assertNotNull(dto.num3);
		assertEquals(1, dto.num3.size());
		assertEquals("def", dto.num3.get(0));
	}

	public void testMapToBean_MultiValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new Object[] { "1", "2" });
		map.put("num3", new Object[] { "abc", "def" });

		FormDto dto = new FormDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		assertNotNull(dto.num2);
		assertEquals(2, dto.num2.length);
		assertEquals(Integer.valueOf(1), dto.num2[0]);
		assertEquals(Integer.valueOf(2), dto.num2[1]);
		assertNotNull(dto.num3);
		assertEquals(2, dto.num3.size());
		assertEquals("abc", dto.num3.get(0));
		assertEquals("def", dto.num3.get(1));
	}

	public void testMapToBean_MultiValueIncludesEmptyValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new String[] { "1", "", "2" });

		FormDto dto = new FormDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		assertEquals(3, dto.num2.length);
		assertEquals(Integer.valueOf(1), dto.num2[0]);
		assertEquals(null, dto.num2[1]);
		assertEquals(Integer.valueOf(2), dto.num2[2]);
	}

	public void testMapToBean_MultiValueIncludesNullValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num3", new String[] { "zzz", null, "xxx" });

		FormDto dto = new FormDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		assertEquals(3, dto.num3.size());
		assertEquals("zzz", dto.num3.get(0));
		assertNull(dto.num3.get(1));
		assertEquals("xxx", dto.num3.get(2));
	}

	public void testConverters() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("decimal", new Object[] { "12.3" });
		map.put("decimals", new Object[] { "45.6", "78.9" });
		map.put("bigint", new Object[] { "9876" });
		map.put("bigints", new Object[] { "5432", "10" });
		map.put("bool1", new Object[] { "true" });
		map.put("bools1", new Object[] { "true", "false" });
		map.put("bool2", new Object[] { "false" });
		map.put("bools2", new Object[] { "false", "true", "false" });
		map.put("byte1", new Object[] { "12" });
		map.put("bytes1", new Object[] { "34", "56" });
		map.put("byte2", new Object[] { "98" });
		map.put("bytes2", new Object[] { "76", "54" });
		map.put("char1", new Object[] { "a" });
		map.put("chars1", new Object[] { "b", "c" });
		map.put("char2", new Object[] { "d" });
		map.put("chars2", new Object[] { "e", "f" });
		map.put("date", new Object[] { "2008-7-28" });
		map.put("dates", new Object[] { "2008-8-14", "2008-10-30" });
		map.put("double1", new Object[] { "1.2" });
		map.put("doubles1", new Object[] { "3.4", "5.6" });
		map.put("double2", new Object[] { "9.8" });
		map.put("doubles2", new Object[] { "7.6", "5.4" });
		map.put("en", new Object[] { "VALUE1" });
		map.put("ens", new Object[] { "VALUE2", "VALUE3" });
		map.put("float1", new Object[] { "1.2" });
		map.put("floats1", new Object[] { "3.4", "5.6" });
		map.put("float2", new Object[] { "9.8" });
		map.put("floats2", new Object[] { "7.6", "5.4" });
		map.put("int1", new Object[] { "12" });
		map.put("ints1", new Object[] { "34", "56" });
		map.put("int2", new Object[] { "98" });
		map.put("ints2", new Object[] { "76", "54" });
		map.put("long1", new Object[] { "12" });
		map.put("longs1", new Object[] { "34", "56" });
		map.put("long2", new Object[] { "98" });
		map.put("longs2", new Object[] { "76", "54" });
		map.put("short1", new Object[] { "12" });
		map.put("shorts1", new Object[] { "34", "56" });
		map.put("short2", new Object[] { "98" });
		map.put("shorts2", new Object[] { "76", "54" });
		map.put("sqldate", new Object[] { "2008-7-28" });
		map.put("sqldates", new Object[] { "2008-8-14", "2008-10-30" });
		map.put("sqltime", new Object[] { "12:34:56" });
		map.put("sqltimes", new Object[] { "13:45:24", "23:44:00" });
		map.put("sqltimestamp", new Object[] { "2008-7-28 12:34:56" });
		map.put("sqltimestamps", new Object[] { "2008-8-14 13:45:24",
				"2008-10-30 23:44:00" });

		ConvertersDto dto = new ConvertersDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));

		assertNotNull(dto.decimal);
		assertTrue(new BigDecimal("12.3").compareTo(dto.decimal) == 0);

		assertNotNull(dto.decimals);
		assertEquals(2, dto.decimals.length);
		assertTrue(new BigDecimal("45.6").compareTo(dto.decimals[0]) == 0);
		assertTrue(new BigDecimal("78.9").compareTo(dto.decimals[1]) == 0);

		assertNotNull(dto.bigint);
		assertTrue(new BigInteger("9876").compareTo(dto.bigint) == 0);

		assertNotNull(dto.bigints);
		assertEquals(2, dto.bigints.length);
		assertTrue(new BigInteger("5432").compareTo(dto.bigints[0]) == 0);
		assertTrue(new BigInteger("10").compareTo(dto.bigints[1]) == 0);

		assertNotNull(dto.bool1);
		assertTrue(dto.bool1);

		assertNotNull(dto.bools1);
		assertEquals(2, dto.bools1.length);
		assertTrue(dto.bools1[0]);
		assertFalse(dto.bools1[1]);

		assertFalse(dto.bool2);

		assertNotNull(dto.bools2);
		assertEquals(3, dto.bools2.length);
		assertFalse(dto.bools2[0]);
		assertTrue(dto.bools2[1]);
		assertFalse(dto.bools2[2]);

		assertNotNull(dto.byte1);
		assertEquals(new Byte((byte) 12), dto.byte1);

		assertNotNull(dto.bytes1);
		assertEquals(2, dto.bytes1.length);
		assertEquals(new Byte((byte) 34), dto.bytes1[0]);
		assertEquals(new Byte((byte) 56), dto.bytes1[1]);

		assertEquals((byte) 98, dto.byte2);

		assertNotNull(dto.bytes2);
		assertEquals(2, dto.bytes2.length);
		assertEquals((byte) 76, dto.bytes2[0]);
		assertEquals((byte) 54, dto.bytes2[1]);

		assertNotNull(dto.char1);
		assertEquals(new Character('a'), dto.char1);

		assertNotNull(dto.chars1);
		assertEquals(2, dto.chars1.length);
		assertEquals(new Character('b'), dto.chars1[0]);
		assertEquals(new Character('c'), dto.chars1[1]);

		assertNotNull(dto.char2);
		assertEquals('d', dto.char2);

		assertNotNull(dto.chars2);
		assertEquals(2, dto.chars2.length);
		assertEquals('e', dto.chars2[0]);
		assertEquals('f', dto.chars2[1]);

		assertNotNull(dto.date);
		assertEquals(new Date(fromDateToMillis(2008, 7, 28)), dto.date);

		assertNotNull(dto.dates);
		assertEquals(2, dto.dates.length);
		assertEquals(new Date(fromDateToMillis(2008, 8, 14)), dto.dates[0]);
		assertEquals(new Date(fromDateToMillis(2008, 10, 30)), dto.dates[1]);

		assertNotNull(dto.double1);
		assertEquals(new Double(1.2d), dto.double1);

		assertNotNull(dto.doubles1);
		assertEquals(2, dto.doubles1.length);
		assertEquals(new Double(3.4d), dto.doubles1[0]);
		assertEquals(new Double(5.6d), dto.doubles1[1]);

		assertEquals(9.8d, dto.double2);

		assertNotNull(dto.doubles2);
		assertEquals(2, dto.doubles2.length);
		assertEquals(7.6d, dto.doubles2[0]);
		assertEquals(5.4d, dto.doubles2[1]);

		assertNotNull(dto.en);
		assertSame(ExEnum.VALUE1, dto.en);

		assertNotNull(dto.ens);
		assertEquals(2, dto.ens.length);
		assertSame(ExEnum.VALUE2, dto.ens[0]);
		assertSame(ExEnum.VALUE3, dto.ens[1]);

		assertNotNull(dto.float1);
		assertEquals(new Float(1.2f), dto.float1);

		assertNotNull(dto.floats1);
		assertEquals(2, dto.floats1.length);
		assertEquals(new Float(3.4f), dto.floats1[0]);
		assertEquals(new Float(5.6f), dto.floats1[1]);

		assertEquals(9.8f, dto.float2);

		assertNotNull(dto.floats2);
		assertEquals(2, dto.floats2.length);
		assertEquals(7.6f, dto.floats2[0]);
		assertEquals(5.4f, dto.floats2[1]);

		assertNotNull(dto.int1);
		assertEquals(new Integer(12), dto.int1);

		assertNotNull(dto.ints1);
		assertEquals(2, dto.ints1.length);
		assertEquals(new Integer(34), dto.ints1[0]);
		assertEquals(new Integer(56), dto.ints1[1]);

		assertEquals(98, dto.int2);

		assertNotNull(dto.ints2);
		assertEquals(2, dto.ints2.length);
		assertEquals(76, dto.ints2[0]);
		assertEquals(54, dto.ints2[1]);

		assertNotNull(dto.long1);
		assertEquals(new Long(12l), dto.long1);

		assertNotNull(dto.longs1);
		assertEquals(2, dto.longs1.length);
		assertEquals(new Long(34l), dto.longs1[0]);
		assertEquals(new Long(56l), dto.longs1[1]);

		assertEquals(98l, dto.long2);

		assertNotNull(dto.longs2);
		assertEquals(2, dto.longs2.length);
		assertEquals(76l, dto.longs2[0]);
		assertEquals(54l, dto.longs2[1]);

		assertNotNull(dto.short1);
		assertEquals(new Short((short) 12), dto.short1);

		assertNotNull(dto.shorts1);
		assertEquals(2, dto.shorts1.length);
		assertEquals(new Short((short) 34), dto.shorts1[0]);
		assertEquals(new Short((short) 56), dto.shorts1[1]);

		assertEquals((short) 98, dto.short2);

		assertNotNull(dto.shorts2);
		assertEquals(2, dto.shorts2.length);
		assertEquals((short) 76, dto.shorts2[0]);
		assertEquals((short) 54, dto.shorts2[1]);

		assertNotNull(dto.sqldate);
		assertEquals(new java.sql.Date(fromDateToMillis(2008, 7, 28)),
				dto.sqldate);

		assertNotNull(dto.sqldates);
		assertEquals(2, dto.sqldates.length);
		assertEquals(new java.sql.Date(fromDateToMillis(2008, 8, 14)),
				dto.sqldates[0]);
		assertEquals(new java.sql.Date(fromDateToMillis(2008, 10, 30)),
				dto.sqldates[1]);

		assertNotNull(dto.sqltime);
		assertEquals(new Time(fromTimeToMillis(12, 34, 56)), dto.sqltime);

		assertNotNull(dto.sqltimes);
		assertEquals(2, dto.sqltimes.length);
		assertEquals(new Time(fromTimeToMillis(13, 45, 24)), dto.sqltimes[0]);
		assertEquals(new Time(fromTimeToMillis(23, 44, 00)), dto.sqltimes[1]);

		assertNotNull(dto.sqltimestamp);
		assertEquals(new Timestamp(fromTimestampToMillis(2008, 7, 28, 12, 34,
				56)), dto.sqltimestamp);

		assertNotNull(dto.sqltimestamps);
		assertEquals(2, dto.sqltimestamps.length);
		assertEquals(new Timestamp(fromTimestampToMillis(2008, 8, 14, 13, 45,
				24)), dto.sqltimestamps[0]);
		assertEquals(new Timestamp(fromTimestampToMillis(2008, 10, 30, 23, 44,
				00)), dto.sqltimestamps[1]);

		System.out.println(dto);
	}

	public void testConvertFileItem() throws UnsupportedEncodingException {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("file", new Object[] { new MockFileItem("123") });
		map.put("bytefile", new Object[] { new MockFileItem("456") });
		map.put("bytefiles", new Object[] { new MockFileItem("abc"),
				new MockFileItem("def") });
		map.put("bytefilelist", new Object[] { new MockFileItem("GHI"),
				new MockFileItem("JKL") });
		map.put("input", new Object[] { new MockFileItem("QQ") });

		FileItemDto dto = new FileItemDto();

		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		String encoding = "UTF-8";
		assertNotNull(dto.file);
		assertEquals("123", new String(dto.file.get(), encoding));
		assertNotNull(dto.bytefile);
		assertEquals("456", new String(dto.bytefile, encoding));
		assertNotNull(dto.bytefiles);
		assertEquals(2, dto.bytefiles.length);
		assertEquals("abc", new String(dto.bytefiles[0], encoding));
		assertEquals("def", new String(dto.bytefiles[1], encoding));
		assertNotNull(dto.bytefilelist);
		assertEquals(2, dto.bytefilelist.size());
		Iterator<byte[]> it = dto.bytefilelist.iterator();
		assertEquals("GHI", new String(it.next(), encoding));
		assertEquals("JKL", new String(it.next(), encoding));
		assertNotNull(dto.input);
		assertEquals("QQ", new String(getBytes(dto.input), encoding));
	}

	public void testBindTypeNoAnnotated() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		FormDto2 dto = new FormDto2();
		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "noAnnotated"));
		assertNotNull(dto.hasRequestParameter);
		assertEquals("abc", dto.hasRequestParameter);
		assertNull(dto.noRequestParameter);
	}

	public void testBindTypeNoBindingType() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		FormDto2 dto = new FormDto2();
		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "noBindingType"));
		assertNotNull(dto.hasRequestParameter);
		assertEquals("abc", dto.hasRequestParameter);
		assertNotNull(dto.noRequestParameter);
		assertEquals("def", dto.noRequestParameter);
	}

	public void testBindTypeAllProperties() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		FormDto2 dto = new FormDto2();
		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "all"));
		assertNotNull(dto.hasRequestParameter);
		assertEquals("abc", dto.hasRequestParameter);
		assertNotNull(dto.noRequestParameter);
		assertEquals("def", dto.noRequestParameter);
	}

	public void testBindTypeOnlySpecifiedProperties() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		FormDto2 dto = new FormDto2();
		requestParameterBinder.bind(map, dto, MockAction.class, actionMethod(
				MockAction.class, "specified"));
		assertNotNull(dto.hasRequestParameter);
		assertEquals("abc", dto.hasRequestParameter);
		assertNull(dto.noRequestParameter);
	}

	public void testBindTypeNoAnnotatedOnClass() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		FormDto2 dto = new FormDto2();
		requestParameterBinder.bind(map, dto, MockAction2.class, actionMethod(
				MockAction2.class, "noAnnotated"));
		assertNotNull(dto.hasRequestParameter);
		assertEquals("abc", dto.hasRequestParameter);
		assertNotNull(dto.noRequestParameter);
		assertEquals("def", dto.noRequestParameter);
	}

	public void testBindTypeOnlySpecifiedPropertiesOnClass() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		FormDto2 dto = new FormDto2();
		requestParameterBinder.bind(map, dto, MockAction2.class, actionMethod(
				MockAction2.class, "specified"));
		assertNotNull(dto.hasRequestParameter);
		assertEquals("abc", dto.hasRequestParameter);
		assertNull(dto.noRequestParameter);
	}

	private Method actionMethod(Class<? extends Action> actionClass,
			String methodName) {
		try {
			return actionClass.getMethod(methodName);
		} catch (NoSuchMethodException ex) {
			throw new RuntimeException();
		}
	}

	private class MockAction extends Action {
		@Form(bindingType = RequestParameterBindingType.ALL_PROPERTIES)
		public ActionResult all() {
			return null;
		}

		@Form(bindingType = RequestParameterBindingType.ONLY_SPECIFIED_PROPERTIES)
		public ActionResult specified() {
			return null;
		}

		@Form
		public ActionResult noBindingType() {
			return null;
		}

		public ActionResult noAnnotated() {
			return null;
		}
	}

	@Form(bindingType = RequestParameterBindingType.ALL_PROPERTIES)
	private class MockAction2 extends Action {
		@Form(bindingType = RequestParameterBindingType.ONLY_SPECIFIED_PROPERTIES)
		public ActionResult specified() {
			return null;
		}

		public ActionResult noAnnotated() {
			return null;
		}
	}

	public static class FormDto2 {
		@RequestParameter
		public String hasRequestParameter;

		public String noRequestParameter;
	}

	public static class FormDto {
		public Date date;
		public Integer num1;
		public Integer[] num2;
		public List<String> num3;
	}

	public static class ConvertersDto {
		public BigDecimal decimal;
		public BigDecimal[] decimals;
		public BigInteger bigint;
		public BigInteger[] bigints;
		public Boolean bool1;
		public Boolean[] bools1;
		public boolean bool2;
		public boolean[] bools2;
		public Byte byte1;
		public Byte[] bytes1;
		public byte byte2;
		public byte[] bytes2;
		public Character char1;
		public Character[] chars1;
		public char char2;
		public char[] chars2;
		public Date date;
		public Date[] dates;
		public Double double1;
		public Double[] doubles1;
		public double double2;
		public double[] doubles2;
		public ExEnum en;
		public ExEnum[] ens;
		public Float float1;
		public Float[] floats1;
		public float float2;
		public float[] floats2;
		public Integer int1;
		public Integer[] ints1;
		public int int2;
		public int[] ints2;
		public Long long1;
		public Long[] longs1;
		public long long2;
		public long[] longs2;
		public Short short1;
		public Short[] shorts1;
		public short short2;
		public short[] shorts2;
		public java.sql.Date sqldate;
		public java.sql.Date[] sqldates;
		public Time sqltime;
		public Time[] sqltimes;
		public Timestamp sqltimestamp;
		public Timestamp[] sqltimestamps;
	}

	public enum ExEnum {
		VALUE1, VALUE2, VALUE3;
	}

	public static class FileItemDto {
		public FileItem file;
		public byte[] bytefile;
		public byte[][] bytefiles;
		public Set<byte[]> bytefilelist;
		public InputStream input;
	}

	private static class MockFileItem implements FileItem {

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

	private static final Map<Integer, Integer> MONTHS;
	static {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, Calendar.JANUARY);
		map.put(2, Calendar.FEBRUARY);
		map.put(3, Calendar.MARCH);
		map.put(4, Calendar.APRIL);
		map.put(5, Calendar.MAY);
		map.put(6, Calendar.JUNE);
		map.put(7, Calendar.JULY);
		map.put(8, Calendar.AUGUST);
		map.put(9, Calendar.SEPTEMBER);
		map.put(10, Calendar.OCTOBER);
		map.put(11, Calendar.NOVEMBER);
		map.put(12, Calendar.DECEMBER);
		MONTHS = Collections.unmodifiableMap(map);
	}

	private static long fromDateToMillis(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, MONTHS.get(month));
		c.set(Calendar.DATE, date);
		return c.getTimeInMillis();
	}

	private static long fromTimeToMillis(int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTimeInMillis();
	}

	private static long fromTimestampToMillis(int year, int month, int date,
			int hour, int minute, int second) {
		Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, MONTHS.get(month));
		c.set(Calendar.DATE, date);
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTimeInMillis();
	}

	private static final byte[] getBytes(InputStream is) {
		byte[] bytes = null;
		byte[] buf = new byte[8192];
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, n);
			}
			bytes = baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return bytes;
	}

}
