package org.seasar.cubby.examples.other.web.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
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

	public String filename;
	
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
