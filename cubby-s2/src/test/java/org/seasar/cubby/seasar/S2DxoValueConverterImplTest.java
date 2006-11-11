package org.seasar.cubby.seasar;

import java.util.Calendar;
import java.util.Date;

import org.seasar.cubby.util.ClassUtils;
import org.seasar.extension.unit.S2TestCase;

public class S2DxoValueConverterImplTest extends S2TestCase {
	private S2DxoValueConverterImpl valueConverter;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include("app.dicon");
	}

	@SuppressWarnings("deprecation")
	public void testDate2String() {
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);
		assertEquals("2006-01-01", 
				valueConverter.convert(
						cal.getTime(), 
						new TestForm(), 
						ClassUtils.getSetter(TestForm.class, "date"), 
						String.class));
	}

	public void testEmptyString2Integer() {
		assertEquals(null, 
				valueConverter.convert(
						"", 
						new TestForm(), 
						ClassUtils.getSetter(TestForm.class, "num1"), 
						Integer.class));
	}

	public void testEmptyStringArray2IntegerArray() {
		try {
			assertEquals(null, 
					valueConverter.convert(
							new String[]{""},
							new TestForm(), 
							ClassUtils.getSetter(TestForm.class, "num2"), 
							Integer[].class));
			fail();
			
		} catch (NumberFormatException e) {
			assertTrue(true);
		}
	}

	class TestForm {
		public static final String DATE_PATTERN = "yyyy-MM-dd";
		Date date;
		Integer num1;
		Integer[] num2;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Integer getNum1() {
			return num1;
		}

		public void setNum1(Integer num1) {
			this.num1 = num1;
		}

		public Integer[] getNum2() {
			return num2;
		}

		public void setNum2(Integer[] num2) {
			this.num2 = num2;
		}
	}
}
