package org.seasar.cubby.validator.validators;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.validator.ScalarFieldValidator;

public class FileRegexpValidatorTest extends AbstractScalarFieldValidatorTestCase {

	public void testValidation1() {
		ScalarFieldValidator validator = new FileRegexpValidator("a.*34");
		assertSuccess(validator, null, "", new MockFileItem("a5634"));
		assertFail(validator, new MockFileItem("b5634"));
	}

	public void testValidation2() {
		ScalarFieldValidator validator = new FileRegexpValidator(Pattern.compile("(?i)a.*34"));
		assertSuccess(validator, null, "", new MockFileItem("a5634"));
		assertSuccess(validator, null, "", new MockFileItem("A5634"));
		assertFail(validator, new MockFileItem("b5634"));
	}
	
	
	@SuppressWarnings("serial")
	static class MockFileItem implements FileItem {
		
		private String name;

		public MockFileItem(String name) {
			this.name = name;
		}
		
		public void delete() {
		}

		public byte[] get() {
			return null;
		}

		public String getContentType() {
			return null;
		}

		public String getFieldName() {
			return null;
		}

		public InputStream getInputStream() throws IOException {
			return null;
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
}
