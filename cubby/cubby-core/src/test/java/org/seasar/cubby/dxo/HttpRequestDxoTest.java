package org.seasar.cubby.dxo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

	public class TestBean {

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
