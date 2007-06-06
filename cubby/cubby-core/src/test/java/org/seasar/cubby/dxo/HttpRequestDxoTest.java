package org.seasar.cubby.dxo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.extension.unit.S2TestCase;

/**
 * 
 * @author agata
 */
public class HttpRequestDxoTest extends S2TestCase {
	private HttpRequestDxo httpRequestDxo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
        include(getClass().getName().replace('.', '/') + ".dicon");
	}

	public void testBeanToMap() {
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);

		TestBean bean = new TestBean();
		bean.setDate(cal.getTime());

		Map<String, String> map = new HashMap<String, String>();

		httpRequestDxo.convert(bean, map);
		assertEquals("2006-01-01", map.get("date"));
	}

	public void testMapToBean() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("date", "2006-01-01");

		TestBean bean = new TestBean();

		httpRequestDxo.convert(map, bean);
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(format.format(cal.getTime()), format.format(bean.getDate()));
	}

	public void testMapToBean_OneValue() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("num1", "1");

		TestBean bean = new TestBean();

		httpRequestDxo.convert(map, bean);
		assertEquals(new Integer(1), bean.getNum1());
	}

	public void testMapToBean_MultiValue() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num2", new String[] {"1", "2"});

		TestBean bean = new TestBean();

		httpRequestDxo.convert(map, bean);
		assertEquals(2, bean.getNum2().length);
		assertEquals(new Integer(1), bean.getNum2()[0]);
		assertEquals(new Integer(2), bean.getNum2()[1]);
	}

	public class TestBean {

		Date date;

		Integer num1;

		Integer[] num2;

		List<String> num3;

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

		public List<String> getNum3() {
			return num3;
		}

		public void setNum3(List<String> num3) {
			this.num3 = num3;
		}

	}
}
