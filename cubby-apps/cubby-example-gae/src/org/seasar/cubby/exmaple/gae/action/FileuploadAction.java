package org.seasar.cubby.exmaple.gae.action;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.FileRegexpValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

import com.google.inject.servlet.RequestScoped;

@RequestScoped
@ActionClass
public class FileuploadAction {

	@RequestParameter
	private FileItem file;

	private String message;

	ValidationRules validation = new DefaultValidationRules() {
		public void initialize() {
			// (?i)を付けることで大文字小文字を区別しないという意味
			// 正規表現に関しての詳細な情報はjava.util.PatternクラスのJavaDocを参照してください。
			add("file", new RequiredValidator());
			add("file", new FileRegexpValidator(".+\\.(?i)(png|jpg)"));
		}
	};

	public String getMessage() {
		return message;
	}

	public ActionResult index() throws Exception {
		return new Forward("index.jsp");
	}

	@Validation(rules = "validation", errorPage = "index.jsp")
	@Accept(RequestMethod.POST)
	public ActionResult upload() throws Exception {
		message = "uploaded... " + file.getName() + " : " + file.getSize();
		return new Forward("upload.jsp");
	}

}
