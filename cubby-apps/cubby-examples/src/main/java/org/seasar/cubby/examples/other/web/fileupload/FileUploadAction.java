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
package org.seasar.cubby.examples.other.web.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.FileRegexpValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Path("fileupload")
public class FileUploadAction extends Action {

	public ValidationRules validation = new DefaultValidationRules() {
		public void initialize() {
			add("filename", new RequiredValidator());
			add("file", new RequiredValidator());
			// (?i)を付けることで大文字小文字を区別しないという意味
			// 正規表現に関しての詳細な情報はjava.util.PatternクラスのJavaDocを参照してください。
			add("file", new FileRegexpValidator(".+\\.(?i)(png|jpg)")); 
		}
	};
	
	// ----------------------------------------------[DI Filed]

	// ----------------------------------------------[Attribute]

	@RequestParameter
	public String filename;
	
	@RequestParameter
	public FileItem file;

	// ----------------------------------------------[Action Method]

	public ActionResult index() {
		return new Forward("fileupload.jsp");
	}

	@Validation(rules = "validation", errorPage = "fileupload.jsp")
	public ActionResult upload() {
		return new Forward("fileupload_done.jsp");
	}

	// ----------------------------------------------[Helper Method]
	
}
