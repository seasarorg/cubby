package org.seasar.cubby.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.seasar.framework.exception.OgnlRuntimeException;

public class CommonsTest extends TestCase {
	private FileItem item;
	public FileItem getItem() {
		return item;
	}
	
	public void testStringUtilsSplit() throws Exception {
		assertEquals("a",  StringUtils.split("a.b.c", ".", 2)[0]);
		assertEquals("b.c",  StringUtils.split("a.b.c", ".", 2)[1]);
	}
	
	public void testGetProperty() throws Exception {
		item = new FileItem() {

			private static final long serialVersionUID = 1L;

			public void delete() {
			}

			public byte[] get() {
				return null;
			}

			public String getContentType() {
				return null;
			}

			public String getFieldName() {
				return "testField";
			}

			public InputStream getInputStream() throws IOException {
				return null;
			}

			public String getName() {
				return "testName";
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

			public String getString(String arg0)
					throws UnsupportedEncodingException {
				return null;
			}

			public boolean isFormField() {
				return false;
			}

			public boolean isInMemory() {
				return false;
			}

			public void setFieldName(String arg0) {
			}

			public void setFormField(boolean arg0) {
			}

			public void write(File arg0) throws Exception {
			}

		};
		assertEquals("testField", getNestedProperty("item.fieldName"));
		assertEquals("testName", getNestedProperty("item.name"));
		try {
			item = null;
			assertEquals("testName", getNestedProperty("item.name"));
			fail();
		} catch (OgnlRuntimeException e) {
		}
	}

	public static class TestFileItem implements FileItem {

		private static final long serialVersionUID = 1L;

		public void delete() {
		}

		public byte[] get() {
			return null;
		}

		public String getContentType() {
			return null;
		}

		public String getFieldName() {
			return "testField";
		}

		public InputStream getInputStream() throws IOException {
			return null;
		}

		public String getName() {
			return "testName";
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

		public String getString(String arg0) throws UnsupportedEncodingException {
			return null;
		}

		public boolean isFormField() {
			return false;
		}

		public boolean isInMemory() {
			return false;
		}

		public void setFieldName(String arg0) {
		}

		public void setFormField(boolean arg0) {
		}

		public void write(File arg0) throws Exception {
		}
		
	}

	private Object getNestedProperty(String propertyName) {
		try {
			return Ognl.getValue(propertyName, this);
		} catch (OgnlException e) {
			throw new OgnlRuntimeException(e);
		}
	}
}
