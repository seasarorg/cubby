package org.seasar.cubby.examples.other.web.fileupload;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Url;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Url("fileupload")
public class FileUploadAction extends Action {

	public ValidationRules validation = new DefaultValidationRules() {
		public void initialize() {
			add("filename", new RequiredValidator());
			add("file", new RequiredValidator());
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

	@Form
	@Validation(rulesField = "validation", errorPage = "fileupload.jsp")
	public ActionResult upload() {
		return new Forward("fileupload_done.jsp");
	}

	// ----------------------------------------------[Helper Method]
	
}
