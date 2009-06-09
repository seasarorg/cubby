/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.internal.controller.impl;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.MessageInfo;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultFormatPattern;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.internal.controller.ConversionFailure;
import org.seasar.cubby.internal.controller.RequestParameterBinder;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.mock.MockActionContext;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.mock.MockConverterProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

/**
 * 
 * @author baba
 */
public class RequestParameterBinderImplTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private RequestParameterBinder requestParameterBinder;

	@Before
	public void setup() {
		final BinderPlugin binderPlugin = new BinderPlugin();
		final FormatPattern formatPattern = new DefaultFormatPattern();
		final MessagesBehaviour messagesBehaviour = new DefaultMessagesBehaviour();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(final Class<T> type) {
						if (FormatPattern.class.equals(type)) {
							return type.cast(formatPattern);
						}
						if (MessagesBehaviour.class.equals(type)) {
							return type.cast(messagesBehaviour);
						}
						throw new LookupException(type.getName());
					}
				}));
		binderPlugin.bind(ConverterProvider.class).toInstance(
				new MockConverterProvider(new BraceConverter()));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());

		pluginRegistry.register(binderPlugin);

		requestParameterBinder = new RequestParameterBinderImpl();
	}

	@After
	public void teardown() {
		pluginRegistry.clear();
	}

	@Test
	public void mapToBeanNullSource() {
		final FormDto dto = new FormDto();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(null, dto, actionContext);
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void mapToBean() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("date", new Object[] { "2006-01-01" });

		final FormDto dto = new FormDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		final Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(format.format(cal.getTime()), format.format(dto.getDate()));
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void mapToBean_OneValue() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num1", new Object[] { "1" });
		map.put("num2", new Object[] { "2" });
		map.put("num3", new Object[] { "def" });

		final FormDto dto = new FormDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getNum1());
		assertEquals(Integer.valueOf(1), dto.getNum1());
		assertNotNull(dto.getNum2());
		assertEquals(1, dto.getNum2().length);
		assertEquals(Integer.valueOf(2), dto.getNum2()[0]);
		assertNotNull(dto.getNum3());
		assertEquals(1, dto.getNum3().size());
		assertEquals("def", dto.getNum3().get(0));
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void mapToBean_MultiValue() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new Object[] { "1", "2" });
		map.put("num3", new Object[] { "abc", "def" });

		final FormDto dto = new FormDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getNum2());
		assertEquals(2, dto.getNum2().length);
		assertEquals(Integer.valueOf(1), dto.getNum2()[0]);
		assertEquals(Integer.valueOf(2), dto.getNum2()[1]);
		assertNotNull(dto.getNum3());
		assertEquals(2, dto.getNum3().size());
		assertEquals("abc", dto.getNum3().get(0));
		assertEquals("def", dto.getNum3().get(1));
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void mapToBean_MultiValueIncludesEmptyValue() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new String[] { "1", "", "2" });

		final FormDto dto = new FormDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertEquals(3, dto.getNum2().length);
		assertEquals(Integer.valueOf(1), dto.getNum2()[0]);
		assertEquals(null, dto.getNum2()[1]);
		assertEquals(Integer.valueOf(2), dto.getNum2()[2]);
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void mapToBean_MultiValueIncludesNullValue() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num3", new String[] { "zzz", null, "xxx" });

		final FormDto dto = new FormDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertEquals(3, dto.getNum3().size());
		assertEquals("zzz", dto.getNum3().get(0));
		assertNull(dto.getNum3().get(1));
		assertEquals("xxx", dto.getNum3().get(2));
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void mapToBean_annotated() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("normal", new Object[] { "abcd" });
		map.put("specifiedName", new Object[] { "efgh" });
		map.put("foo", new Object[] { "ijkl" });
		map.put("specifiedConverter", new Object[] { "mnop" });
		map.put("specifiedNameAndConverter", new Object[] { "qrst" });
		map.put("bar", new Object[] { "uvwx" });

		final AnnotatedDto dto = new AnnotatedDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertEquals("abcd", dto.getNormal());
		assertEquals("ijkl", dto.getSpecifiedName());
		assertEquals("{mnop}", dto.getSpecifiedConverter());
		assertEquals("{uvwx}", dto.getSpecifiedNameAndConverter());
		assertTrue(conversionFailures.isEmpty());
	}

	@Test
	public void converters() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
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

		final ConvertersDto dto = new ConvertersDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);

		assertNotNull(dto.getDecimal());
		assertTrue(new BigDecimal("12.3").compareTo(dto.getDecimal()) == 0);

		assertNotNull(dto.getDecimals());
		assertEquals(2, dto.getDecimals().length);
		assertTrue(new BigDecimal("45.6").compareTo(dto.getDecimals()[0]) == 0);
		assertTrue(new BigDecimal("78.9").compareTo(dto.getDecimals()[1]) == 0);

		assertNotNull(dto.getBigint());
		assertTrue(new BigInteger("9876").compareTo(dto.getBigint()) == 0);

		assertNotNull(dto.getBigints());
		assertEquals(2, dto.getBigints().length);
		assertTrue(new BigInteger("5432").compareTo(dto.getBigints()[0]) == 0);
		assertTrue(new BigInteger("10").compareTo(dto.getBigints()[1]) == 0);

		assertNotNull(dto.getBool1());
		assertTrue(dto.getBool1());

		assertNotNull(dto.getBools1());
		assertEquals(2, dto.getBools1().length);
		assertTrue(dto.getBools1()[0]);
		assertFalse(dto.getBools1()[1]);

		assertFalse(dto.isBool2());

		assertNotNull(dto.getBools2());
		assertEquals(3, dto.getBools2().length);
		assertFalse(dto.getBools2()[0]);
		assertTrue(dto.getBools2()[1]);
		assertFalse(dto.getBools2()[2]);

		assertNotNull(dto.getByte1());
		assertEquals(Byte.valueOf((byte) 12), dto.getByte1());

		assertNotNull(dto.getBytes1());
		assertEquals(2, dto.getBytes1().length);
		assertEquals(Byte.valueOf((byte) 34), dto.getBytes1()[0]);
		assertEquals(Byte.valueOf((byte) 56), dto.getBytes1()[1]);

		assertEquals((byte) 98, dto.getByte2());

		assertNotNull(dto.getBytes2());
		assertEquals(2, dto.getBytes2().length);
		assertEquals((byte) 76, dto.getBytes2()[0]);
		assertEquals((byte) 54, dto.getBytes2()[1]);

		assertNotNull(dto.getChar1());
		assertEquals(Character.valueOf('a'), dto.getChar1());

		assertNotNull(dto.getChars1());
		assertEquals(2, dto.getChars1().length);
		assertEquals(Character.valueOf('b'), dto.getChars1()[0]);
		assertEquals(Character.valueOf('c'), dto.getChars1()[1]);

		assertNotNull(dto.getChar2());
		assertEquals('d', dto.getChar2());

		assertNotNull(dto.getChars2());
		assertEquals(2, dto.getChars2().length);
		assertEquals('e', dto.getChars2()[0]);
		assertEquals('f', dto.getChars2()[1]);

		assertNotNull(dto.getDate());
		assertEquals(new Date(fromDateToMillis(2008, 7, 28)), dto.getDate());

		assertNotNull(dto.getDates());
		assertEquals(2, dto.getDates().length);
		assertEquals(new Date(fromDateToMillis(2008, 8, 14)), dto.getDates()[0]);
		assertEquals(new Date(fromDateToMillis(2008, 10, 30)),
				dto.getDates()[1]);

		assertNotNull(dto.getDouble1());
		assertEquals(new Double(1.2d), dto.getDouble1());

		assertNotNull(dto.getDoubles1());
		assertEquals(2, dto.getDoubles1().length);
		assertEquals(new Double(3.4d), dto.getDoubles1()[0]);
		assertEquals(new Double(5.6d), dto.getDoubles1()[1]);

		assertEquals(9.8d, dto.getDouble2(), 0.0d);

		assertNotNull(dto.getDoubles2());
		assertEquals(2, dto.getDoubles2().length);
		assertEquals(7.6d, dto.getDoubles2()[0], 0.0d);
		assertEquals(5.4d, dto.getDoubles2()[1], 0.0d);

		assertNotNull(dto.getEn());
		assertSame(ExEnum.VALUE1, dto.getEn());

		assertNotNull(dto.getEns());
		assertEquals(2, dto.getEns().length);
		assertSame(ExEnum.VALUE2, dto.getEns()[0]);
		assertSame(ExEnum.VALUE3, dto.getEns()[1]);

		assertNotNull(dto.getFloat1());
		assertEquals(new Float(1.2f), dto.getFloat1());

		assertNotNull(dto.getFloats1());
		assertEquals(2, dto.getFloats1().length);
		assertEquals(new Float(3.4f), dto.getFloats1()[0]);
		assertEquals(new Float(5.6f), dto.getFloats1()[1]);

		assertEquals(9.8f, dto.getFloat2(), 0.0f);

		assertNotNull(dto.getFloats2());
		assertEquals(2, dto.getFloats2().length);
		assertEquals(7.6f, dto.getFloats2()[0], 0.0f);
		assertEquals(5.4f, dto.getFloats2()[1], 0.0f);

		assertNotNull(dto.getInt1());
		assertEquals(Integer.valueOf(12), dto.getInt1());

		assertNotNull(dto.getInts1());
		assertEquals(2, dto.getInts1().length);
		assertEquals(Integer.valueOf(34), dto.getInts1()[0]);
		assertEquals(Integer.valueOf(56), dto.getInts1()[1]);

		assertEquals(98, dto.getInt2());

		assertNotNull(dto.getInts2());
		assertEquals(2, dto.getInts2().length);
		assertEquals(76, dto.getInts2()[0]);
		assertEquals(54, dto.getInts2()[1]);

		assertNotNull(dto.getLong1());
		assertEquals(Long.valueOf(12l), dto.getLong1());

		assertNotNull(dto.getLongs1());
		assertEquals(2, dto.getLongs1().length);
		assertEquals(Long.valueOf(34l), dto.getLongs1()[0]);
		assertEquals(Long.valueOf(56l), dto.getLongs1()[1]);

		assertEquals(98l, dto.getLong2());

		assertNotNull(dto.getLongs2());
		assertEquals(2, dto.getLongs2().length);
		assertEquals(76l, dto.getLongs2()[0]);
		assertEquals(54l, dto.getLongs2()[1]);

		assertNotNull(dto.getShort1());
		assertEquals(Short.valueOf((short) 12), dto.getShort1());

		assertNotNull(dto.getShorts1());
		assertEquals(2, dto.getShorts1().length);
		assertEquals(Short.valueOf((short) 34), dto.getShorts1()[0]);
		assertEquals(Short.valueOf((short) 56), dto.getShorts1()[1]);

		assertEquals((short) 98, dto.getShort2());

		assertNotNull(dto.getShorts2());
		assertEquals(2, dto.getShorts2().length);
		assertEquals((short) 76, dto.getShorts2()[0]);
		assertEquals((short) 54, dto.getShorts2()[1]);

		assertNotNull(dto.getSqldate());
		assertEquals(new java.sql.Date(fromDateToMillis(2008, 7, 28)), dto
				.getSqldate());

		assertNotNull(dto.getSqldates());
		assertEquals(2, dto.getSqldates().length);
		assertEquals(new java.sql.Date(fromDateToMillis(2008, 8, 14)), dto
				.getSqldates()[0]);
		assertEquals(new java.sql.Date(fromDateToMillis(2008, 10, 30)), dto
				.getSqldates()[1]);

		assertNotNull(dto.getSqltime());
		assertEquals(new Time(fromTimeToMillis(12, 34, 56)), dto.getSqltime());

		assertNotNull(dto.getSqltimes());
		assertEquals(2, dto.getSqltimes().length);
		assertEquals(new Time(fromTimeToMillis(13, 45, 24)),
				dto.getSqltimes()[0]);
		assertEquals(new Time(fromTimeToMillis(23, 44, 00)),
				dto.getSqltimes()[1]);

		assertNotNull(dto.getSqltimestamp());
		assertEquals(new Timestamp(fromTimestampToMillis(2008, 7, 28, 12, 34,
				56)), dto.getSqltimestamp());

		assertNotNull(dto.getSqltimestamps());
		assertEquals(2, dto.getSqltimestamps().length);
		assertEquals(new Timestamp(fromTimestampToMillis(2008, 8, 14, 13, 45,
				24)), dto.getSqltimestamps()[0]);
		assertEquals(new Timestamp(fromTimestampToMillis(2008, 10, 30, 23, 44,
				00)), dto.getSqltimestamps()[1]);

		assertTrue(conversionFailures.isEmpty());

		System.out.println(dto);
	}

	@Test
	public void convertersWithError() throws Exception {
		final HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		final HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		replay(request, response);

		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("decimal", new Object[] { "a" });
		map.put("decimals", new Object[] { "45.6", "b" });
		map.put("bigint", new Object[] { "c" });
		map.put("bigints", new Object[] { "d", "10" });
		map.put("byte1", new Object[] { "12a" });
		map.put("bytes1", new Object[] { "3324234456789", "56" });
		map.put("byte2", new Object[] { "98o" });
		map.put("bytes2", new Object[] { "76p", "54" });
		map.put("date", new Object[] { "2009-2-29" });
		map.put("dates", new Object[] { "2008-8-14", "2008/10/30" });
		map.put("double1", new Object[] { "1.2a" });
		map.put("doubles1", new Object[] { "3.4", "5.6b" });
		map.put("double2", new Object[] { "9.8c" });
		map.put("doubles2", new Object[] { "7.6d", "5.4" });
		map.put("en", new Object[] { "VALUE4" });
		map.put("ens", new Object[] { "VALUE2", "VALUE5" });
		map.put("float1", new Object[] { "1.2a" });
		map.put("floats1", new Object[] { "3.4b", "5.6" });
		map.put("float2", new Object[] { "9.8c" });
		map.put("floats2", new Object[] { "7.6d", "5.4" });
		map.put("int1", new Object[] { "12.1" });
		map.put("ints1", new Object[] { "34f", "56" });
		map.put("int2", new Object[] { "98g" });
		map.put("ints2", new Object[] { "76", "54h" });
		map.put("long1", new Object[] { "12i" });
		map.put("longs1", new Object[] { "34j", "56" });
		map.put("long2", new Object[] { "98k" });
		map.put("longs2", new Object[] { "76l", "54" });
		map.put("short1", new Object[] { "12m" });
		map.put("shorts1", new Object[] { "34n", "56" });
		map.put("short2", new Object[] { "98o" });
		map.put("shorts2", new Object[] { "76p", "54" });
		map.put("sqldate", new Object[] { "2008-7-280" });
		map.put("sqldates", new Object[] { "2008-8-14-", "2008-10-30" });
		map.put("sqltime", new Object[] { "25:34:56" });
		map.put("sqltimes", new Object[] { "13:45:99", "23:44:00" });
		map.put("sqltimestamp", new Object[] { "2008-7-28-12:34:56" });
		map.put("sqltimestamps", new Object[] { "2008-8-32 13:45:24",
				"2008-10-30 23:44:00" });

		final ConvertersDto dto = new ConvertersDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		// requestParameterBinder.bind(map, dto, actionContext, errors);

		final ActionErrors errors = new ActionErrorsImpl();

		ThreadContext.runInContext(request, response, new Command() {

			public void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				final List<ConversionFailure> conversionFailures = requestParameterBinder
						.bind(map, dto, actionContext);
				for (final ConversionFailure conversionFailure : conversionFailures) {
					final MessageInfo messageInfo = conversionFailure
							.getMessageInfo();
					final FieldInfo[] fieldInfos = conversionFailure
							.getFieldInfos();
					final String message = messageInfo
							.toMessage(conversionFailure.getFieldName());
					errors.add(message, fieldInfos);
				}

			}

		});

		assertFalse(errors.isEmpty());

		System.out.println(errors.getFields().get("decimals"));
		assertFalse(errors.getFields().get("decimals").isEmpty());
		System.out.println(errors.getIndexedFields().get("decimals").get(0));
		assertTrue(errors.getIndexedFields().get("decimals").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("decimals").get(1));
		assertFalse(errors.getIndexedFields().get("decimals").get(1).isEmpty());

		System.out.println(errors.getFields().get("bigint"));
		assertFalse(errors.getFields().get("bigint").isEmpty());
		System.out.println(errors.getIndexedFields().get("bigints").get(0));
		assertFalse(errors.getIndexedFields().get("bigints").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("bigints").get(1));
		assertTrue(errors.getIndexedFields().get("bigints").get(1).isEmpty());

		System.out.println(errors.getFields().get("byte1"));
		assertFalse(errors.getFields().get("byte1").isEmpty());
		System.out.println(errors.getIndexedFields().get("bytes1").get(0));
		assertFalse(errors.getIndexedFields().get("bytes1").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("bytes1").get(1));
		assertTrue(errors.getIndexedFields().get("bytes1").get(1).isEmpty());

		System.out.println(errors.getFields().get("byte2"));
		assertFalse(errors.getFields().get("byte2").isEmpty());
		System.out.println(errors.getIndexedFields().get("bytes2").get(0));
		assertFalse(errors.getIndexedFields().get("bytes2").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("bytes2").get(1));
		assertTrue(errors.getIndexedFields().get("bytes2").get(1).isEmpty());

		System.out.println(errors.getFields().get("double1"));
		assertFalse(errors.getFields().get("double1").isEmpty());
		System.out.println(errors.getIndexedFields().get("doubles1").get(0));
		assertTrue(errors.getIndexedFields().get("doubles1").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("doubles1").get(1));
		assertFalse(errors.getIndexedFields().get("doubles1").get(1).isEmpty());

		System.out.println(errors.getFields().get("double2"));
		assertFalse(errors.getFields().get("double2").isEmpty());
		System.out.println(errors.getIndexedFields().get("doubles2").get(0));
		assertFalse(errors.getIndexedFields().get("doubles2").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("doubles2").get(1));
		assertTrue(errors.getIndexedFields().get("doubles2").get(1).isEmpty());

		System.out.println(errors.getFields().get("float1"));
		assertFalse(errors.getFields().get("float1").isEmpty());
		System.out.println(errors.getIndexedFields().get("floats1").get(0));
		assertFalse(errors.getIndexedFields().get("floats1").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("floats1").get(1));
		assertTrue(errors.getIndexedFields().get("floats1").get(1).isEmpty());

		System.out.println(errors.getFields().get("float2"));
		assertFalse(errors.getFields().get("float2").isEmpty());
		System.out.println(errors.getIndexedFields().get("floats2").get(0));
		assertFalse(errors.getIndexedFields().get("floats2").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("floats2").get(1));
		assertTrue(errors.getIndexedFields().get("floats2").get(1).isEmpty());

		System.out.println(errors.getFields().get("int1"));
		assertFalse(errors.getFields().get("int1").isEmpty());
		System.out.println(errors.getIndexedFields().get("ints1").get(0));
		assertFalse(errors.getIndexedFields().get("ints1").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("ints1").get(1));
		assertTrue(errors.getIndexedFields().get("ints1").get(1).isEmpty());

		System.out.println(errors.getFields().get("int2"));
		assertFalse(errors.getFields().get("int2").isEmpty());
		System.out.println(errors.getIndexedFields().get("ints2").get(0));
		assertTrue(errors.getIndexedFields().get("ints2").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("ints2").get(1));
		assertFalse(errors.getIndexedFields().get("ints2").get(1).isEmpty());

		System.out.println(errors.getFields().get("long1"));
		assertFalse(errors.getFields().get("long1").isEmpty());
		System.out.println(errors.getIndexedFields().get("longs1").get(0));
		assertFalse(errors.getIndexedFields().get("longs1").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("longs1").get(1));
		assertTrue(errors.getIndexedFields().get("longs1").get(1).isEmpty());

		System.out.println(errors.getFields().get("long2"));
		assertFalse(errors.getFields().get("long2").isEmpty());
		System.out.println(errors.getIndexedFields().get("longs2").get(0));
		assertFalse(errors.getIndexedFields().get("longs2").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("longs2").get(1));
		assertTrue(errors.getIndexedFields().get("longs2").get(1).isEmpty());

		System.out.println(errors.getFields().get("short1"));
		assertFalse(errors.getFields().get("short1").isEmpty());
		System.out.println(errors.getIndexedFields().get("shorts1").get(0));
		assertFalse(errors.getIndexedFields().get("shorts1").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("shorts1").get(1));
		assertTrue(errors.getIndexedFields().get("shorts1").get(1).isEmpty());

		System.out.println(errors.getFields().get("short2"));
		assertFalse(errors.getFields().get("short2").isEmpty());
		System.out.println(errors.getIndexedFields().get("shorts2").get(0));
		assertFalse(errors.getIndexedFields().get("shorts2").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("shorts2").get(1));
		assertTrue(errors.getIndexedFields().get("shorts2").get(1).isEmpty());

		System.out.println(errors.getFields().get("date"));
		assertFalse(errors.getFields().get("date").isEmpty());
		System.out.println(errors.getIndexedFields().get("dates").get(0));
		assertTrue(errors.getIndexedFields().get("dates").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("dates").get(1));
		assertFalse(errors.getIndexedFields().get("dates").get(1).isEmpty());

		System.out.println(errors.getFields().get("sqldate"));
		assertFalse(errors.getFields().get("sqldate").isEmpty());
		System.out.println(errors.getIndexedFields().get("sqldates").get(0));
		assertFalse(errors.getIndexedFields().get("sqldates").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("sqldates").get(1));
		assertTrue(errors.getIndexedFields().get("sqldates").get(1).isEmpty());

		System.out.println(errors.getFields().get("sqltime"));
		assertFalse(errors.getFields().get("sqltime").isEmpty());
		System.out.println(errors.getIndexedFields().get("sqltimes").get(0));
		assertFalse(errors.getIndexedFields().get("sqltimes").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("sqltimes").get(1));
		assertTrue(errors.getIndexedFields().get("sqltimes").get(1).isEmpty());

		System.out.println(errors.getFields().get("sqltimestamp"));
		assertFalse(errors.getFields().get("sqltimestamp").isEmpty());
		System.out.println(errors.getIndexedFields().get("sqltimestamps")
				.get(0));
		assertFalse(errors.getIndexedFields().get("sqltimestamps").get(0)
				.isEmpty());
		System.out.println(errors.getIndexedFields().get("sqltimestamps")
				.get(1));
		assertTrue(errors.getIndexedFields().get("sqltimestamps").get(1)
				.isEmpty());

		System.out.println(errors.getFields().get("en"));
		assertFalse(errors.getFields().get("en").isEmpty());
		System.out.println(errors.getIndexedFields().get("ens").get(0));
		assertTrue(errors.getIndexedFields().get("ens").get(0).isEmpty());
		System.out.println(errors.getIndexedFields().get("ens").get(1));
		assertFalse(errors.getIndexedFields().get("ens").get(1).isEmpty());
	}

	@Test
	public void convertFileItem() throws Exception {
		final HttpServletRequest request = createNiceMock(HttpServletRequest.class);
		final HttpServletResponse response = createNiceMock(HttpServletResponse.class);
		replay(request, response);

		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("file", new FileItem[] { new MockFileItem("123") });
		map.put("bytefile", new FileItem[] { new MockFileItem("456") });
		map.put("bytefiles", new FileItem[] { new MockFileItem("abc"),
				new MockFileItem("def") });
		map.put("bytefilelist", new FileItem[] { new MockFileItem("GHI"),
				new MockFileItem("JKL") });
		map.put("input", new FileItem[] { new MockFileItem("QQ") });

		final FileItemDto dto = new FileItemDto();

		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final ActionErrors errors = new ActionErrorsImpl();
		ThreadContext.runInContext(request, response, new Command() {

			public void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				final List<ConversionFailure> conversionFailures = requestParameterBinder
						.bind(map, dto, actionContext);
				for (final ConversionFailure conversionFailure : conversionFailures) {
					final MessageInfo messageInfo = conversionFailure
							.getMessageInfo();
					final String message = messageInfo
							.toMessage(conversionFailure.getFieldName());
					errors.add(message);
				}
			}

		});

		final String encoding = "UTF-8";
		assertNotNull(dto.getFile());
		assertEquals("123", new String(dto.getFile().get(), encoding));
		assertNotNull(dto.getBytefile());
		assertEquals("456", new String(dto.getBytefile(), encoding));
		assertNotNull(dto.getBytefiles());
		assertEquals(2, dto.getBytefiles().length);
		assertEquals("abc", new String(dto.getBytefiles()[0], encoding));
		assertEquals("def", new String(dto.getBytefiles()[1], encoding));
		assertNotNull(dto.getBytefilelist());
		assertEquals(2, dto.getBytefilelist().size());
		final Iterator<byte[]> it = dto.getBytefilelist().iterator();
		assertEquals("GHI", new String(it.next(), encoding));
		assertEquals("JKL", new String(it.next(), encoding));
		assertNotNull(dto.getInput());
		assertEquals("QQ", new String(getBytes(dto.getInput()), encoding));
		assertTrue(errors.isEmpty());
	}

	public void testBindTypeNoAnnotated() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		final FormDto2 dto = new FormDto2();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "noAnnotated"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getHasRequestParameter());
		assertEquals("abc", dto.getHasRequestParameter());
		assertNull(dto.getNoRequestParameter());
		assertTrue(conversionFailures.isEmpty());
	}

	public void testBindTypeNoBindingType() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		final FormDto2 dto = new FormDto2();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class,
						"noBindingType"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getHasRequestParameter());
		assertEquals("abc", dto.getHasRequestParameter());
		assertNotNull(dto.getNoRequestParameter());
		assertEquals("def", dto.getNoRequestParameter());
		assertTrue(conversionFailures.isEmpty());
	}

	public void testBindTypeAllProperties() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		final FormDto2 dto = new FormDto2();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "all"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getHasRequestParameter());
		assertEquals("abc", dto.getHasRequestParameter());
		assertNotNull(dto.getNoRequestParameter());
		assertEquals("def", dto.getNoRequestParameter());
		assertTrue(conversionFailures.isEmpty());
	}

	public void testBindTypeOnlySpecifiedProperties() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		final FormDto2 dto = new FormDto2();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction.class, actionMethod(MockAction.class, "specified"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getHasRequestParameter());
		assertEquals("abc", dto.getHasRequestParameter());
		assertNull(dto.getNoRequestParameter());
		assertTrue(conversionFailures.isEmpty());
	}

	public void testBindTypeNoAnnotatedOnClass() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		final FormDto2 dto = new FormDto2();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction2.class, actionMethod(MockAction2.class,
						"noAnnotated"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getHasRequestParameter());
		assertEquals("abc", dto.getHasRequestParameter());
		assertNotNull(dto.getNoRequestParameter());
		assertEquals("def", dto.getNoRequestParameter());
		assertTrue(conversionFailures.isEmpty());
	}

	public void testBindTypeOnlySpecifiedPropertiesOnClass() {
		final Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("hasRequestParameter", new Object[] { "abc" });
		map.put("noRequestParameter", new Object[] { "def" });
		final FormDto2 dto = new FormDto2();
		final ActionContext actionContext = new MockActionContext(null,
				MockAction2.class, actionMethod(MockAction2.class, "specified"));
		final List<ConversionFailure> conversionFailures = requestParameterBinder
				.bind(map, dto, actionContext);
		assertNotNull(dto.getHasRequestParameter());
		assertEquals("abc", dto.getHasRequestParameter());
		assertNull(dto.getNoRequestParameter());
		assertTrue(conversionFailures.isEmpty());
	}

	private Method actionMethod(final Class<? extends Action> actionClass,
			final String methodName) {
		try {
			return actionClass.getMethod(methodName);
		} catch (final NoSuchMethodException ex) {
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
		private String hasRequestParameter;

		public String getHasRequestParameter() {
			return hasRequestParameter;
		}

		@RequestParameter
		public void setHasRequestParameter(final String hasRequestParameter) {
			this.hasRequestParameter = hasRequestParameter;
		}

		private String noRequestParameter;

		public String getNoRequestParameter() {
			return noRequestParameter;
		}

		public void setNoRequestParameter(final String noRequestParameter) {
			this.noRequestParameter = noRequestParameter;
		}
	}

	public static class FormDto {
		private Date date;
		private Integer num1;
		private Integer[] num2;
		private List<String> num3;

		public Date getDate() {
			return date;
		}

		public void setDate(final Date date) {
			this.date = date;
		}

		public Integer getNum1() {
			return num1;
		}

		public void setNum1(final Integer num1) {
			this.num1 = num1;
		}

		public Integer[] getNum2() {
			return num2;
		}

		public void setNum2(final Integer[] num2) {
			this.num2 = num2;
		}

		public List<String> getNum3() {
			return num3;
		}

		public void setNum3(final List<String> num3) {
			this.num3 = num3;
		}
	}

	public static class ConvertersDto {
		private BigDecimal decimal;
		private BigDecimal[] decimals;
		private BigInteger bigint;
		private BigInteger[] bigints;
		private Boolean bool1;
		private Boolean[] bools1;
		private boolean bool2;
		private boolean[] bools2;
		private Byte byte1;
		private Byte[] bytes1;
		private byte byte2;
		private byte[] bytes2;
		private Character char1;
		private Character[] chars1;
		private char char2;
		private char[] chars2;
		private Date date;
		private Date[] dates;
		private Double double1;
		private Double[] doubles1;
		private double double2;
		private double[] doubles2;
		private ExEnum en;
		private ExEnum[] ens;
		private Float float1;
		private Float[] floats1;
		private float float2;
		private float[] floats2;
		private Integer int1;
		private Integer[] ints1;
		private int int2;
		private int[] ints2;
		private Long long1;
		private Long[] longs1;
		private long long2;
		private long[] longs2;
		private Short short1;
		private Short[] shorts1;
		private short short2;
		private short[] shorts2;
		private java.sql.Date sqldate;
		private java.sql.Date[] sqldates;
		private Time sqltime;
		private Time[] sqltimes;
		private Timestamp sqltimestamp;
		private Timestamp[] sqltimestamps;

		public BigDecimal getDecimal() {
			return decimal;
		}

		public void setDecimal(final BigDecimal decimal) {
			this.decimal = decimal;
		}

		public BigDecimal[] getDecimals() {
			return decimals;
		}

		public void setDecimals(final BigDecimal[] decimals) {
			this.decimals = decimals;
		}

		public BigInteger getBigint() {
			return bigint;
		}

		public void setBigint(final BigInteger bigint) {
			this.bigint = bigint;
		}

		public BigInteger[] getBigints() {
			return bigints;
		}

		public void setBigints(final BigInteger[] bigints) {
			this.bigints = bigints;
		}

		public Boolean getBool1() {
			return bool1;
		}

		public void setBool1(final Boolean bool1) {
			this.bool1 = bool1;
		}

		public Boolean[] getBools1() {
			return bools1;
		}

		public void setBools1(final Boolean[] bools1) {
			this.bools1 = bools1;
		}

		public boolean isBool2() {
			return bool2;
		}

		public void setBool2(final boolean bool2) {
			this.bool2 = bool2;
		}

		public boolean[] getBools2() {
			return bools2;
		}

		public void setBools2(final boolean[] bools2) {
			this.bools2 = bools2;
		}

		public Byte getByte1() {
			return byte1;
		}

		public void setByte1(final Byte byte1) {
			this.byte1 = byte1;
		}

		public Byte[] getBytes1() {
			return bytes1;
		}

		public void setBytes1(final Byte[] bytes1) {
			this.bytes1 = bytes1;
		}

		public byte getByte2() {
			return byte2;
		}

		public void setByte2(final byte byte2) {
			this.byte2 = byte2;
		}

		public byte[] getBytes2() {
			return bytes2;
		}

		public void setBytes2(final byte[] bytes2) {
			this.bytes2 = bytes2;
		}

		public Character getChar1() {
			return char1;
		}

		public void setChar1(final Character char1) {
			this.char1 = char1;
		}

		public Character[] getChars1() {
			return chars1;
		}

		public void setChars1(final Character[] chars1) {
			this.chars1 = chars1;
		}

		public char getChar2() {
			return char2;
		}

		public void setChar2(final char char2) {
			this.char2 = char2;
		}

		public char[] getChars2() {
			return chars2;
		}

		public void setChars2(final char[] chars2) {
			this.chars2 = chars2;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(final Date date) {
			this.date = date;
		}

		public Date[] getDates() {
			return dates;
		}

		public void setDates(final Date[] dates) {
			this.dates = dates;
		}

		public Double getDouble1() {
			return double1;
		}

		public void setDouble1(final Double double1) {
			this.double1 = double1;
		}

		public Double[] getDoubles1() {
			return doubles1;
		}

		public void setDoubles1(final Double[] doubles1) {
			this.doubles1 = doubles1;
		}

		public double getDouble2() {
			return double2;
		}

		public void setDouble2(final double double2) {
			this.double2 = double2;
		}

		public double[] getDoubles2() {
			return doubles2;
		}

		public void setDoubles2(final double[] doubles2) {
			this.doubles2 = doubles2;
		}

		public ExEnum getEn() {
			return en;
		}

		public void setEn(final ExEnum en) {
			this.en = en;
		}

		public ExEnum[] getEns() {
			return ens;
		}

		public void setEns(final ExEnum[] ens) {
			this.ens = ens;
		}

		public Float getFloat1() {
			return float1;
		}

		public void setFloat1(final Float float1) {
			this.float1 = float1;
		}

		public Float[] getFloats1() {
			return floats1;
		}

		public void setFloats1(final Float[] floats1) {
			this.floats1 = floats1;
		}

		public float getFloat2() {
			return float2;
		}

		public void setFloat2(final float float2) {
			this.float2 = float2;
		}

		public float[] getFloats2() {
			return floats2;
		}

		public void setFloats2(final float[] floats2) {
			this.floats2 = floats2;
		}

		public Integer getInt1() {
			return int1;
		}

		public void setInt1(final Integer int1) {
			this.int1 = int1;
		}

		public Integer[] getInts1() {
			return ints1;
		}

		public void setInts1(final Integer[] ints1) {
			this.ints1 = ints1;
		}

		public int getInt2() {
			return int2;
		}

		public void setInt2(final int int2) {
			this.int2 = int2;
		}

		public int[] getInts2() {
			return ints2;
		}

		public void setInts2(final int[] ints2) {
			this.ints2 = ints2;
		}

		public Long getLong1() {
			return long1;
		}

		public void setLong1(final Long long1) {
			this.long1 = long1;
		}

		public Long[] getLongs1() {
			return longs1;
		}

		public void setLongs1(final Long[] longs1) {
			this.longs1 = longs1;
		}

		public long getLong2() {
			return long2;
		}

		public void setLong2(final long long2) {
			this.long2 = long2;
		}

		public long[] getLongs2() {
			return longs2;
		}

		public void setLongs2(final long[] longs2) {
			this.longs2 = longs2;
		}

		public Short getShort1() {
			return short1;
		}

		public void setShort1(final Short short1) {
			this.short1 = short1;
		}

		public Short[] getShorts1() {
			return shorts1;
		}

		public void setShorts1(final Short[] shorts1) {
			this.shorts1 = shorts1;
		}

		public short getShort2() {
			return short2;
		}

		public void setShort2(final short short2) {
			this.short2 = short2;
		}

		public short[] getShorts2() {
			return shorts2;
		}

		public void setShorts2(final short[] shorts2) {
			this.shorts2 = shorts2;
		}

		public java.sql.Date getSqldate() {
			return sqldate;
		}

		public void setSqldate(final java.sql.Date sqldate) {
			this.sqldate = sqldate;
		}

		public java.sql.Date[] getSqldates() {
			return sqldates;
		}

		public void setSqldates(final java.sql.Date[] sqldates) {
			this.sqldates = sqldates;
		}

		public Time getSqltime() {
			return sqltime;
		}

		public void setSqltime(final Time sqltime) {
			this.sqltime = sqltime;
		}

		public Time[] getSqltimes() {
			return sqltimes;
		}

		public void setSqltimes(final Time[] sqltimes) {
			this.sqltimes = sqltimes;
		}

		public Timestamp getSqltimestamp() {
			return sqltimestamp;
		}

		public void setSqltimestamp(final Timestamp sqltimestamp) {
			this.sqltimestamp = sqltimestamp;
		}

		public Timestamp[] getSqltimestamps() {
			return sqltimestamps;
		}

		public void setSqltimestamps(final Timestamp[] sqltimestamps) {
			this.sqltimestamps = sqltimestamps;
		}
	}

	public enum ExEnum {
		VALUE1, VALUE2, VALUE3;
	}

	public static class FileItemDto {
		private FileItem file;
		private byte[] bytefile;
		private byte[][] bytefiles;
		private Set<byte[]> bytefilelist;
		private InputStream input;

		public FileItem getFile() {
			return file;
		}

		public void setFile(final FileItem file) {
			this.file = file;
		}

		public byte[] getBytefile() {
			return bytefile;
		}

		public void setBytefile(final byte[] bytefile) {
			this.bytefile = bytefile;
		}

		public byte[][] getBytefiles() {
			return bytefiles;
		}

		public void setBytefiles(final byte[][] bytefiles) {
			this.bytefiles = bytefiles;
		}

		public Set<byte[]> getBytefilelist() {
			return bytefilelist;
		}

		public void setBytefilelist(final Set<byte[]> bytefilelist) {
			this.bytefilelist = bytefilelist;
		}

		public InputStream getInput() {
			return input;
		}

		public void setInput(final InputStream input) {
			this.input = input;
		}
	}

	private static class MockFileItem implements FileItem {

		private static final long serialVersionUID = 1L;

		private final String name;

		public MockFileItem(final String name) {
			this.name = name;
		}

		public void delete() {
		}

		public byte[] get() {
			try {
				return name.getBytes("UTF-8");
			} catch (final UnsupportedEncodingException e) {
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

		public String getString(final String encoding)
				throws UnsupportedEncodingException {
			return null;
		}

		public boolean isFormField() {
			return false;
		}

		public boolean isInMemory() {
			return false;
		}

		public void setFieldName(final String name) {
		}

		public void setFormField(final boolean state) {
		}

		public void write(final File file) throws Exception {
		}
	}

	private static final Map<Integer, Integer> MONTHS;
	static {
		final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
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

	private static long fromDateToMillis(final int year, final int month,
			final int date) {
		final Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, MONTHS.get(month));
		c.set(Calendar.DATE, date);
		return c.getTimeInMillis();
	}

	private static long fromTimeToMillis(final int hour, final int minute,
			final int second) {
		final Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTimeInMillis();
	}

	private static long fromTimestampToMillis(final int year, final int month,
			final int date, final int hour, final int minute, final int second) {
		final Calendar c = Calendar.getInstance();
		c.clear();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, MONTHS.get(month));
		c.set(Calendar.DATE, date);
		c.set(Calendar.HOUR, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		return c.getTimeInMillis();
	}

	private static final byte[] getBytes(final InputStream is) {
		byte[] bytes = null;
		final byte[] buf = new byte[8192];
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int n = 0;
			while ((n = is.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, n);
			}
			bytes = baos.toByteArray();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (final IOException e) {
				}
			}
		}
		return bytes;
	}

	public interface SeparationAction {

	}

	public static class SeparationActionImpl implements SeparationAction {

	}

	public static class AnnotatedDto {

		private String normal;

		private String specifiedName;

		private String specifiedConverter;

		private String specifiedNameAndConverter;

		public String getNormal() {
			return normal;
		}

		@RequestParameter
		public void setNormal(final String normal) {
			this.normal = normal;
		}

		public String getSpecifiedName() {
			return specifiedName;
		}

		@RequestParameter(name = "foo")
		public void setSpecifiedName(final String specifiedName) {
			this.specifiedName = specifiedName;
		}

		public String getSpecifiedConverter() {
			return specifiedConverter;
		}

		@RequestParameter(converter = BraceConverter.class)
		public void setSpecifiedConverter(final String specifiedConverter) {
			this.specifiedConverter = specifiedConverter;
		}

		public String getSpecifiedNameAndConverter() {
			return specifiedNameAndConverter;
		}

		@RequestParameter(name = "bar", converter = BraceConverter.class)
		public void setSpecifiedNameAndConverter(
				final String specifiedNameAndConverter) {
			this.specifiedNameAndConverter = specifiedNameAndConverter;
		}

	}

	public static class BraceConverter implements Converter {

		public Object convertToObject(final Object value,
				final Class<?> objectType, final ConversionHelper helper) {
			if (value == null) {
				return null;
			}
			return "{" + value + "}";
		}

		public String convertToString(final Object value,
				final ConversionHelper helper) {
			if (value == null) {
				return null;
			}
			return value.toString().substring(1, value.toString().length() - 1);
		}

		public Class<?> getObjectType() {
			return String.class;
		}

		public boolean canConvert(final Class<?> parameterType,
				final Class<?> objectType) {
			return false;
		}

	}

}
