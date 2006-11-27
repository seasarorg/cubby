package org.seasar.cubby.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;

public class CommonsTest extends TestCase {
	private FileItem item;
	public FileItem getItem() {
		return item;
	}
	
	public void testStringUtilsSplit() throws Exception {
		assertEquals("a",  StringUtils.split("a.b.c", ".", 2)[0]);
		assertEquals("b.c",  StringUtils.split("a.b.c", ".", 2)[1]);
	}
	
	@SuppressWarnings("serial")
	public void testGetProperty() throws Exception {
		item = new FileItem() {

			public void delete() {
				// TODO 自動生成されたメソッド・スタブ
				
			}

			public byte[] get() {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public String getContentType() {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public String getFieldName() {
				return "testField";
			}

			public InputStream getInputStream() throws IOException {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public String getName() {
				return "testName";
			}

			public OutputStream getOutputStream() throws IOException {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public long getSize() {
				// TODO 自動生成されたメソッド・スタブ
				return 0;
			}

			public String getString() {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public String getString(String arg0) throws UnsupportedEncodingException {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

			public boolean isFormField() {
				// TODO 自動生成されたメソッド・スタブ
				return false;
			}

			public boolean isInMemory() {
				// TODO 自動生成されたメソッド・スタブ
				return false;
			}

			public void setFieldName(String arg0) {
				// TODO 自動生成されたメソッド・スタブ
				
			}

			public void setFormField(boolean arg0) {
				// TODO 自動生成されたメソッド・スタブ
				
			}

			public void write(File arg0) throws Exception {
				// TODO 自動生成されたメソッド・スタブ
				
			}
			
		};
		assertEquals("testField", BeanUtils.getNestedProperty(this, "item.fieldName"));
		assertEquals("testName", BeanUtils.getNestedProperty(this, "item.name"));
		try {
			item = null;
			assertEquals("testName", BeanUtils.getNestedProperty(this, "item.name"));
			fail();
		} catch (NestedNullException e) {
		}
	}
}
