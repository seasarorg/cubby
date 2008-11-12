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
package org.seasar.cubby.validator.validators;

import static org.seasar.cubby.validator.validators.ScalarFieldValidatorAssert.assertFail;
import static org.seasar.cubby.validator.validators.ScalarFieldValidatorAssert.assertSuccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.seasar.cubby.validator.ScalarFieldValidator;

public class FileRegexpValidatorTest {

	@Test
	public void validate1() {
		ScalarFieldValidator validator = new FileRegexpValidator("a.*34");
		assertSuccess(validator, null, "", new MockFileItem("a5634"));
		assertFail(validator, new MockFileItem("b5634"));
	}

	@Test
	public void validate2() {
		ScalarFieldValidator validator = new FileRegexpValidator(Pattern
				.compile("(?i)a.*34"));
		assertSuccess(validator, null, "", new MockFileItem("a5634"));
		assertSuccess(validator, null, "", new MockFileItem("A5634"));
		assertFail(validator, new MockFileItem("b5634"));
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
